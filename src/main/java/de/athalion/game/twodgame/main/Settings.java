package de.athalion.game.twodgame.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.athalion.game.twodgame.utility.UtilityTool;

import java.io.*;

public class Settings {

    public boolean enableController = true;
    public boolean enableSound = true;
    public int musicVolume = 7;
    public int FXVolume = 9;
    public int environmentVolume = 5;
    public boolean fullscreen = true;

    public static void saveSettings(Settings settings) {
        File settingsFile = new File(System.getProperty("user.dir") + "/settings.json");
        createSettingsFile();
        try (Writer writer = new FileWriter(settingsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
        } catch (IOException e) {
            UtilityTool.openErrorWindow(e);
        }
    }

    public static Settings loadSettings() {
        File settingsFile = new File(System.getProperty("user.dir") + "/settings.json");
        createSettingsFile();
        try (Reader reader = new FileReader(settingsFile)) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            UtilityTool.openErrorWindow(e);
        }
        return new Settings();
    }

    public static void createSettingsFile() {
        File settingsFile = new File(System.getProperty("user.dir") + "/settings.json");
        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                UtilityTool.openErrorWindow(e);
            }
            try (Writer writer = new FileWriter(settingsFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(new Settings(), writer);
            } catch (IOException e) {
                UtilityTool.openErrorWindow(e);
            }
        }
    }

}
