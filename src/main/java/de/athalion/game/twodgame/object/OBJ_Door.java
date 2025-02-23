package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Door extends Entity {

    GamePanel gamePanel;

    public OBJ_Door(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.OBJECT;
        name = "door";
        id = "OBJ_door";

        down1 = setup("/objects/door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

}
