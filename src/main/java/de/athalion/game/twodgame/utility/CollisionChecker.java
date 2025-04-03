package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.main.GamePanel;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        int maxWorldCol = gamePanel.worldManager.getCurrentWorld().getMaxWorldCol();
        int maxWorldRow = gamePanel.worldManager.getCurrentWorld().getMaxWorldRow();

        switch (entity.direction) {
            case UP:

                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.getTileSet().getTile(tileNum1).collision || gamePanel.tileManager.getTileSet().getTile(tileNum1).collision)
                    entity.collisionOn = true;

                break;
            case DOWN:

                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.getTileSet().getTile(tileNum1).collision || gamePanel.tileManager.getTileSet().getTile(tileNum1).collision)
                    entity.collisionOn = true;

                break;
            case LEFT:

                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.getTileSet().getTile(tileNum1).collision || gamePanel.tileManager.getTileSet().getTile(tileNum1).collision)
                    entity.collisionOn = true;

                break;
            case RIGHT:

                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.worldManager.getCurrentWorld().mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.getTileSet().getTile(tileNum1).collision || gamePanel.tileManager.getTileSet().getTile(tileNum1).collision)
                    entity.collisionOn = true;

                break;
        }

    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new).length; i++) {

            if (gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i] != null) {

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea.x = gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].worldX + gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea.x;
                gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea.y = gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].worldY + gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea.y;

                switch (entity.direction) {

                    case UP:
                        entity.solidArea.y -= entity.speed;
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.speed;
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.speed;
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.speed;
                        break;

                }

                if (entity.solidArea.intersects(gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea)) {
                    if (gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea.x = gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidAreaDefaultX;
                gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidArea.y = gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[i].solidAreaDefaultY;

            }

        }

        return index;

    }

    public Entity checkEntity(Entity entity, Entity[] target) {

        Entity collidedEntity = null;

        for (Entity value : target) {

            if (value != null) {

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                value.solidArea.x = value.worldX + value.solidArea.x;
                value.solidArea.y = value.worldY + value.solidArea.y;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= entity.speed;
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.speed;
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.speed;
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(value.solidArea)) {
                    if (value != entity) {
                        entity.collisionOn = true;
                        collidedEntity = value;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                value.solidArea.x = value.solidAreaDefaultX;
                value.solidArea.y = value.solidAreaDefaultY;

            }

        }

        return collidedEntity;

    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch (entity.direction) {

            case UP:
                entity.solidArea.y -= entity.speed;
                break;
            case DOWN:
                entity.solidArea.y += entity.speed;
                break;
            case LEFT:
                entity.solidArea.x -= entity.speed;
                break;
            case RIGHT:
                entity.solidArea.x += entity.speed;
                break;

        }

        if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;

    }

}
