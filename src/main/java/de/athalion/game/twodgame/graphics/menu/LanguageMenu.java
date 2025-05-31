package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.lang.Language;
import de.athalion.game.twodgame.lang.Languages;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class LanguageMenu implements MenuPage {

    GamePanel gamePanel;

    int commandNum;
    int maxCommandNum;

    List<Language> languages;
    float displayY = 0;

    public LanguageMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        languages = Languages.list();
        commandNum = languages.indexOf(Translations.getCurrentLanguage());
        maxCommandNum = languages.size() - 1;
    }

    @Override
    public void draw(Graphics2D g2) {
        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = Translations.get("menu.language.title");
        int x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        int y = gamePanel.tileSize;
        g2.drawString(text, x, y);

        displayY += ((-commandNum * gamePanel.tileSize) - displayY) / 16;
        for (int i = 0; i < languages.size(); i++) {
            Language language = languages.get(i);
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
                            Math.max((int) (255 - (distance / gamePanel.tileSize * 255)), 0)
                    ));
                }
                x = RenderUtils.getXForCenteredText(language.getName(), g2, gamePanel);
                g2.drawString(language.getName(), x, y);
            }
        }
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
            Language newLanguage = languages.get(commandNum);
            Translations.changeLanguage(newLanguage);
            gamePanel.settings.language = newLanguage.getId();
        }
        if (keyState.isMenuBackPressed()) {
            newMenuPage = new GraphicsSettingsMenu(gamePanel);
        }

        return newMenuPage;
    }

}
