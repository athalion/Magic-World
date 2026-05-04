package de.athalion.game.twodgame.entity.monster;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.location.Direction;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Textures;

import java.util.Random;

public class MON_greenSlime extends Entity {

    public MON_greenSlime() {

        type = EntityType.MONSTER;
        name = "greenSlime";
        id = "MON_greenSlime";

        stats.setBase(Stats.SPEED, 1);
        stats.setBase(Stats.MAX_HEALTH, 4);
        health = stats.get(Stats.MAX_HEALTH);
        level = 1;

        stats.setBase(Stats.STRENGTH, 2);
        stats.setBase(Stats.DEFENCE, 0);

        exp = 2;

        hitbox.x = 3;
        hitbox.y = 18;
        hitbox.width = 42;
        hitbox.height = 30;

        characterImage = Textures.setup(Identifier.forPath("/monster/green_slime/slime_c"), false);

    }

    public void setAction() {

        actionLockTimer++;

        if (actionLockTimer >= 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = Direction.UP;
            }
            if (i > 25 && i <= 50) {
                direction = Direction.DOWN;
            }
            if (i > 50 && i <= 75) {
                direction = Direction.LEFT;
            }
            if (i > 75) {
                direction = Direction.RIGHT;
            }

            actionLockTimer = 0;

        }

    }

    public void damageReaction() {

        actionLockTimer = 0;
        direction = GameInstance.getInstance().getPlayer().direction;

    }

}
