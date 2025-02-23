package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Arrow extends Item {

    public OBJ_Arrow(GamePanel gamePanel) {

        super(gamePanel);

        type = EntityType.OBJECT;
        name = "Pfeil";
        id = "OBJ_arrow";

        description = "Er hat eine sehr scharfe Spitze!";

        down1 = setup("/item/arrow", gamePanel.tileSize, gamePanel.tileSize);

    }

}
