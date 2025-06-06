package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Replacement;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class InputSettingsMenu implements MenuPage {

    final GamePanel gamePanel;

    int commandNum = 0;
    int maxCommandNum = 2;

    public InputSettingsMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = Translations.get("menu.input.title");
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        maxCommandNum = 2;

        text = Translations.get("menu.input.controller", new Replacement("%a", (gamePanel.settings.enableController ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = gamePanel.tileSize * 5;
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "Grafik";
        y = gamePanel.tileSize * 6;
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "Audio";
        y = gamePanel.tileSize * 7;
        if (commandNum == 2) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        MenuUtils.drawControlTips(g2, gamePanel, "[W] hoch", "[S] runter", "[ENTER] auswählen", "[ESC] zurück");

    }

    @Override
    public MenuPage acceptInput(KeyState keyState, KeyEvent keyEvent) {

        MenuPage newMenuPage = null;

        if (keyState.isMenuUpPressed()) {
            commandNum--;
            if (commandNum < 0) commandNum = 2;
        }
        if (keyState.isMenuDownPressed()) {
            commandNum++;
            if (commandNum > 2) commandNum = 0;
        }
        if (keyState.isMenuOKPressed()) {
            switch (commandNum) {
                case 0:
                    gamePanel.settings.enableController = !gamePanel.settings.enableController;
                    break;
                case 1:

                    break;
                case 2:

                    break;
            }

        }
        if (keyState.isMenuBackPressed()) {
            newMenuPage = new SettingsMenu(gamePanel);
        }

        return newMenuPage;

    }

}
