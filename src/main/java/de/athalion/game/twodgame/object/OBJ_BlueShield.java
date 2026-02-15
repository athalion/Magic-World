package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_BlueShield extends Item {
    public OBJ_BlueShield(GamePanel gamePanel) {
        super(gamePanel);

        equipType = EquipType.WEAPON;

        type = EntityType.OBJECT;
        name = "Blaues Schild";
        id = "OBJ_blueShield";

        down1 = setup("/objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);

        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;

        description = "Es glänzt etwas in der Sonne";

    }

}
