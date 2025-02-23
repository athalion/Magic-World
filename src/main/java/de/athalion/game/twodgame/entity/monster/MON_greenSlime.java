package de.athalion.game.twodgame.entity.monster;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;

import java.util.Random;


public class MON_greenSlime extends Entity {

    GamePanel gamePanel;

    public MON_greenSlime(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.MONSTER;
        name = "greenSlime";
        id = "MON_greenSlime";

        speed = 1;
        maxLife = 4;
        life = maxLife;
        level = 1;

        attack = 2;
        defence = 0;

        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();

        characterImage = setup("/monster/green_slime/slime_c", gamePanel.tileSize * 2 - 30, gamePanel.tileSize * 2 - 30);

    }

    public void getImage() {

        up1 = setup("/monster/green_slime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/green_slime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/green_slime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/green_slime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/green_slime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/green_slime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/green_slime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/green_slime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);

    }

    public void setAction() {

        actionLockTimer++;

        if (actionLockTimer >= 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = Direction.UP;
            }
            if (i > 25 && i <= 50) {
                direction = Direction.DOWN;
            }
            if (i > 50 && i <= 75) {
                direction = Direction.LEFT;
            }
            if (i > 75) {
                direction = Direction.RIGHT;
            }

            actionLockTimer = 0;

        }

    }

    public void damageReaction() {

        actionLockTimer = 0;
        direction = gamePanel.player.direction;

    }

}
