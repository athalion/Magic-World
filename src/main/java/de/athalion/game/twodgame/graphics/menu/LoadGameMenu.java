package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class LoadGameMenu implements MenuPage {

    GamePanel gamePanel;

    List<String> saves;

    int commandNum = 0;
    int maxCommandNum = 0;

    public LoadGameMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        saves = gamePanel.getSaves();
        maxCommandNum = saves.size() - 1;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        int y = gamePanel.tileSize;
        int width = gamePanel.tileSize * 7;
        int height = gamePanel.tileSize;
        int x = MenuUtils.getXForCenteredSomething(width, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 46F));
        String text = "Spielstand auswählen!";
        g2.drawString(text, RenderUtils.getXForCenteredText(text, g2, gamePanel), y);

        y += gamePanel.tileSize;

        int i = commandNum;
        if ((saves.size() - commandNum) <= 9) i = Math.max(saves.size() - 9, 0);

        while (i < saves.size()) {
            if (commandNum == i) {
                g2.setColor(Color.WHITE);
            } else g2.setColor(Color.GRAY);
            g2.fillRect(x + 1, y + 1, width - 2, height - 2);
            if (commandNum == i) {
                g2.setColor(Color.DARK_GRAY);
            } else g2.setColor(Color.BLACK);
            g2.fillRect(x + 4, y + 4, width - 8, height - 8);
            if (commandNum == i) {
                g2.setColor(Color.ORANGE);
            } else g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
            g2.drawString(saves.get(i), x + 13, y + 32);

            y += gamePanel.tileSize;
            i++;

            if (y >= gamePanel.tileSize * 11) break;
        }

        MenuUtils.drawControlTips(g2, gamePanel, "[A] hoch", "[S] runter", "[ENTER] auswählen", "[ESC] zurück");

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
            gamePanel.loadGame(saves.get(commandNum));
            gamePanel.stopMusic();
            gamePanel.gameState = GameState.PLAY;
        }
        if (keyState.isMenuBackPressed()) {
            newMenuPage = new TitleMenu(gamePanel);
        }

        return newMenuPage;

    }

}
