package de.athalion.game.twodgame.sound;

import de.athalion.game.twodgame.input.controller.ControllerSystem;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.save.Settings;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.util.HashMap;

public class SoundSystem {

    private final static HashMap<String, Sound> SOUNDS = new HashMap<>();
    private final static HashMap<String, Music> MUSIC = new HashMap<>();

    private static int effectVolume;
    private static int environmentVolume;
    private static int musicVolume;

    /**
     * Initializes the sound system and loads all the sounds
     */
    public static void init() {
        Logger.log("Initializing sound system...");
        TinySound.init();
        for (Sounds value : Sounds.values()) {
            if (value.getType() != Sounds.Type.MUSIC) {
                SOUNDS.put(value.name(), TinySound.loadSound(value.getIdentifier().getResource()));
            } else MUSIC.put(value.name(), TinySound.loadMusic(value.getIdentifier().getResource()));
        }
        Logger.log("Done!");
    }

    /**
     * Plays a sound effect.
     * @param sound the sound to play
     */
    public static void playSound(Sounds sound) {
        if (sound.getType().equals(Sounds.Type.EFFECT)) {
            SOUNDS.get(sound.name()).play((double) effectVolume / 10);
        } else if (sound.getType().equals(Sounds.Type.ENVIRONMENT)) {
            SOUNDS.get(sound.name()).play((double) environmentVolume / 10);
        }
    }

    public static void playSoundWithHaptics(Sounds sound) {
        playSound(sound);
        ControllerSystem.doAdvancedVibration(sound);
    }

    /**
     * Plays music.
     * @param sound the music to play
     */
    public static void playMusic(Sounds sound) {
        MUSIC.get(sound.name()).play(true, (double) musicVolume / 10);
    }

    /**
     * Stops the currently playing music.
     */
    public static void stopMusic() {
        MUSIC.forEach((s, music1) -> music1.stop());
    }

    /**
     * Stops all currently playing sound effects.
     */
    public static void stopSound() {
        SOUNDS.forEach((s, sound) -> sound.stop());
    }

    /**
     * Stops a specific sound effect.
     * @param sound the sound to stop
     */
    public static void stopSound(Sounds sound) {
        SOUNDS.get(sound.name()).stop();
    }

    /**
     * Stops all sounds of a specific type.
     * @param type the type of sound to stop
     */
    public static void stopSound(Sounds.Type type) {
        SOUNDS.forEach((s, sound) -> {
            if (Sounds.valueOf(s).getType().equals(type)) sound.stop();
        });
    }

    /**
     * Updates the volume settings.
     */
    public static void updateVolume() {
        Settings settings = Settings.getSettings();
        if (settings.enableSound) {
            effectVolume = settings.effectVolume;
            environmentVolume = settings.environmentVolume;
            musicVolume = settings.musicVolume;
        } else {
            effectVolume = 0;
            environmentVolume = 0;
            musicVolume = 0;
        }
        MUSIC.forEach((_, music1) -> music1.setVolume(musicVolume));
    }

    /**
     * Stops the sound system.
     */
    public static void shutdown() {
        Logger.log("Shutting down sound system...");
        TinySound.shutdown();
    }

}
