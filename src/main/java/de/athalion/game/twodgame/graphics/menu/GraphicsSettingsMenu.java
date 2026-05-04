package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Replacement;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.Settings;

import java.awt.*;

public class GraphicsSettingsMenu implements MenuPage {

    int commandNum = 0;

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1F, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(48F);

        String text = "Einstellungen - Grafik";
        int x = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 3;
        context.drawString(text, x, y);

        text = Translations.get("menu.graphics.fullscreen", new Replacement("%a", (Settings.getSettings().fullscreen ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 5;
        context.drawString(text, x, y, commandNum == 0 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.graphics.hardware_acceleration", new Replacement("%a", (Settings.getSettings().hardwareAcceleration ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 6;
        context.drawString(text, x, y, commandNum == 1 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.graphics.language");
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 7;
        context.drawString(text, x, y, commandNum == 2 ? Color.ORANGE : Color.WHITE);

//        MenuUtils.drawControlTips(context, "[W] hoch", "[S] runter", "[ENTER] auswählen", "[ESC] zurück");
    }

    @Override
    public MenuPage update() {
        MenuPage newMenuPage = null;

        if (InputSystem.isActionJustPressed(InputAction.MENU_UP)) {
            commandNum--;
            if (commandNum < 0) commandNum = 2;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_DOWN)) {
            commandNum++;
            if (commandNum > 2) commandNum = 0;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_ENTER)) {
            switch (commandNum) {
                case 0:
                    Settings.getSettings().fullscreen = !Settings.getSettings().fullscreen;
                    GameInstance.getInstance().getGameFrame().setFullscreen(Settings.getSettings().fullscreen);
                    break;
                case 1:
                    Settings.getSettings().hardwareAcceleration = !Settings.getSettings().hardwareAcceleration;
                    System.setProperty("sun.java2d.opengl", Boolean.toString(Settings.getSettings().hardwareAcceleration));
                    break;
                case 2:
                    newMenuPage = new LanguageMenu();
                    break;
            }

        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            newMenuPage = new SettingsMenu();
        }

        return newMenuPage;
    }

}
