package de.athalion.game.twodgame.graphics.effects;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;

public interface PostProcessor {
    void process(DrawContext context, Camera camera);
}
