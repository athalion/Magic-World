package de.athalion.game.twodgame.sound;

import de.athalion.game.twodgame.resources.Identifier;

public enum Sounds {

    // MUSIC

    // EFFECTS
    EFFECT_SWING_SWORD("/sound/effects/swing_sword.wav", Type.EFFECT, true),

    // ENVIRONMENT
    ENVIRONMENT_RAIN("/sound/environment/rain.wav", Type.ENVIRONMENT, false);

    private final Identifier identifier;
    private final Type type;
    private final boolean hapticsEnabled;

    Sounds(String path, Type type, boolean hapticsEnabled) {
        identifier = Identifier.forPath(path);
        this.type = type;
        this.hapticsEnabled = hapticsEnabled;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    public boolean isHapticsEnabled() {
        return hapticsEnabled;
    }

    public enum Type {
        EFFECT, ENVIRONMENT, MUSIC
    }

}
