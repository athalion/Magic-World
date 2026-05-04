package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;

public interface MenuPage {

    void draw(DrawContext context);

    MenuPage update();

}
