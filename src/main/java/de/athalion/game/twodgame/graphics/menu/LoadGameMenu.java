package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.state.SaveStateManager;
import de.athalion.game.twodgame.sound.SoundSystem;

import java.awt.*;
import java.util.List;

public class LoadGameMenu implements MenuPage {

    int commandNum = 0;
    int maxCommandNum;

    List<String> saves;
    float displayY = 0;

    public LoadGameMenu() {
        saves = SaveStateManager.getSaves();
        maxCommandNum = saves.size() - 1;
    }

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1F, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(48f);

        String text = Translations.get("menu.load.title");
        int x = context.calculateXForCenteredText(text);
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE;
        context.drawString(text, x, y);

        displayY += ((-commandNum * GameInstance.getInstance().getGameFrame().TILE_SIZE) - displayY) / 16;
        for (int i = 0; i < saves.size(); i++) {
            String save = saves.get(i);
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
                x = context.calculateXForCenteredText(save);
                context.drawString(save, x, y);
            }
        }

//        MenuUtils.drawControlTips(context, "[A] hoch", "[S] runter", "[ENTER] auswählen", "[ESC] zurück");
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
            // TODO implement game loading
//            GameInstance.getInstance().loadGame(saves.get(commandNum));
            SoundSystem.stopMusic();
//            GameInstance.getInstance().gameState = GameState.PLAY;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            newMenuPage = new TitleMenu();
        }

        return newMenuPage;
    }

}
