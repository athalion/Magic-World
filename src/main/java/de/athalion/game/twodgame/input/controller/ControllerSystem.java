package de.athalion.game.twodgame.input.controller;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import com.studiohartman.jamepad.ControllerUnpluggedException;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.save.Settings;
import de.athalion.game.twodgame.sound.Sounds;

public class ControllerSystem {

    private final static ControllerManager controllerManager = new ControllerManager();
    private static AdvancedVibrationExecutor advancedVibrationExecutor;

    public static void init() {
        Logger.log("Initializing controller system...");
        controllerManager.initSDLGamepad();
        advancedVibrationExecutor = new AdvancedVibrationExecutor(vector -> doVibration((float) vector.getX(), (float) vector.getY(), 20));
        advancedVibrationExecutor.init();
        Logger.log("Done!");
    }

    public static ControllerState checkInput() {
        controllerManager.update();
        return controllerManager.getState(0);
    }

    public static void doVibration(float leftMagnitude, float rightMagnitude, int duration) {
        if (Settings.getSettings().enableControllerVibration) {
            try {
                if (controllerManager.getControllerIndex(0).isConnected())
                    controllerManager.getControllerIndex(0).doVibration(leftMagnitude, rightMagnitude, duration);
            } catch (ControllerUnpluggedException e) {
                Logger.error("Error while doing controller vibration: " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
        }
    }

    public static void doAdvancedVibration(Sounds sound) {
        advancedVibrationExecutor.executeVibration(sound);
    }

    public static void quit() {
        Logger.log("Shutting down controller system.");
        advancedVibrationExecutor.shutdown();
        controllerManager.quitSDLGamepad();
    }

}
