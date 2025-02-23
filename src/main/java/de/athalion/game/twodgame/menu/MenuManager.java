package de.athalion.game.twodgame.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuManager {

    GamePanel gamePanel;

    MenuPage menuPage;
    MenuPage newMenuPage;

    int fadeTimer = -1;

    public MenuManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        menuPage = new TitleMenu(gamePanel);
    }

    public void render(Graphics2D g2) {
        menuPage.draw(g2);

        if (fadeTimer >= 0) {
            fadeTimer++;
            if (fadeTimer < 20) {
                RenderUtils.fillScreenBlack((float) fadeTimer / 20, g2, gamePanel);
            } else if (fadeTimer == 21) {
                menuPage = newMenuPage;
                RenderUtils.fillScreenBlack(1F, g2, gamePanel);
            } else {
                RenderUtils.fillScreenBlack((float) (40 - fadeTimer) / 20, g2, gamePanel);
            }
            if (fadeTimer == 40) {
                fadeTimer = -1;
            }
        }
    }

    public void acceptInput(KeyState keyState, KeyEvent keyEvent) {
        MenuPage newMenuPage = menuPage.acceptInput(keyState, keyEvent);
        if (newMenuPage != null) {
            this.newMenuPage = newMenuPage;
            fadeTimer = 0;
        }
    }

}
