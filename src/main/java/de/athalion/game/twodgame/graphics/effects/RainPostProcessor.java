package de.athalion.game.twodgame.graphics.effects;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.particle.Particles;
import de.athalion.game.twodgame.world.WorldInstance;
import de.athalion.game.twodgame.world.tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainPostProcessor implements PostProcessor {

    List<Vector> rainPositions = new ArrayList<>();

    @Override
    public void process(DrawContext context, Camera camera) {
        WorldInstance worldInstance = camera.getWorldInstance();
        TileManager tileManager = worldInstance.getTileManager();
        Random random = new Random();

        for (int i = 0; i < tileManager.getWorldWidth() / 2; i++) {
            rainPositions.add(new Vector(
                    random.nextInt(tileManager.getWorldWidth() * GameInstance.getInstance().getGameFrame().TILE_SIZE),
                    -10
            ));
        }

        context.setColor(new Color(100, 100, 255, 100));
        for (Vector rainPosition : rainPositions) {
            Vector screenPosition = rainPosition.clone().subtract(
                    camera.getCameraConfiguration().getPosition().getX() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2),
                    camera.getCameraConfiguration().getPosition().getY() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2)
            );

            if (screenPosition.getX() + GameInstance.getInstance().getGameFrame().TILE_SIZE >= 0
                    && screenPosition.getX() - GameInstance.getInstance().getGameFrame().TILE_SIZE < GameInstance.getInstance().getGameFrame().SCREEN_WIDTH
                    && screenPosition.getY() + GameInstance.getInstance().getGameFrame().TILE_SIZE >= 0
                    && screenPosition.getY() - GameInstance.getInstance().getGameFrame().TILE_SIZE < GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT) {
                context.fillRect((int) screenPosition.getY(), (int) screenPosition.getY(), 3, 15);
            }

            rainPosition.add(0, 10);
        }

        Vector rainPosition = rainPositions.get(random.nextInt(rainPositions.size()));
        worldInstance.spawnParticle(Particles.SPLASH, rainPosition.toLocation(worldInstance));
        rainPositions.removeIf(positions -> positions.getY() - 20 > tileManager.getWorldHeight() * GameInstance.getInstance().getGameFrame().TILE_SIZE);
    }

}
