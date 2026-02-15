package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingsMenu implements MenuPage {

    GamePanel gamePanel;

    int commandNum = 0;
    int maxCommandNum = 2;

    public SettingsMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = Translations.get("menu.settings.title");
        int x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        text = Translations.get("menu.settings.input");
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.tileSize * 6;
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = Translations.get("menu.settings.graphics");
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.tileSize * 8;
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = Translations.get("menu.settings.audio");
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.tileSize * 10;
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
            newMenuPage = switch (commandNum) {
                case 0 -> new InputSettingsMenu(gamePanel);
                case 1 -> new GraphicsSettingsMenu(gamePanel);
                case 2 -> new SoundSettingsMenu(gamePanel);
                default -> newMenuPage;
            };

        }
        if (keyState.isMenuBackPressed()) {
            gamePanel.saveSettings();
            newMenuPage = new TitleMenu(gamePanel);
        }

        return newMenuPage;

    }

}
