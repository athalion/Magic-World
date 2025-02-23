package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.item.WeaponType;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_Axe extends Item {

    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);

        equipType = EquipType.WEAPON;
        weaponType = WeaponType.AXE;

        type = EntityType.OBJECT;
        name = "Holzfäller Axt";
        id = "OBJ_axe";

        down1 = setup("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);

        attackValue = 2;
        attackSpeedMultiplier = 2F;
        attackArea.height = 30;
        attackArea.width = 30;

        description = "Eine schwere eiserne Axt.";

    }

}
