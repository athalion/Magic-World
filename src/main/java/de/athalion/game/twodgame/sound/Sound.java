package de.athalion.game.twodgame.sound;

import de.athalion.game.twodgame.utility.UtilityTool;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    public static int MUSIC_MENU = 0;
    public static int EFFECT_COIN = 1;
    public static int EFFECT_POWERUP = 2;
    public static int EFFECT_UNLOCK = 3;
    public static int EFFECT_FANFARE = 4;
    public static int EFFECT_HITMONSTER = 5;
    public static int EFFECT_RECEIVEDAMAGE = 6;
    public static int EFFECT_SWINGWEAPON = 7;
    public static int EFFECT_LEVELUP = 8;
    public static int EFFECT_CURSOR = 9;
    public static int EFFECT_TEXT = 10;
    public static int EFFECT_LOGO = 11;
    public static int MUSIC_DEMON_BATTLE_INTRO = 12;
    public static int MUSIC_DEMON_BATTLE_LOOP = 13;
    public static int MUSIC_DEMON_BATTLE_OUTRO = 14;
    public static int MUSIC_DEMON_BATTLE_SMALL = 15;
    public static int ENVIRONMENT_RAIN = 16;
    public static int ENVIRONMENT_RAIN_DULL = 17;

    Clip clip;
    FloatControl volumeControl;
    float volume;
    URL[] soundURL = new URL[100];
    public int index;
    public int next = 999;
    public boolean nextLooped = false;
    public boolean isLooped = false;

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/music/menu.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/swingweapon.wav");
        soundURL[8] = getClass().getResource("/sound/levelup.wav");
        soundURL[9] = getClass().getResource("/sound/effects/cursor.wav");
        soundURL[10] = getClass().getResource("/sound/text.wav");
        soundURL[11] = getClass().getResource("/sound/logo.wav");
        soundURL[12] = getClass().getResource("/sound/music/demon_battle/demon_battle_intro.wav");
        soundURL[13] = getClass().getResource("/sound/music/demon_battle/demon_battle_loop.wav");
        soundURL[14] = getClass().getResource("/sound/music/demon_battle/demon_battle_outro.wav");
        soundURL[15] = getClass().getResource("/sound/music/demon_battle/demon_battle_small.wav");
        soundURL[16] = getClass().getResource("/sound/environment/rain.wav");
        soundURL[17] = getClass().getResource("/sound/environment/rain_dull.wav");

    }

    public void setFile(int i) {

        index = i;

        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            UtilityTool.openErrorWindow(e);
        }

    }

    public void play() {
        clip.start();
    }

    public void stop() {
        if (clip != null) clip.stop();
    }

    public long getLength() {
        return clip.getMicrosecondLength();
    }

    public boolean isLooped() {
        return isLooped;
    }

    public void setLooped(boolean looped) {
        isLooped = looped;
    }

    public void updateVolume(float volume) {
        if (volumeControl != null) {
            this.volume = volume;
            volumeControl.setValue(80 * (volume / 10) - 80);
        }
    }

}