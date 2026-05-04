package de.athalion.game.twodgame.graphics.pipeline;

import de.athalion.game.twodgame.graphics.renderer.Renderers;

import java.util.List;

public class RenderPipelines {

    public final static RenderPipeline MENU_PIPELINE = new RenderPipeline(List.of(Renderers.MENU_RENDERER));
    public final static RenderPipeline PLAY_PIPELINE = new RenderPipeline(List.of(Renderers.WORLD_RENDERER, Renderers.ENTITY_RENDERER));

}
