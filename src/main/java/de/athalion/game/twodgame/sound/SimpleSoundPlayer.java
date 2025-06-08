package de.athalion.game.twodgame.sound;

import de.athalion.game.twodgame.logs.Logger;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SimpleSoundPlayer extends SoundPlayer implements LineListener {

    @Override
    public void update(LineEvent event) {

    }

    @Override
    public void setFile(String file) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(file)));
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            Logger.error("Error reading sound file " + file + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
    }

    @Override
    public void play() {
        clip.start();
    }

    @Override
    public void stop() {
        clip.stop();
    }

}
