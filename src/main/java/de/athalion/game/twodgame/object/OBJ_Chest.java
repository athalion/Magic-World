package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gamePanel;

    public OBJ_Chest(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.OBJECT;
        name = "chest";
        id = "OBJ_chest";

        down1 = setup("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);

    }

}
