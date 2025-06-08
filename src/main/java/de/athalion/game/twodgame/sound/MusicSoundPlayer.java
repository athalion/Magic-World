package de.athalion.game.twodgame.sound;

public class MusicSoundPlayer extends SoundPlayer {

    public String next = "";
    public boolean nextLooped = false;
    public boolean isLooped = false;

    public long getLength() {
        return clip.getMicrosecondLength();
    }

    public boolean isLooped() {
        return isLooped;
    }

    public void setLooped(boolean looped) {
        isLooped = looped;
    }

}
