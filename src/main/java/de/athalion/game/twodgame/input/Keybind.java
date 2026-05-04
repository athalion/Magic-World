package de.athalion.game.twodgame.input;

public class Keybind {

    InputAction inputAction;
    int keyCode;
    InputActionState actionState = new InputActionState(false, false);

    public Keybind(InputAction inputAction, int keyCode) {
        this.inputAction = inputAction;
        this.keyCode = keyCode;
    }

    public InputAction getInputAction() {
        return inputAction;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public InputActionState getActionState() {
        return actionState;
    }

}
