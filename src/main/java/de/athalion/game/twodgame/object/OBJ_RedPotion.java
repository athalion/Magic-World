package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.sound.SoundSystem;
import de.athalion.game.twodgame.sound.Sounds;

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

        gamePanel.simpleDialog("Als du den Trank trinkst, kannst du spüren wie neue Energie durch deinen Körper strömt.");
        SoundSystem.playSound(Sounds.EFFECT_POWERUP);

        entity.life += 5;
        if (entity.life > entity.maxLife) entity.life = entity.maxLife;

    }

}
