package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);

        type = EntityType.PROJECTILE;
    }

    public void set(int worldX, int worldY, Direction direction, boolean alive, Entity user) {

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        life = maxLife;

    }

    public void update() {

        if (user == gamePanel.player) {
            Entity monster = gamePanel.collisionChecker.checkEntity(this, gamePanel.worldManager.getCurrentWorld().getMonsters().toArray(Entity[]::new));
            if (monster != null) {
                gamePanel.player.damageMonster(monster, true);
                alive = false;
            }
        } else {

        }

        switch (direction) {
            case Direction.UP:
                worldY -= speed;
                break;
            case Direction.DOWN:
                worldY += speed;
                break;
            case Direction.LEFT:
                worldX -= speed;
                break;
            case Direction.RIGHT:
                worldX += speed;
                break;
        }

        life--;
        if (life <= 0) {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }

    }

}
