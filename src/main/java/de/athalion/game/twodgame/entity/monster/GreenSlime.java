package de.athalion.game.twodgame.entity.monster;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.TextureStack;
import de.athalion.game.twodgame.resources.texture.TextureStackPointer;
import de.athalion.game.twodgame.resources.texture.Textures;

public class GreenSlime extends Entity {

    public static final TextureStack GREEN_SLIME_TEXTURE_STACK = new TextureStack(
            Textures.setup(Identifier.forPath("textures/entity/green_slime/character.png"), false),
            new TextureStack.TextureSet(
                    Textures.setup(Identifier.forPath("/textures/entity/green_slime/idle/up.png"), true),
                    Textures.setup(Identifier.forPath("/textures/entity/green_slime/idle/down.png"), true),
                    Textures.setup(Identifier.forPath("/textures/entity/green_slime/idle/left.png"), true),
                    Textures.setup(Identifier.forPath("/textures/entity/green_slime/idle/right.png"), true)
            )
    ).addState("walking", new TextureStack.TextureSet(
            Textures.setup(Identifier.forPath("/textures/entity/green_slime/walking/up.png"), true),
            Textures.setup(Identifier.forPath("/textures/entity/green_slime/walking/down.png"), true),
            Textures.setup(Identifier.forPath("/textures/entity/green_slime/walking/left.png"), true),
            Textures.setup(Identifier.forPath("/textures/entity/green_slime/walking/right.png"), true)
    ));

    public GreenSlime() {
        type = EntityType.MONSTER;

        textureStackPointer = new TextureStackPointer(GREEN_SLIME_TEXTURE_STACK);

        stats.setBase(Stats.SPEED, 1);
        stats.setBase(Stats.MAX_HEALTH, 4);
        health = stats.get(Stats.MAX_HEALTH);
        level = 1;

        stats.setBase(Stats.STRENGTH, 2);
        stats.setBase(Stats.DEFENCE, 0);

        exp = 2;

        hitboxSize = new Vector(42, 30);
    }

}
