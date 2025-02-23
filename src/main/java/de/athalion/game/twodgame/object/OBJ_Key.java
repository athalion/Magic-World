package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Key extends Item {

    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);

        type = EntityType.OBJECT;
        name = "key";
        id = "OBJ_key";

        down1 = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);

        description = "Ein alter, schwerer Schlüssel aus Eisen.";

    }

}
