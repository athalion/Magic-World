package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.item.WeaponType;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.object.projectile.OBJ_ArrowProjectile;

public class OBJ_Bow extends Item {

    public OBJ_Bow(GamePanel gamePanel) {

        super(gamePanel);

        equipType = EquipType.WEAPON;
        weaponType = WeaponType.BOW;

        type = EntityType.OBJECT;
        name = "Bogen";
        id = "OBJ_bow";

        down1 = setup("/objects/bow", gamePanel.tileSize, gamePanel.tileSize);

        attackValue = 1;
        attackSpeedMultiplier = 1F;
        attackArea.width = 0;
        attackArea.height = 0;

        projectile = new OBJ_ArrowProjectile(gamePanel);
        itemToConsume = new OBJ_Arrow(gamePanel);

        description = "Ein einfacher Bogen. \nEr besteht aus einem Ast und etwas Kordel.";

    }

}
