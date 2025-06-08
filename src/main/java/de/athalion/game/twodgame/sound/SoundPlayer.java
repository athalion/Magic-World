package de.athalion.game.twodgame.sound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public abstract class SoundPlayer {

    Clip clip;
    FloatControl volumeControl;
    float volume;
    public String file;

    public void setFile(String file) {
    }

    public void play() {
    }

    public void stop() {

    }

    public void updateVolume(float volume) {
        if (volumeControl != null) {
            this.volume = volume;
            volumeControl.setValue(80 * (volume / 10) - 80);
        }
    }

}