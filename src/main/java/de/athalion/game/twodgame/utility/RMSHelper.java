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
                    Vector rmsStereo = computeStereoRms(buffer, bytesRead, channels);
                    rmsList.add(rmsStereo);
                }

                Vector[] out = new Vector[rmsList.size()];
                rmsList.toArray(out);
                return out;
            }
        }
    }

    public static Vector[] applyExponentialSmoothing(Vector[] input, double tauSeconds) {
        if (input == null || input.length == 0) return new Vector[0];

        double dt = Math.max(1e-9, WINDOW_MS / 1000.0);
        double alpha = Math.exp(-dt / Math.max(1e-9, tauSeconds));
        Vector[] out = new Vector[input.length];

        out[0] = new Vector(input[0].getX(), input[0].getY());

        for (int i = 1; i < input.length; i++) {
            double prevL = out[i - 1].getX();
            double prevR = out[i - 1].getY();
            double curL  = input[i].getX();
            double curR  = input[i].getY();

            double smL = alpha * prevL + (1.0 - alpha) * curL;
            double smR = alpha * prevR + (1.0 - alpha) * curR;

            // clamp just in case
            smL = Math.clamp(smL, 0.0, 1.0);
            smR = Math.clamp(smR, 0.0, 1.0);

            out[i] = new Vector(smL, smR);
        }
        return out;
    }

    public static Vector[] applyCurves(Vector[] input) {
        if (input == null || input.length == 0) return new Vector[0];

        Vector[] out = new Vector[input.length];

        for (int i = 0; i < input.length; i++) {
            double l = input[i].getX();
            double r = input[i].getY();

            out[i] = new Vector(l * l, r * r);
        }
        return out;
    }

    // Compute stereo RMS for 16-bit PCM little-endian. If file has >2 channels, use first two.
    private static Vector computeStereoRms(byte[] audioBytes, int bytesRead, int channels) {
        if (bytesRead < 2) return new Vector(0.0, 0.0);

        long sumSqLeft = 0L;
        long sumSqRight = 0L;
        int countLeft = 0;
        int countRight = 0;

        int frameSize = channels * 2;
        for (int offset = 0; offset + 1 < bytesRead; offset += frameSize) {
            // left channel (channel 0)
            int idxL = offset;
            if (idxL + 1 < bytesRead) {
                int low = audioBytes[idxL] & 0xFF;
                int high = audioBytes[idxL + 1];
                int sampleL = (high << 8) | low;
                sumSqLeft += (long) sampleL * sampleL;
                countLeft++;
            }
            // right channel (channel 1) if exists, otherwise copy left
            if (channels > 1) {
                int idxR = offset + 2;
                if (idxR + 1 < bytesRead) {
                    int low = audioBytes[idxR] & 0xFF;
                    int high = audioBytes[idxR + 1];
                    int sampleR = (high << 8) | low;
                    sumSqRight += (long) sampleR * sampleR;
                    countRight++;
                }
            } else {
                int low = audioBytes[offset] & 0xFF;
                int high = audioBytes[offset + 1];
                int sample = (high << 8) | low;
                sumSqRight += (long) sample * sample;
                countRight++;
            }
        }

        double meanSqL = countLeft == 0 ? 0.0 : (double) sumSqLeft / countLeft;
        double meanSqR = countRight == 0 ? 0.0 : (double) sumSqRight / countRight;
        double rmsL = Math.sqrt(meanSqL);
        double rmsR = Math.sqrt(meanSqR);

        double normL = Math.min(1.0, rmsL / 32768.0);
        double normR = Math.min(1.0, rmsR / 32768.0);

        return new Vector(normL, normR);
    }

}
