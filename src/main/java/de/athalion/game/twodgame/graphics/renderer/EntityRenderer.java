package de.athalion.game.twodgame.graphics.renderer;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.location.LocatableObject;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.world.WorldInstance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntityRenderer implements Renderer {

    @Override
    public void draw(DrawContext context, Camera camera) {
        WorldInstance worldInstance = camera.getWorldInstance();
        List<LocatableObject> objects = new ArrayList<>(worldInstance.getEntities());
        objects.addAll(worldInstance.getParticles());

        objects.removeIf(locatableObject -> {
            int screenX = (int) (locatableObject.getLocation().getX() - camera.getCameraConfiguration().getPosition().getX() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2));
            int screenY = (int) (locatableObject.getLocation().getY() - camera.getCameraConfiguration().getPosition().getY() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2));

            return (screenX + GameInstance.getInstance().getGameFrame().TILE_SIZE < 0
                    || screenX - GameInstance.getInstance().getGameFrame().TILE_SIZE > GameInstance.getInstance().getGameFrame().SCREEN_WIDTH
                    || screenY + GameInstance.getInstance().getGameFrame().TILE_SIZE < 0
                    || screenY - GameInstance.getInstance().getGameFrame().TILE_SIZE > GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT);
        });

        objects.sort(Comparator.comparingInt(value -> value.getLocation().getY()));

        objects.forEach(locatableObject -> locatableObject.draw(
                context,
                (int) (locatableObject.getLocation().getX() - camera.getCameraConfiguration().getPosition().getX() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2)),
                (int) (locatableObject.getLocation().getY() - camera.getCameraConfiguration().getPosition().getY() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2))
        ));
    }

}
