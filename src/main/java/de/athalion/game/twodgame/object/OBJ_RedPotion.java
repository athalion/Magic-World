package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.sound.Sound;

public class OBJ_RedPotion extends Item {

    GamePanel gamePanel;

    public OBJ_RedPotion(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        equipType = EquipType.CONSUMABLE;

        type = EntityType.OBJECT;
        name = "Seltsamer Roter Trank";
        id = "OBJ_redPotion";

        down1 = setup("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);

        description = "Heilt dich etwas.";

    }

    public void use(Entity entity) {

        gamePanel.simpleDialog("Als du den Trank trinkts, kannst du spühren wie neue Energie durch deinen Körper ströhmt.");
        gamePanel.playSoundEffect(Sound.EFFECT_POWERUP);

        entity.life += 5;
        if (entity.life > entity.maxLife) entity.life = entity.maxLife;

    }

}
