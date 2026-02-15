package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_ShieldWood extends Item {
    public OBJ_ShieldWood(GamePanel gamePanel) {
        super(gamePanel);

        equipType = EquipType.OFFHAND;

        type = EntityType.OBJECT;
        name = "Wooden Shield";
        id = "OBJ_woodenShield";

        down1 = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);

        defenceValue = 1;

    }
}
