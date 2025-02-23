package de.athalion.game.twodgame.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SoundSettingsMenu implements MenuPage {

    GamePanel gamePanel;

    int commandNum = 0;
    int maxCommandNum = 2;

    public SoundSettingsMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = "Einstellungen - Audio";
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        maxCommandNum = 3;

        text = "Sound aktivieren - " + (gamePanel.settings.enableSound ? "Ein" : "Aus");
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

        text = "Umbebungs Lautstärke - " + gamePanel.settings.environmentVolume * 10 + "%";
        y = gamePanel.tileSize * 8;
        if (commandNum == 3) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        MenuUtils.drawControlTips(g2, gamePanel, "[W] hoch", "[S] runter", "[A] weniger", "[D] mehr", "[ENTER] auswählen", "[ESC] zurück");

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
        if (keyState.isMenuRightPressed()) {
            switch (commandNum) {
                case 1:
                    if (gamePanel.settings.musicVolume != 0) gamePanel.settings.musicVolume -= 1;
                    break;
                case 2:
                    if (gamePanel.settings.FXVolume != 0) gamePanel.settings.FXVolume -= 1;
                    break;
                case 3:
                    if (gamePanel.settings.environmentVolume != 0) gamePanel.settings.environmentVolume -= 1;
                    break;
            }
            gamePanel.updateVolume();
        }
        if (keyState.isMenuLeftPressed()) {
            switch (commandNum) {
                case 1:
                    if (gamePanel.settings.musicVolume != 10) gamePanel.settings.musicVolume += 1;
                    break;
                case 2:
                    if (gamePanel.settings.FXVolume != 10) gamePanel.settings.FXVolume += 1;
                    break;
                case 3:
                    if (gamePanel.settings.environmentVolume != 10) gamePanel.settings.environmentVolume += 1;
                    break;
            }
            gamePanel.updateVolume();
        }
        if (keyState.isMenuOKPressed()) {
            if (commandNum == 0) {
                gamePanel.settings.enableSound = !gamePanel.settings.enableSound;
                gamePanel.updateVolume();
            }
        }
        if (keyState.isMenuBackPressed()) {
            newMenuPage = new SettingsMenu(gamePanel);
        }

        return newMenuPage;

    }

}
