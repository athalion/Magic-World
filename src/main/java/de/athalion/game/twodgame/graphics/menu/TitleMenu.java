package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class TitleMenu implements MenuPage {

    GamePanel gamePanel;

    BufferedImage title;
    BufferedImage background;

    int commandNum = 0;
    int maxCommandNum = 3;

    public TitleMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        title = RenderUtils.loadImage("/menu/title/title", getClass());
        background = RenderUtils.loadImage("/menu/title/menu_background", getClass());
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        double ratio = (double) gamePanel.screenWidth / background.getWidth();
        int height = (int) (background.getHeight() * ratio);

        g2.drawImage(background, 0, gamePanel.screenHeight / 2 - (height / 2), gamePanel.screenWidth, height, null);

        ratio = (gamePanel.screenWidth / 1.5) / title.getWidth();
        int width = (int) (title.getWidth() * ratio);
        height = (int) (title.getHeight() * ratio);

        g2.drawImage(title, gamePanel.screenWidth / 2 - (width / 2), gamePanel.tileSize * 2, width, height, null);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = "NEUES SPIEL";
        int x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        int y = gamePanel.screenHeight / 2 + (gamePanel.tileSize);
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        text = "SPIEL LADEN";
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        text = "EINSTELLUNGEN";
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 3);
        if (commandNum == 2) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        text = "BEENDEN";
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 4);
        if (commandNum == 3) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        MenuUtils.drawControlTips(g2, gamePanel, "[W] hoch", "[S] runter", "[ENTER] auswählen");

    }

    @Override
    public MenuPage acceptInput(KeyState keyState, KeyEvent keyEvent) {

        MenuPage newMenuPage = null;

        if (keyState.isMenuUpPressed()) {
            commandNum--;
            if (commandNum < 0) commandNum = 3;
        }
        if (keyState.isMenuDownPressed()) {
            commandNum++;
            if (commandNum > 3) commandNum = 0;
        }
        if (keyState.isMenuOKPressed()) {
            switch (commandNum) {
                case 0:
                    newMenuPage = new NewGameMenu(gamePanel);
                    break;
                case 1:
                    newMenuPage = new LoadGameMenu(gamePanel);
                    break;
                case 2:
                    newMenuPage = new SettingsMenu(gamePanel);
                    break;
                case 3:
                    gamePanel.quit();
                    break;
            }

        }

        return newMenuPage;

    }

}
