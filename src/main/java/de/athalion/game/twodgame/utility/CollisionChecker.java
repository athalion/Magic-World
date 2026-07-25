package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.math.Rectangle;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.world.WorldInstance;
import de.athalion.game.twodgame.world.tile.Tile;
import de.athalion.game.twodgame.world.tile.TileManager;
import de.athalion.game.twodgame.world.tile.TileSet;

import java.util.ArrayList;
import java.util.List;

public class CollisionChecker {

    public static boolean checkTile(Entity entity, Vector velocity) {
        WorldInstance world = entity.getWorld();
        TileManager tileManager = world.getTileManager();
        TileSet tileSet = tileManager.getTileSet();

        Rectangle futureHitbox = entity.getHitbox().move(velocity);

        int tileX = (int) (futureHitbox.getX() / GameInstance.getInstance().getGameFrame().TILE_SIZE);
        int tileY = (int) (futureHitbox.getY() / GameInstance.getInstance().getGameFrame().TILE_SIZE);
        int tileX2 = (int) (futureHitbox.getX() + futureHitbox.getWidth() / GameInstance.getInstance().getGameFrame().TILE_SIZE);
        int tileY2 = (int) (futureHitbox.getY() + futureHitbox.getHeight() / GameInstance.getInstance().getGameFrame().TILE_SIZE);

        for (int x = Math.max(0, tileX); x < Math.min(tileManager.getWorldWidth(), tileX2); x++) {
            for (int y = Math.max(0, tileY); y < Math.min(tileManager.getWorldHeight(), tileY2); y++) {
                Tile tile = tileSet.getTile(tileManager.getMap()[x][y]);

                if (tile.hasCollision()) return true;
            }
        }

        return false;
    }

    public static List<Entity> checkEntities(Entity entity, Vector velocity, List<Entity> targets) {
        Rectangle futureHitbox = entity.getHitbox().move(velocity);

        List<Entity> collidedEntities = new ArrayList<>();

        for (Entity target : targets) {
            if (target != entity) {
                Rectangle hitBox = target.getHitbox();
                if (futureHitbox.intersects(hitBox)) collidedEntities.add(target);
            }
        }

        return collidedEntities;
    }

}
