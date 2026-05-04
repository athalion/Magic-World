package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Language;
import de.athalion.game.twodgame.lang.Languages;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.Settings;

import java.awt.*;
import java.util.List;

public class LanguageMenu implements MenuPage {

    int commandNum;
    int maxCommandNum;

    List<Language> languages;
    float displayY = 0;

    public LanguageMenu() {
        languages = Languages.list();
        commandNum = languages.indexOf(Translations.getCurrentLanguage());
        maxCommandNum = languages.size() - 1;
    }

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1f, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(48F);

        String text = Translations.get("menu.language.title");
        int x = context.calculateXForCenteredText(text);
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        context.drawString(text, x, y);

        displayY += ((-commandNum * GameInstance.getInstance().getGameFrame().TILE_SIZE) - displayY) / 16;
        for (int i = 0; i < languages.size(); i++) {
            Language language = languages.get(i);
            y = (int) (displayY + ((i + 1.5) * GameInstance.getInstance().getGameFrame().TILE_SIZE) + ((float) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2));
            if (y > 0 && y < GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT + GameInstance.getInstance().getGameFrame().TILE_SIZE) {
                double distance = Math.max((3 * GameInstance.getInstance().getGameFrame().TILE_SIZE) - y, y - (GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT - (1.5 * GameInstance.getInstance().getGameFrame().TILE_SIZE)));
                if (i == commandNum) {
                    context.setColor(Color.ORANGE);
                    context.setFontSize(48f);
                } else {
                    context.setColor(Color.WHITE);
                    context.setFontSize(32f);
                }
                if (distance >= 0) {
                    context.setColor(new Color(
                            context.getColor().getRed(),
                            context.getColor().getGreen(),
                            context.getColor().getBlue(),
                            Math.max((int) (255 - (distance / GameInstance.getInstance().getGameFrame().TILE_SIZE * 255)), 0)
                    ));
                }
                x = context.calculateXForCenteredText(language.getName());
                context.drawString(language.getName(), x, y);
            }
        }
    }

    @Override
    public MenuPage update() {
        MenuPage newMenuPage = null;

        if (InputSystem.isActionJustPressed(InputAction.MENU_UP)) {
            commandNum--;
            if (commandNum < 0) commandNum = maxCommandNum;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_DOWN)) {
            commandNum++;
            if (commandNum > maxCommandNum) commandNum = 0;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_ENTER)) {
            Language newLanguage = languages.get(commandNum);
            Translations.changeLanguage(newLanguage);
            Settings.getSettings().language = newLanguage.getId();
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            newMenuPage = new GraphicsSettingsMenu();
        }

        return newMenuPage;
    }

}
