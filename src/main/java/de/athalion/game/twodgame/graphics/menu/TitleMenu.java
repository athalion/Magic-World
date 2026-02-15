package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TitleMenu implements MenuPage {

    GamePanel gamePanel;

    BufferedImage title;
    //    BufferedImage background;
    BufferedImage background1;
    BufferedImage background2;
    BufferedImage background3;
    BufferedImage background4;

    int commandNum = 0;
    int maxCommandNum = 3;

    int targetX, targetY = 0;
    int x, y = 0;
    int updateTimer = 0;

    Random random = new Random();

    public TitleMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        title = RenderUtils.loadImage("/menu/title/title", getClass());
//        background = RenderUtils.loadImage("/menu/title/menu_background", getClass());
        background1 = RenderUtils.loadImage("/menu/title/background4", getClass());
        background2 = RenderUtils.loadImage("/menu/title/background1", getClass());
        background3 = RenderUtils.loadImage("/menu/title/background2", getClass());
        background4 = RenderUtils.loadImage("/menu/title/background3", getClass());
    }

    @Override
    public void draw(Graphics2D g2) {
        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        if (updateTimer >= 300) {
            targetX = random.nextInt(background1.getWidth() / 5) - background1.getWidth() / 10;
            targetY = random.nextInt(background1.getHeight() / 5) - background1.getHeight() / 10;
            updateTimer = 0;
        } else updateTimer++;

        x += (targetX - x) / 30;
        y += (targetY - y) / 30;

        double ratio = (double) gamePanel.screenWidth / ((double) background1.getWidth() / 5 * 4);
        int height = (int) (background1.getHeight() * ratio);
        g2.drawImage(background1, x / 4, y / 4, gamePanel.screenWidth, height, null);

        g2.drawImage(background2, x / 3, y / 3, gamePanel.screenWidth, height, null);

        g2.drawImage(background3, x / 2, y / 2, gamePanel.screenWidth, height, null);

        ratio = (gamePanel.screenWidth / 1.5) / title.getWidth();
        int width = (int) (title.getWidth() * ratio);
        height = (int) (title.getHeight() * ratio);
        g2.drawImage(title, gamePanel.screenWidth / 2 - (width / 2), gamePanel.tileSize * 2, width, height, null);

        g2.drawImage(background4, x, y, gamePanel.screenWidth, height, null);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = Translations.get("menu.main.new");
        int x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        int y = gamePanel.screenHeight / 2 + (gamePanel.tileSize);
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        text = Translations.get("menu.main.load");
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        text = Translations.get("menu.main.settings");
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 3);
        if (commandNum == 2) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        text = Translations.get("menu.main.quit");
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
            if (commandNum < 0) commandNum = maxCommandNum;
        }
        if (keyState.isMenuDownPressed()) {
            commandNum++;
            if (commandNum > maxCommandNum) commandNum = 0;
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
                    System.exit(0);
                    break;
            }

        }

        return newMenuPage;

    }

}
