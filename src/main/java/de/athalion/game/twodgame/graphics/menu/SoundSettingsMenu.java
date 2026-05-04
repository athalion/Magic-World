package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Replacement;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.Settings;
import de.athalion.game.twodgame.sound.SoundSystem;

import java.awt.*;

public class SoundSettingsMenu implements MenuPage {

    int commandNum = 0;
    int maxCommandNum = 3;

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1F, Color.BLACK);

        context.setFontSize(48F);

        String text = Translations.get("menu.audio.title");
        int x = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 3;
        context.drawString(text, x, y, Color.WHITE);

        text = Translations.get("menu.audio.enable_sound", new Replacement("%a", (Settings.getSettings().enableSound ? Translations.get("setting.enabled") : Translations.get("setting.disabled"))));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 5;
        context.drawString(text, x, y, commandNum == 0 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.audio.music", new Replacement("%a", String.valueOf(Settings.getSettings().musicVolume)));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 6;
        context.drawString(text, x, y, commandNum == 1 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.audio.effects", new Replacement("%a", String.valueOf(Settings.getSettings().effectVolume)));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 7;
        context.drawString(text, x, y, commandNum == 2 ? Color.ORANGE : Color.WHITE);

        text = Translations.get("menu.audio.environment", new Replacement("%a", String.valueOf(Settings.getSettings().environmentVolume)));
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 8;
        context.drawString(text, x, y, commandNum == 3 ? Color.ORANGE : Color.WHITE);

//        MenuUtils.drawControlTips(context, "[W] hoch", "[S] runter", "[A] weniger", "[D] mehr", "[ENTER] auswählen", "[ESC] zurück");
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
        if (InputSystem.isActionJustPressed(InputAction.MENU_LEFT)) {
            switch (commandNum) {
                case 1:
                    if (Settings.getSettings().musicVolume > 0) Settings.getSettings().musicVolume -= 1;
                    break;
                case 2:
                    if (Settings.getSettings().effectVolume > 0) Settings.getSettings().effectVolume -= 1;
                    break;
                case 3:
                    if (Settings.getSettings().environmentVolume > 0) Settings.getSettings().environmentVolume -= 1;
                    break;
            }
            SoundSystem.updateVolume();
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_RIGHT)) {
            switch (commandNum) {
                case 1:
                    if (Settings.getSettings().musicVolume < 10) Settings.getSettings().musicVolume += 1;
                    break;
                case 2:
                    if (Settings.getSettings().effectVolume < 10) Settings.getSettings().effectVolume += 1;
                    break;
                case 3:
                    if (Settings.getSettings().environmentVolume < 10) Settings.getSettings().environmentVolume += 1;
                    break;
            }
            SoundSystem.updateVolume();
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_ENTER)) {
            if (commandNum == 0) {
                Settings.getSettings().enableSound = !Settings.getSettings().enableSound;
                SoundSystem.updateVolume();
            }
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            newMenuPage = new SettingsMenu();
        }

        return newMenuPage;
    }

}
