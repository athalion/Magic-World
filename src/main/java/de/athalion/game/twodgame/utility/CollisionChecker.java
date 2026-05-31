package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.math.Rectangle;
import de.athalion.game.twodgame.world.WorldInstance;
import de.athalion.game.twodgame.world.tile.TileManager;
import de.athalion.game.twodgame.world.tile.TileSet;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CollisionChecker {

    public static boolean checkTile(Entity entity) {
        WorldInstance world = entity.getWorld();
        TileManager tileManager = world.getTileManager();
        TileSet tileSet = tileManager.getTileSet();

        Rectangle futurePosition = entity.getHitbox();
        futurePosition.move(entity.getVelocity());



        return false;
    }

    public static @Nullable Entity checkEntities(Entity entity, List<Entity> targets) {
        Entity collidedEntity = null;

        Rectangle futureHitbox = entity.getHitbox();
        futureHitbox.move(entity.getVelocity());
        for (Entity target : targets) {
            if (target != entity) {
                Rectangle hitBox = target.getHitbox();
                if (futureHitbox.intersects(hitBox)) collidedEntity = target;
            }
        }

        return collidedEntity;
    }

}
