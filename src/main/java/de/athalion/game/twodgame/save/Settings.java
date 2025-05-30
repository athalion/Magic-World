package de.athalion.game.twodgame.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.athalion.game.twodgame.lang.Languages;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.logs.Logger;

import java.io.*;

public class Settings {

    public boolean enableController = true;
    public boolean enableSound = true;
    public int musicVolume = 7;
    public int FXVolume = 9;
    public int environmentVolume = 5;
    public boolean fullscreen = true;
    public boolean hardwareAcceleration = true;
    public int screen = 0;
    public String language = "en_us";

    public static void saveSettings(Settings settings) {
        Logger.log("Saving settings...");
        File settingsFile = new File(System.getProperty("user.dir") + "/settings.json");
        createSettingsFile();
        try (Writer writer = new FileWriter(settingsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
            Logger.log("Done!");
        } catch (IOException e) {
            Logger.error("Error while saving settings: " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
    }

    public static Settings loadSettings() {
        Logger.log("Loading settings...");
        File settingsFile = new File(System.getProperty("user.dir") + "/settings.json");
        createSettingsFile();
        try (Reader reader = new FileReader(settingsFile)) {
            Gson gson = new GsonBuilder().create();
            Logger.log("Done!");
            return gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            Logger.error("Error while loading settings: " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
        return new Settings();
    }

    public static void createSettingsFile() {
        File settingsFile = new File(System.getProperty("user.dir") + "/settings.json");
        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                Logger.error("Error while creating new settings file: " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
            try (Writer writer = new FileWriter(settingsFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(new Settings(), writer);
            } catch (IOException e) {
                Logger.error("Error while writing default settings: " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
        }
    }

    public static void applySettings(Settings settings) {
        System.setProperty("sun.java2d.opengl", settings.hardwareAcceleration ? "true" : "false");
        Translations.changeLanguage(Languages.get(settings.language));
    }

}
