package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Replacement;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.Settings;

import java.awt.*;

public class InputSettingsMenu implements MenuPage {

    final int maxCommandNum = 2;

    int commandNum = 0;

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1f, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(48f);

        String text = Translations.get("menu.input.title");
        int x = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 3;
        context.drawString(text, x, y);

        text = Translations.get("menu.input.controller", new Replacement("%a", (Settings.getSettings().enableController ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 5;
        context.drawString(text, x, y, commandNum == 0 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.input.controllerVibration", new Replacement("%a", (Settings.getSettings().enableControllerVibration ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 6;
        context.drawString(text, x, y, commandNum == 1 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.input.keybinds");
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 7;
        context.drawString(text, x, y, commandNum == 2 ? Color.ORANGE : Color.WHITE);

        // TODO
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
            switch (commandNum) {
                case 0:
                    Settings.getSettings().enableController = !Settings.getSettings().enableController;
                    break;
                case 1:
                    Settings.getSettings().enableControllerVibration = !Settings.getSettings().enableControllerVibration;
                    break;
                case 2:
                    newMenuPage = new KeybindsMenu();
                    break;
            }

        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            newMenuPage = new SettingsMenu();
        }

        return newMenuPage;
    }

}
