package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.item.WeaponType;
import de.athalion.game.twodgame.main.GamePanel;

public class OBJ_SwordNormal extends Item {
    public OBJ_SwordNormal(GamePanel gamePanel) {
        super(gamePanel);

        equipType = EquipType.WEAPON;
        weaponType = WeaponType.SWORD;

        type = EntityType.OBJECT;
        name = "Normal Sword";
        id = "OBJ_normalSword";

        down1 = setup("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);

        attackValue = 1;
        attackSpeedMultiplier = 1F;
        attackArea.width = 36;
        attackArea.height = 36;

    }

}
