package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.Settings;

import java.awt.*;

public class SettingsMenu implements MenuPage {

    int commandNum = 0;
    int maxCommandNum = 2;

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1F, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(48F);

        String text = Translations.get("menu.settings.title");
        int x = context.calculateXForCenteredText(text);
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 3;
        context.drawString(text, x, y);

        text = Translations.get("menu.settings.input");
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 6;
        context.drawString(text, x, y, commandNum == 0 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.settings.graphics");
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 8;
        context.drawString(text, x, y, commandNum == 1 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.settings.audio");
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 10;
        context.drawString(text, x, y, commandNum == 2 ? Color.ORANGE : Color.WHITE);

//        MenuUtils.drawControlTips(context, "[W] hoch", "[S] runter", "[ENTER] auswählen", "[ESC] zurück");
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
            newMenuPage = switch (commandNum) {
                case 0 -> new InputSettingsMenu();
                case 1 -> new GraphicsSettingsMenu();
                case 2 -> new SoundSettingsMenu();
                default -> newMenuPage;
            };

        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            Settings.saveSettings();
            newMenuPage = new TitleMenu();
        }

        return newMenuPage;
    }

}
