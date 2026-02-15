package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Heart extends Entity {

    GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.OBJECT;
        name = "heart";
        id = "OBJ_heart";

        image = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);

    }

}
