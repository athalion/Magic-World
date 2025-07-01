package de.athalion.game.twodgame.input;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import com.studiohartman.jamepad.ControllerUnpluggedException;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.save.Settings;

public class ControllerSystem {

    private final static ControllerManager controllerManager = new ControllerManager();
    private static boolean vibration = true;

    public static void init() {
        Logger.log("Initializing controller system...");
        controllerManager.initSDLGamepad();
        Logger.log("Done!");
    }

    public static KeyState checkInput() {
        ControllerState currentState = controllerManager.getState(0);
        if (currentState.isConnected) {
            return new KeyState()
                    .setMenuUpPressed(currentState.dpadUpJustPressed)
                    .setMenuDownPressed(currentState.dpadDownJustPressed)
                    .setMenuLeftPressed(currentState.dpadLeftJustPressed)
                    .setMenuRightPressed(currentState.dpadRightJustPressed)
                    .setMenuOKPressed(currentState.aJustPressed)
                    .setMenuBackPressed(currentState.bJustPressed)
                    .setMoveUpPressed(currentState.leftStickY >= 0.7f)
                    .setMoveDownPressed(currentState.leftStickY <= -0.7f)
                    .setMoveLeftPressed(currentState.leftStickX <= -0.7f)
                    .setMoveRightPressed(currentState.leftStickX >= 0.7f)
                    .setAttackPressed(currentState.aJustPressed)
                    .setInventoryKeyPressed(currentState.xJustPressed)
                    .setEscapePressed(currentState.startJustPressed);
        }
        return null;
    }

    public static void doVibration(float leftMagnitude, float rightMagnitude, int duration) {
        if (vibration) {
            try {
                controllerManager.getControllerIndex(0).doVibration(leftMagnitude, rightMagnitude, duration);
            } catch (ControllerUnpluggedException e) {
                Logger.error("Error while doing controller vibration: " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
        }
    }

    public static void updateSettings(Settings settings) {
        vibration = settings.enableControllerVibration;
    }

    public static void quit() {
        Logger.log("Shutting down controller system.");
        controllerManager.quitSDLGamepad();
    }

}
