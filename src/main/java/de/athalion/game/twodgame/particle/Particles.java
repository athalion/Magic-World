package de.athalion.game.twodgame.particle;

import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.resources.texture.Textures;

public class Particles {
    public final static Texture ENTITY_DEATH = Textures.setup(Identifier.forPath("/particle/entity_death.png"), true);
    public final static Texture SPLASH = Textures.setup(Identifier.forPath("/particle/splash.png"), true);
}
