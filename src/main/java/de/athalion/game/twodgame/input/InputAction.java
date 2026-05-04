package de.athalion.game.twodgame.input;

import com.studiohartman.jamepad.ControllerState;

import java.awt.event.KeyEvent;

public enum InputAction {

    MENU_UP(KeyEvent.VK_W, controllerState -> new InputActionState(controllerState.dpadUp, controllerState.dpadUpJustPressed)),
    MENU_DOWN(KeyEvent.VK_S, controllerState -> new InputActionState(controllerState.dpadDown, controllerState.dpadDownJustPressed)),
    MENU_LEFT(KeyEvent.VK_A, controllerState -> new InputActionState(controllerState.dpadLeft, controllerState.dpadLeftJustPressed)),
    MENU_RIGHT(KeyEvent.VK_D, controllerState -> new InputActionState(controllerState.dpadRight, controllerState.dpadRightJustPressed)),
    MENU_ENTER(KeyEvent.VK_ENTER, controllerState -> new InputActionState(controllerState.a, controllerState.aJustPressed)),
    MENU_BACK(KeyEvent.VK_ESCAPE, controllerState -> new InputActionState(controllerState.b, controllerState.bJustPressed)),

    MOVE_UP(KeyEvent.VK_W, controllerState -> new InputActionState(controllerState.leftStickY >= 0.7f, false)),
    MOVE_DOWN(KeyEvent.VK_S, controllerState -> new InputActionState(controllerState.leftStickY <= -0.7f, false)),
    MOVE_LEFT(KeyEvent.VK_A, controllerState -> new InputActionState(controllerState.leftStickX <= -0.7f, false)),
    MOVE_RIGHT(KeyEvent.VK_D, controllerState -> new InputActionState(controllerState.rightStickX >= 0.7f, false)),
    ATTACK(KeyEvent.VK_ENTER, controllerState -> new InputActionState(controllerState.a, controllerState.aJustPressed)),

    INVENTORY(KeyEvent.VK_E, controllerState -> new InputActionState(controllerState.y, controllerState.yJustPressed)),

    ESCAPE(KeyEvent.VK_ESCAPE, controllerState -> new InputActionState(controllerState.start, controllerState.startJustPressed));

    private final int defaultBind;
    private final InputStateExtractor controllerStateExtractor;

    InputAction(int defaultBind, InputStateExtractor controllerStateExtractor) {
        this.defaultBind = defaultBind;
        this.controllerStateExtractor = controllerStateExtractor;
    }

    public int getDefaultBind() {
        return defaultBind;
    }

    public InputActionState extractControllerState(ControllerState controllerState) {
        return controllerStateExtractor.extract(controllerState);
    }

}
