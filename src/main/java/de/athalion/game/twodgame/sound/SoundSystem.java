package de.athalion.game.twodgame.sound;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.save.Settings;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.util.HashMap;

public class SoundSystem {

    private final static HashMap<String, Sound> sounds = new HashMap<>();
    private final static HashMap<String, Music> music = new HashMap<>();

    private static int effectVolume;
    private static int environmentVolume;
    private static int musicVolume;

    public static void init() {
        Logger.log("Initializing sound system...");
        TinySound.init();
        for (Sounds value : Sounds.values()) {
            if (value.getType() != Sounds.Type.MUSIC) {
                sounds.put(value.name(), TinySound.loadSound(value.getPath()));
            } else music.put(value.name(), TinySound.loadMusic(value.getPath()));
        }
        Logger.log("Done!");
    }

    public static void playSound(Sounds sound) {
        if (sound.getType().equals(Sounds.Type.EFFECT)) {
            sounds.get(sound.name()).play((double) effectVolume / 10);
        } else if (sound.getType().equals(Sounds.Type.ENVIRONMENT)) {
            sounds.get(sound.name()).play((double) environmentVolume / 10);
        }
    }

    public static void playMusic(Sounds sound) {
        music.get(sound.name()).play(true, (double) musicVolume / 10);
    }

    public static void stopMusic() {
        music.forEach((s, music1) -> music1.stop());
    }

    public static void stopSound() {
        sounds.forEach((s, sound) -> sound.stop());
    }

    public static void stopSound(Sounds sound) {
        sounds.get(sound.name()).stop();
    }

    public static void stopSound(Sounds.Type type) {
        sounds.forEach((s, sound) -> {
            if (Sounds.valueOf(s).getType().equals(type)) sound.stop();
        });
    }

    public static void updateVolume(Settings settings) {
        if (settings.enableSound) {
            effectVolume = settings.effectVolume;
            environmentVolume = settings.environmentVolume;
            musicVolume = settings.musicVolume;
        } else {
            effectVolume = 0;
            environmentVolume = 0;
            musicVolume = 0;
        }
        music.forEach((s, music1) -> music1.setVolume(musicVolume));
    }

    public static void shutdown() {
        Logger.log("Shutting down sound system...");
        TinySound.shutdown();
    }

}
