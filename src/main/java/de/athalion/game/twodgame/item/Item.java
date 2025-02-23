package de.athalion.game.twodgame.item;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.Projectile;
import de.athalion.game.twodgame.main.GamePanel;

public class Item extends Entity {

    public int attackValue;
    public float attackSpeedMultiplier;
    public int defenceValue;
    public String description = "";
    public EquipType equipType;
    public WeaponType weaponType;
    public Projectile projectile;
    public Item itemToConsume;

    public void use() {
    }

    public Item(GamePanel gamePanel) {

        super(gamePanel);

    }

}
