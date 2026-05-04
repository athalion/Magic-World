package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;

import java.awt.*;

public class MenuManager {

    MenuPage menuPage;
    MenuPage newMenuPage;

    int fadeTimer = -1;

    public MenuManager() {
        menuPage = new TitleMenu();
    }

    public void render(DrawContext context) {
        menuPage.draw(context);

        if (fadeTimer >= 0) {
            fadeTimer++;
            context.setColor(Color.BLACK);
            if (fadeTimer < 20) {
                context.fillScreen((float) fadeTimer / 20);
            } else if (fadeTimer == 21) {
                menuPage = newMenuPage;
                context.fillScreen(1f);
            } else {
                context.fillScreen((float) (40 - fadeTimer) / 20);
            }
            if (fadeTimer == 40) {
                fadeTimer = -1;
            }
        }
    }

    public void update() {
        if (fadeTimer >= 0) return;
        MenuPage newMenuPage = menuPage.update();
        if (newMenuPage != null) {
            this.newMenuPage = newMenuPage;
            fadeTimer = 0;
        }
    }

}
