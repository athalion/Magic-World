package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Boots extends Entity {

    GamePanel gamePanel;

    public OBJ_Boots(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.OBJECT;
        name = "boots";
        id = "OBJ_boots";

        down1 = setup("/objects/boots", gamePanel.tileSize, gamePanel.tileSize);

    }

}
