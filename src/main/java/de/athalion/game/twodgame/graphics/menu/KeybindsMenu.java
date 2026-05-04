package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class KeybindsMenu implements MenuPage {

    final List<InputAction> inputActions;
    final int maxCommandNum;

    int commandNum = 0;
    boolean changingBind = false;
    float displayY = 0;

    public KeybindsMenu() {
        inputActions = Arrays.stream(InputAction.values()).toList();
        maxCommandNum = inputActions.size() - 1;
    }

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1F, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(48f);

        String text = Translations.get("menu.keybinds.title");
        int x = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        context.drawString(text, x, y);

        displayY += ((-commandNum * GameInstance.getInstance().getGameFrame().TILE_SIZE) - displayY) / 16;
        for (int i = 0; i < inputActions.size(); i++) {
            InputAction inputAction = inputActions.get(i);
            y = (int) (displayY + ((i + 1.5) * GameInstance.getInstance().getGameFrame().TILE_SIZE) + ((float) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2));
            if (y > 0 && y < GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT + GameInstance.getInstance().getGameFrame().TILE_SIZE) {
                double distance = Math.max((3 * GameInstance.getInstance().getGameFrame().TILE_SIZE) - y, y - (GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT - (1.5 * GameInstance.getInstance().getGameFrame().TILE_SIZE)));
                if (i == commandNum) {
                    context.setColor(Color.ORANGE);
                    context.setFontSize(48F);
                } else {
                    context.setColor(Color.WHITE);
                    context.setFontSize(32F);
                }
                if (distance >= 0) {
                    context.setColor(new Color(
                            context.getColor().getRed(),
                            context.getColor().getGreen(),
                            context.getColor().getBlue(),
                            Math.max((int) (255 - (distance / (1.5 * GameInstance.getInstance().getGameFrame().TILE_SIZE) * 255)), 0)
                    ));
                }

                text = Translations.get("keybind." + inputAction.toString().toLowerCase());
                x = GameInstance.getInstance().getGameFrame().TILE_SIZE;
                context.drawString(text, x, y);

                int keyCode = InputSystem.getKeybind(inputAction).getKeyCode();
                int type = Character.getType(keyCode);
                text = (type == Character.UPPERCASE_LETTER || type == Character.LETTER_NUMBER) ? Character.toString(keyCode) : Character.getName(keyCode);
                x = GameInstance.getInstance().getGameFrame().TILE_SIZE * 11;
                context.drawString((changingBind && i == commandNum) ? "> " + text + " <" : text, x, y);
            }
        }
    }

    @Override
    public MenuPage update() {
        MenuPage newMenuPage = null;

        if (!changingBind) {
            if (InputSystem.isActionJustPressed(InputAction.MENU_UP)) {
                commandNum--;
                if (commandNum < 0) commandNum = maxCommandNum;
            }
            if (InputSystem.isActionJustPressed(InputAction.MENU_DOWN)) {
                commandNum++;
                if (commandNum > maxCommandNum) commandNum = 0;
            }
            if (InputSystem.isActionJustPressed(InputAction.MENU_ENTER)) {
                changingBind = true;
            }
            if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
                newMenuPage = new InputSettingsMenu();
            }
        } else {
            for (KeyEvent keyEvent : InputSystem.getInputQueue()) {
                if (keyEvent.getKeyCode() != KeyEvent.VK_ESCAPE) {
                    InputSystem.rebind(inputActions.get(commandNum), keyEvent.getKeyCode());
                }
                changingBind = false;
            }
        }

        return newMenuPage;
    }

}
