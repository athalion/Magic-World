package de.athalion.game.twodgame.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GraphicsSettingsMenu implements MenuPage {

    GamePanel gamePanel;

    int commandNum = 0;
    int maxCommandNum = 2;

    public GraphicsSettingsMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = "Einstellungen - Grafik";
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        maxCommandNum = 2;

        text = "Vollbild - " + (gamePanel.settings.fullscreen ? "Ein" : "Aus");
        y = gamePanel.tileSize * 5;
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "Musik Lautstärke - " + gamePanel.settings.musicVolume * 10 + "%";
        y = gamePanel.tileSize * 6;
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "Effekt Lautstärke - " + gamePanel.settings.FXVolume * 10 + "%";
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
            if (commandNum < 0) commandNum = maxCommandNum;
        }
        if (keyState.isMenuDownPressed()) {
            commandNum++;
            if (commandNum > maxCommandNum) commandNum = 0;
        }
        if (keyState.isMenuOKPressed()) {
            switch (commandNum) {
                case 0:
                    gamePanel.settings.fullscreen = !gamePanel.settings.fullscreen;
                    gamePanel.setFullScreen(gamePanel.settings.fullscreen);
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
