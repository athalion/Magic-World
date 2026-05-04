package de.athalion.game.twodgame.input;

import com.studiohartman.jamepad.ControllerState;
import de.athalion.game.twodgame.input.controller.ControllerSystem;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.Settings;

import java.awt.event.KeyEvent;
import java.util.*;

public class InputSystem {

    private final static KeyHandler KEY_HANDLER = new KeyHandler();

    private static final HashMap<InputAction, Keybind> KEYBINDS = new HashMap<>();

    private final static Queue<KeyEvent> inputQueue = new LinkedList<>();

    public static void init() {
        GameInstance.getInstance().getGameFrame().addKeyListener(KEY_HANDLER);
        Settings.getSettings().keybinds.forEach((inputAction, keyCode) -> KEYBINDS.put(inputAction, new Keybind(inputAction, keyCode)));
        KEY_HANDLER.onKeyJustPressed(keyEvent -> {
            inputQueue.add(keyEvent);
            getKeybinds(keyEvent.getKeyCode()).forEach(keybind -> keybind.getActionState().setJustPressed(true));
        });
        KEY_HANDLER.onKeyPressed(keyEvent -> {
            getKeybinds(keyEvent.getKeyCode()).forEach(keybind -> keybind.getActionState().setPressed(false));
        });
        KEY_HANDLER.onKeyReleased(keyEvent -> {
            getKeybinds(keyEvent.getKeyCode()).forEach(keybind -> keybind.getActionState().setPressed(false));
        });
        ControllerSystem.init();
    }

    public static void processInput() {
        if (Settings.getSettings().enableController) {
            ControllerState controllerState = ControllerSystem.checkInput();
            if (!controllerState.isConnected) return;
            KEYBINDS.values().forEach(keybind -> keybind.getActionState().with(keybind.getInputAction().extractControllerState(controllerState)));
        }
    }

    public static void reset() {
        KEYBINDS.values().forEach(keybind -> keybind.getActionState().setJustPressed(false));
        inputQueue.clear();
    }

    public static Keybind getKeybind(InputAction action) {
        return KEYBINDS.get(action);
    }

    public static List<Keybind> getKeybinds(int keyCode) {
        List<Keybind> keybinds = new ArrayList<>();
        for (Keybind keybind : KEYBINDS.values()) {
            if (keybind.getKeyCode() == keyCode) keybinds.add(keybind);
        }
        return keybinds;
    }

    public static void rebind(InputAction action, int keyCode) {
        KEYBINDS.put(action, new Keybind(action, keyCode));
    }

    public static boolean isActionPressed(InputAction action) {
        return getKeybind(action).getActionState().isPressed();
    }

    public static boolean isActionJustPressed(InputAction action) {
        return getKeybind(action).getActionState().isJustPressed();
    }

    public static Queue<KeyEvent> getInputQueue() {
        return inputQueue;
    }

    public static void quit() {
        ControllerSystem.quit();
    }

    public static HashMap<InputAction, Integer> getDefaultKeybinds() {
        HashMap<InputAction, Integer> defaultKeybinds = new HashMap<>();
        for (InputAction action : InputAction.values()) {
            defaultKeybinds.put(action, action.getDefaultBind());
        }
        return defaultKeybinds;
    }

}
