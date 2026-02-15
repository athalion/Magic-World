package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Replacement;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.sound.SoundSystem;
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

        String text = Translations.get("menu.audio.title");
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        maxCommandNum = 3;

        text = Translations.get("menu.audio.enable_sound", new Replacement("%a", (gamePanel.settings.enableSound ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = gamePanel.tileSize * 5;
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = Translations.get("menu.audio.music", new Replacement("%a", String.valueOf(gamePanel.settings.musicVolume)));
        y = gamePanel.tileSize * 6;
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = Translations.get("menu.audio.effects", new Replacement("%a", String.valueOf(gamePanel.settings.effectVolume)));
        y = gamePanel.tileSize * 7;
        if (commandNum == 2) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = Translations.get("menu.audio.environment", new Replacement("%a", String.valueOf(gamePanel.settings.environmentVolume)));
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
                    if (gamePanel.settings.effectVolume != 0) gamePanel.settings.effectVolume -= 1;
                    break;
                case 3:
                    if (gamePanel.settings.environmentVolume != 0) gamePanel.settings.environmentVolume -= 1;
                    break;
            }
            SoundSystem.updateVolume(gamePanel.settings);
        }
        if (keyState.isMenuLeftPressed()) {
            switch (commandNum) {
                case 1:
                    if (gamePanel.settings.musicVolume != 10) gamePanel.settings.musicVolume += 1;
                    break;
                case 2:
                    if (gamePanel.settings.effectVolume != 10) gamePanel.settings.effectVolume += 1;
                    break;
                case 3:
                    if (gamePanel.settings.environmentVolume != 10) gamePanel.settings.environmentVolume += 1;
                    break;
            }
            SoundSystem.updateVolume(gamePanel.settings);
        }
        if (keyState.isMenuOKPressed()) {
            if (commandNum == 0) {
                gamePanel.settings.enableSound = !gamePanel.settings.enableSound;
                SoundSystem.updateVolume(gamePanel.settings);
            }
        }
        if (keyState.isMenuBackPressed()) {
            newMenuPage = new SettingsMenu(gamePanel);
        }

        return newMenuPage;

    }

}
