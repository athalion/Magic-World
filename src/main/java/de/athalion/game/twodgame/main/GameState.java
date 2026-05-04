package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.graphics.pipeline.RenderPipeline;
import de.athalion.game.twodgame.graphics.pipeline.RenderPipelines;
import de.athalion.game.twodgame.main.update.*;

public enum GameState {

    MENU(RenderPipelines.MENU_PIPELINE, new MenuUpdater()),
    PLAY(RenderPipelines.PLAY_PIPELINE, new PlayUpdater()),
    PAUSE(RenderPipelines.PLAY_PIPELINE, new PauseUpdater()),
    CUTSCENE(RenderPipelines.PLAY_PIPELINE, new CutsceneUpdater());

    private final RenderPipeline renderPipeline;
    private final Updater updater;

    GameState(RenderPipeline renderPipeline, Updater updater) {
        this.renderPipeline = renderPipeline;
        this.updater = updater;
    }

    public RenderPipeline getRenderPipeline() {
        return renderPipeline;
    }

    public Updater getUpdater() {
        return updater;
    }

}
