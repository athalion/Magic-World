package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface MenuPage {

    void draw(Graphics2D g2);

    MenuPage acceptInput(KeyState keyState, KeyEvent keyEvent);

}
