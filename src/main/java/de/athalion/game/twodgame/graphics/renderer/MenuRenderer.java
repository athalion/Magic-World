package de.athalion.game.twodgame.graphics.renderer;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.main.GameInstance;

public class MenuRenderer implements Renderer {

    @Override
    public void draw(DrawContext context, Camera camera) {
        GameInstance.getInstance().getMenuManager().render(context);
    }

}
