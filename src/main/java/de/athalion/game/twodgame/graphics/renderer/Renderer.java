package de.athalion.game.twodgame.graphics.renderer;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;

public interface Renderer {

    void draw(DrawContext context, Camera camera);

}
