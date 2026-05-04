package de.athalion.game.twodgame.graphics.pipeline;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.graphics.renderer.Renderer;

import java.util.List;

public class RenderPipeline {

    List<Renderer> renderers;

    public RenderPipeline(List<Renderer> renderers) {
        this.renderers = renderers;
    }

    public void render(DrawContext context, Camera camera) {
        renderers.forEach(renderer -> renderer.draw(context, camera));
    }

}
