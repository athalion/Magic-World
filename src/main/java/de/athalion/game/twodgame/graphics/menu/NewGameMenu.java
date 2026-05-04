package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.save.state.SaveStateManager;
import de.athalion.game.twodgame.sound.SoundSystem;

import java.awt.*;
import java.awt.event.KeyEvent;

public class NewGameMenu implements MenuPage {
    
    StringBuilder input = new StringBuilder();

    @Override
    public void draw(DrawContext context) {
        context.fillScreen(1F, Color.BLACK);

        context.setColor(Color.WHITE);
        context.setFontSize(42F);

        String text = Translations.get("menu.new.title");
        int x = context.calculateXForCenteredText(text);
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 3;
        context.drawString(text, x, y);

        text = input.toString();
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().TILE_SIZE * 10;
        context.drawString(text, x, y);

//        MenuUtils.drawControlTips(context, "[ENTER] bestätigen", "[ESC] zurück");
    }

    @Override
    public MenuPage update() {
        MenuPage newMenuPage = null;

        if (InputSystem.isActionJustPressed(InputAction.MENU_ENTER)) {
            SaveStateManager.createSaveState(input.toString().trim());
            SoundSystem.stopMusic();
//            GameInstance.getInstance().gameState = GameState.PLAY;
        } else if (InputSystem.isActionJustPressed(InputAction.MENU_BACK)) {
            newMenuPage = new TitleMenu();
        }

        for (KeyEvent keyEvent : InputSystem.getInputQueue()) {
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
