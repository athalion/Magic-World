package de.athalion.game.twodgame.input;

public class InputActionState {

    boolean pressed;
    boolean justPressed;

    public InputActionState(boolean pressed, boolean justPressed) {
        this.pressed = pressed;
        this.justPressed = justPressed;
    }

    public void with(InputActionState other) {
        this.pressed = this.pressed || other.pressed;
        this.justPressed = this.justPressed || other.justPressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isJustPressed() {
        return justPressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setJustPressed(boolean justPressed) {
        this.justPressed = justPressed;
    }

}
