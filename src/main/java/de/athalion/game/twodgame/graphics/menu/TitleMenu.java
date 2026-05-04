package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.resources.texture.Textures;

import java.awt.*;

public class TitleMenu implements MenuPage {

    Texture title = Textures.setup(Identifier.forPath("/menu/title/title.png"), false);
    Texture background = Textures.setup(Identifier.forPath("/menu/title/menu_background.png"), false);

    private int commandNum = 0;

    @Override
    public void draw(DrawContext context) {

        context.fillScreen(1f, Color.BLACK);

        double ratio = (double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / background.getWidth();
        int height = (int) (background.getHeight() * ratio);

        context.drawTexture(background, 0, GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2 - (height / 2), GameInstance.getInstance().getGameFrame().SCREEN_WIDTH, height);

        ratio = (GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 1.5) / title.getWidth();
        int width = (int) (title.getWidth() * ratio);
        height = (int) (title.getHeight() * ratio);

        context.drawTexture(title, GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2 - (width / 2), GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT * 2, width, height);

        context.setFontSize(48f);

        String text = Translations.get("menu.main.new");
        int x = context.calculateXForCenteredText(text);
        int y = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2 + (GameInstance.getInstance().getGameFrame().TILE_SIZE);
        context.drawString(text, x, y, commandNum == 0 ? Color.ORANGE : Color.BLACK);

        text = Translations.get("menu.main.load");
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2 + (GameInstance.getInstance().getGameFrame().TILE_SIZE * 2);
        context.drawString(text, x, y, commandNum == 1 ? Color.ORANGE : Color.BLACK);

        text = Translations.get("menu.main.settings");
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2 + (GameInstance.getInstance().getGameFrame().TILE_SIZE * 3);
        context.drawString(text, x, y, commandNum == 2 ? Color.ORANGE : Color.BLACK);

        text = Translations.get("menu.main.quit");
        x = context.calculateXForCenteredText(text);
        y = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2 + (GameInstance.getInstance().getGameFrame().TILE_SIZE * 4);
        context.drawString(text, x, y, commandNum == 3 ? Color.ORANGE : Color.BLACK);

//        MenuUtils.drawControlTips(context, "[W] hoch", "[S] runter", "[ENTER] auswählen");

    }

    @Override
    public MenuPage update() {
        MenuPage newMenuPage = null;

        if (InputSystem.isActionJustPressed(InputAction.MENU_UP)) {
            commandNum--;
            if (commandNum < 0) commandNum = 3;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_DOWN)) {
            commandNum++;
            if (commandNum > 3) commandNum = 0;
        }
        if (InputSystem.isActionJustPressed(InputAction.MENU_ENTER)) {
            switch (commandNum) {
                case 0:
                    newMenuPage = new NewGameMenu();
                    break;
                case 1:
                    newMenuPage = new LoadGameMenu();
                    break;
                case 2:
                    newMenuPage = new SettingsMenu();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }

        }

        return newMenuPage;
    }

}
