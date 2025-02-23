package de.athalion.game.twodgame.object.projectile;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.entity.Projectile;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_ArrowProjectile extends Projectile {

    GamePanel gamePanel;

    public OBJ_ArrowProjectile(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.PROJECTILE;
        name = "Arrow";
        id = "PRO_arrow";

        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        alive = false;
        getImage();

    }

    public void getImage() {

        up1 = setup("/projectile/arrow/arrow_up", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/projectile/arrow/arrow_up", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/projectile/arrow/arrow_down", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/projectile/arrow/arrow_down", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/projectile/arrow/arrow_left", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/projectile/arrow/arrow_left", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/projectile/arrow/arrow_right", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/projectile/arrow/arrow_right", gamePanel.tileSize, gamePanel.tileSize);

    }

}
