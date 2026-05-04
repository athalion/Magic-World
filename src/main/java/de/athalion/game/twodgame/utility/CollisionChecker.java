package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.world.WorldInstance;
import de.athalion.game.twodgame.world.tile.TileManager;
import de.athalion.game.twodgame.world.tile.TileSet;

import java.awt.*;
import java.util.List;

public class CollisionChecker {

    public static boolean checkTile(Entity entity) {

        WorldInstance world = entity.getWorld();
        TileManager tileManager = world.getTileManager();
        TileSet tileSet = tileManager.getTileSet();

        int entityLeftWorldX = entity.getLocation().getX() + entity.hitbox.x;
        int entityRightWorldX = entity.getLocation().getX() + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.getLocation().getY() + entity.hitbox.y;
        int entityBottomWorldY = entity.getLocation().getY() + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeftWorldX / GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int entityRightCol = entityRightWorldX / GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int entityTopRow = entityTopWorldY / GameInstance.getInstance().getGameFrame().TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / GameInstance.getInstance().getGameFrame().TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case UP:

                entityTopRow = (int) ((entityTopWorldY - entity.getStat(Stats.SPEED)) / GameInstance.getInstance().getGameFrame().TILE_SIZE);
                tileNum1 = tileManager.getMap()[entityLeftCol][entityTopRow];
                tileNum2 = tileManager.getMap()[entityRightCol][entityTopRow];
                if (tileSet.getTile(tileNum1).collision || tileSet.getTile(tileNum2).collision)
                    return true;

                break;
            case DOWN:

                entityBottomRow = (int) ((entityBottomWorldY + entity.getStat(Stats.SPEED)) / GameInstance.getInstance().getGameFrame().TILE_SIZE);
                tileNum1 = tileManager.getMap()[entityLeftCol][entityBottomRow];
                tileNum2 = tileManager.getMap()[entityRightCol][entityBottomRow];
                if (tileSet.getTile(tileNum1).collision || tileSet.getTile(tileNum2).collision)
                    return true;

                break;
            case LEFT:

                entityLeftCol = (int) ((entityLeftWorldX - entity.getStat(Stats.SPEED)) / GameInstance.getInstance().getGameFrame().TILE_SIZE);
                tileNum1 = tileManager.getMap()[entityLeftCol][entityTopRow];
                tileNum2 = tileManager.getMap()[entityLeftCol][entityBottomRow];
                if (tileSet.getTile(tileNum1).collision || tileSet.getTile(tileNum2).collision)
                    return true;

                break;
            case RIGHT:

                entityRightCol = (int) ((entityRightWorldX + entity.getStat(Stats.SPEED)) / GameInstance.getInstance().getGameFrame().TILE_SIZE);
                tileNum1 = tileManager.getMap()[entityRightCol][entityTopRow];
                tileNum2 = tileManager.getMap()[entityRightCol][entityBottomRow];
                if (tileSet.getTile(tileNum1).collision || tileSet.getTile(tileNum2).collision)
                    return true;

                break;
        }
        return false;
    }

    public static Entity checkEntities(Entity entity, List<Entity> targets) {
        Entity collidedEntity = null;

        Rectangle hitBox1 = new Rectangle(entity.hitbox);
        hitBox1.setLocation(entity.location.getX(), entity.location.getY());
        for (Entity target : targets) {
            if (target != entity) {
                Rectangle hitBox2 = new Rectangle(target.hitbox);
                hitBox2.setLocation(target.location.getX(), target.location.getY());

                if (hitBox1.intersects(hitBox2)) collidedEntity = target;
            }
        }

        return collidedEntity;
    }

}
