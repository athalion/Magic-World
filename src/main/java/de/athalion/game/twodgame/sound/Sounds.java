package de.athalion.game.twodgame.sound;

public enum Sounds {

    // MUSIC
    MUSIC_MENU("/sound/music/menu.wav", Type.MUSIC),
    MUSIC_DEMON_BATTLE_INTRO("/sound/music/demon_battle/demon_battle_intro.wav", Type.MUSIC),
    MUSIC_DEMON_BATTLE_LOOP("/sound/music/demon_battle/demon_battle_loop.wav", Type.MUSIC),
    MUSIC_DEMON_BATTLE_OUTRO( "/sound/music/demon_battle/demon_battle_outro.wav", Type.MUSIC),
    MUSIC_DEMON_BATTLE_SMALL( "/sound/music/demon_battle/demon_battle_small.wav", Type.MUSIC),

    // EFFECTS
    EFFECT_COIN("/sound/coin.wav", Type.EFFECT),
    EFFECT_POWERUP("/sound/powerup.wav", Type.EFFECT),
    EFFECT_UNLOCK("/sound/unlock.wav", Type.EFFECT),
    EFFECT_FANFARE("/sound/fanfare.wav", Type.EFFECT),
    EFFECT_HIT_MONSTER("/sound/hitmonster.wav", Type.EFFECT),
    EFFECT_RECEIVE_DAMAGE("/sound/receivedamage.wav", Type.EFFECT),
    EFFECT_SWING_WEAPON("/sound/swingweapon.wav", Type.EFFECT),
    EFFECT_LEVEL_UP("/sound/levelup.wav", Type.EFFECT),
    EFFECT_CURSOR("/sound/effects/cursor.wav", Type.EFFECT),
    EFFECT_TEXT("/sound/text.wav", Type.EFFECT),
    EFFECT_LOGO("/sound/logo.wav", Type.EFFECT),

    // ENVIRONMENT
    ENVIRONMENT_RAIN("/sound/environment/rain.wav", Type.ENVIRONMENT),
    ENVIRONMENT_RAIN_DULL("/sound/environment/rain_dull.wav", Type.ENVIRONMENT);

    private final String path;
    private final Type type;

    Sounds(String path, Type type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        EFFECT, ENVIRONMENT, MUSIC
    }

}
