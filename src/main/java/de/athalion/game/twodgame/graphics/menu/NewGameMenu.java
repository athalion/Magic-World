package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.save.SaveStateManager;
import de.athalion.game.twodgame.sound.SoundSystem;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class NewGameMenu implements MenuPage {

    GamePanel gamePanel;

    StringBuilder input = new StringBuilder();

    public NewGameMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));

        String text = Translations.get("menu.new.title");
        int x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        text = input.toString();
        x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        y = gamePanel.tileSize * 10;
        g2.drawString(text, x, y);

        MenuUtils.drawControlTips(g2, gamePanel, "[ENTER] bestätigen", "[ESC] zurück");

    }

    @Override
    public MenuPage acceptInput(KeyState keyState, KeyEvent keyEvent) {

        MenuPage newMenuPage = null;

        if (keyState.isMenuOKPressed()) {
            SaveStateManager.createSaveState(input.toString().trim());
            SoundSystem.stopMusic();
            gamePanel.gameState = GameState.PLAY;
        } else if (keyState.isMenuBackPressed()) {
            newMenuPage = new TitleMenu(gamePanel);
        } else if (keyEvent != null) {
            int code = keyEvent.getKeyCode();
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (!input.isEmpty()) {
                    input.deleteCharAt(input.length() - 1);
                }
            } else {
                int type = Character.getType(code);
                if (type == Character.LOWERCASE_LETTER || type == Character.UPPERCASE_LETTER || Character.isSpaceChar(code)) {
                    input.append(keyEvent.getKeyChar());
                }
            }
        }

        return newMenuPage;

    }

}
