package de.athalion.game.twodgame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class KeyHandler implements KeyListener {

    Consumer<KeyEvent> keyJustPressedCallback;
    Consumer<KeyEvent> keyPressedCallback;
    Consumer<KeyEvent> keyReleasedCallback;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyJustPressedCallback != null) keyJustPressedCallback.accept(e);
        if (keyPressedCallback != null) keyPressedCallback.accept(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyReleasedCallback != null) keyReleasedCallback.accept(e);
    }

    public void onKeyJustPressed(Consumer<KeyEvent> keyJustPressedCallback) {
        this.keyJustPressedCallback = keyJustPressedCallback;
    }

    public void onKeyPressed(Consumer<KeyEvent> keyPressedCallback) {
        this.keyPressedCallback = keyPressedCallback;
    }

    public void onKeyReleased(Consumer<KeyEvent> keyReleasedCallback) {
        this.keyReleasedCallback = keyReleasedCallback;
    }

}
