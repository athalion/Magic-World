package de.athalion.game.twodgame.sound;

import de.athalion.game.twodgame.logs.Logger;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class Sound {

    public static String MUSIC_MENU = "/sound/music/menu.wav";
    public static String EFFECT_COIN = "/sound/coin.wav";
    public static String EFFECT_POWERUP = "/sound/powerup.wav";
    public static String EFFECT_UNLOCK = "/sound/unlock.wav";
    public static String EFFECT_FANFARE = "/sound/fanfare.wav";
    public static String EFFECT_HIT_MONSTER = "/sound/hitmonster.wav";
    public static String EFFECT_RECEIVE_DAMAGE = "/sound/receivedamage.wav";
    public static String EFFECT_SWING_WEAPON = "/sound/swingweapon.wav";
    public static String EFFECT_LEVEL_UP = "/sound/levelup.wav";
    public static String EFFECT_CURSOR = "/sound/effects/cursor.wav";
    public static String EFFECT_TEXT = "/sound/text.wav";
    public static String EFFECT_LOGO = "/sound/logo.wav";
    public static String MUSIC_DEMON_BATTLE_INTRO = "/sound/music/demon_battle/demon_battle_intro.wav";
    public static String MUSIC_DEMON_BATTLE_LOOP = "/sound/music/demon_battle/demon_battle_loop.wav";
    public static String MUSIC_DEMON_BATTLE_OUTRO = "/sound/music/demon_battle/demon_battle_outro.wav";
    public static String MUSIC_DEMON_BATTLE_SMALL = "/sound/music/demon_battle/demon_battle_small.wav";
    public static String ENVIRONMENT_RAIN = "/sound/environment/rain.wav";
    public static String ENVIRONMENT_RAIN_DULL = "/sound/environment/rain_dull.wav";

    Clip clip;
    FloatControl volumeControl;
    float volume;
    public String file;
    public String next = "";
    public boolean nextLooped = false;
    public boolean isLooped = false;

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