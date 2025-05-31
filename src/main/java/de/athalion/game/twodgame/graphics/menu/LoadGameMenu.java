package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.save.SaveStateManager;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class LoadGameMenu implements MenuPage {

    GamePanel gamePanel;

    int commandNum = 0;
    int maxCommandNum = 0;

    List<String> saves;
    float displayY = 0;

    public LoadGameMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        saves = SaveStateManager.getSaves();
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
        String text = Translations.get("menu.load.title");
        g2.drawString(text, RenderUtils.getXForCenteredText(text, g2, gamePanel), y);

        displayY += ((-commandNum * gamePanel.tileSize) - displayY) / 16;
        for (int i = 0; i < saves.size(); i++) {
            String save = saves.get(i);
            y = (int) (displayY + ((i + 1.5) * gamePanel.tileSize) + ((float) gamePanel.screenHeight / 2));
            if (y > 0 && y < gamePanel.screenHeight + gamePanel.tileSize) {
                double distance = Math.max((3 * gamePanel.tileSize) - y, y - (gamePanel.screenHeight - (1.5 * gamePanel.tileSize)));
                if (i == commandNum) {
                    g2.setColor(Color.ORANGE);
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
                } else {
                    g2.setColor(Color.WHITE);
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
                }
                if (distance >= 0) {
                    g2.setColor(new Color(
                            g2.getColor().getRed(),
                            g2.getColor().getGreen(),
                            g2.getColor().getBlue(),
                            Math.max((int) (255 - (distance / (1.5 * gamePanel.tileSize) * 255)), 0)
                    ));
                }
                x = RenderUtils.getXForCenteredText(save, g2, gamePanel);
                g2.drawString(save, x, y);
            }
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
