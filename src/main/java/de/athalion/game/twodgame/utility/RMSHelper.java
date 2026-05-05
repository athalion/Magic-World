package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.math.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class RMSHelper {

    private final static int WINDOW_MS = 20;
    private final static double RIGHT_MOTOR_BOOST = 1.4;
    private final static double NOISE_FLOOR = 0.02;
    private final static double TAU_SECONDS = 0.03;

    public static Vector[] extractStereoRms(InputStream wavStream)
            throws UnsupportedAudioFileException, IOException {
        try (AudioInputStream in = AudioSystem.getAudioInputStream(wavStream)) {
            AudioFormat baseFormat = in.getFormat();

            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            try (AudioInputStream din = AudioSystem.getAudioInputStream(targetFormat, in)) {
                float sampleRate = targetFormat.getSampleRate();
                int channels = targetFormat.getChannels();
                int frameSize = targetFormat.getFrameSize();
                int windowFrames = Math.max(1, (int) (sampleRate * (WINDOW_MS / 1000.0)));
                int windowBytes = windowFrames * frameSize;

                byte[] buffer = new byte[windowBytes];
                List<Vector> rmsList = new ArrayList<>();

                int bytesRead;
                while ((bytesRead = din.read(buffer, 0, buffer.length)) != -1) {
                    Vector rmsStereo = computeSmartRms(buffer, bytesRead, channels);
                    rmsList.add(rmsStereo);
                }

                return rmsList.toArray(new Vector[0]);
            }
        }
    }

    /**
     * Improved RMS calculation
     */
    private static Vector computeSmartRms(byte[] audioBytes, int bytesRead, int channels) {
        if (bytesRead < 2) return new Vector(0.0, 0.0);

        long sumSqLeft = 0L;
        long sumSqRight = 0L;
        int count = 0;

        int frameSize = channels * 2;
        for (int offset = 0; offset + frameSize <= bytesRead; offset += frameSize) {
            int lowL = audioBytes[offset] & 0xFF;
            int highL = audioBytes[offset + 1];
            short sampleL = (short) ((highL << 8) | lowL);

            short sampleR;
            if (channels > 1) {
                int lowR = audioBytes[offset + 2] & 0xFF;
                int highR = audioBytes[offset + 3];
                sampleR = (short) ((highR << 8) | lowR);
            } else {
                sampleR = sampleL;
            }

            sumSqLeft += (long) sampleL * sampleL;
            sumSqRight += (long) sampleR * sampleR;
            count++;
        }

        double rmsL = Math.sqrt((double) sumSqLeft / count) / 32768.0;
        double rmsR = Math.sqrt((double) sumSqRight / count) / 32768.0;

        rmsL = rmsL < NOISE_FLOOR ? 0 : rmsL;
        rmsR = rmsR < NOISE_FLOOR ? 0 : rmsR;

        rmsR *= RIGHT_MOTOR_BOOST;

        return new Vector(Math.min(1.0, rmsL), Math.min(1.0, rmsR));
    }

    public static Vector[] applyExponentialSmoothing(Vector[] input) {
        if (input == null || input.length == 0) return new Vector[0];

        double dt = WINDOW_MS / 1000.0;
        double alpha = Math.exp(-dt / Math.max(1e-9, TAU_SECONDS));

        Vector[] out = new Vector[input.length];
        out[0] = input[0];

        for (int i = 1; i < input.length; i++) {
            double smL = alpha * out[i - 1].getX() + (1.0 - alpha) * input[i].getX();
            double smR = alpha * out[i - 1].getY() + (1.0 - alpha) * input[i].getY();
            out[i] = new Vector(Math.clamp(smL, 0.0, 1.0), Math.clamp(smR, 0.0, 1.0));
        }
        return out;
    }

    public static Vector[] applyCurves(Vector[] input) {
        if (input == null || input.length == 0) return new Vector[0];
        Vector[] out = new Vector[input.length];
        for (int i = 0; i < input.length; i++) {
            double l = input[i].getX();
            double r = input[i].getY();

            out[i] = new Vector(l * l * l, r * r);
        }
        return out;
    }
}
