package de.athalion.game.twodgame.resources.texture;

import de.athalion.game.twodgame.location.Direction;

import java.util.HashMap;

public class TextureStack {

    HashMap<String, TextureSet> textureSets = new HashMap<>();
    Texture characterTexture;

    public TextureStack(Texture characterTexture, TextureSet idle) {
        this.characterTexture = characterTexture;
        textureSets.put("idle", idle);
    }

    public TextureStack addState(String stateName, TextureSet textureSet) {
        textureSets.put(stateName, textureSet);
        return this;
    }

    public Texture getTexture(String state, Direction lookingDirection) {
        return textureSets.get(state).get(lookingDirection);
    }

    public static class TextureSet {
        HashMap<Direction, Texture> textures;

        public TextureSet(Texture up, Texture down, Texture left, Texture right) {
            this.textures = new HashMap<>(4);
            this.textures.put(Direction.UP, up);
            this.textures.put(Direction.DOWN, down);
            this.textures.put(Direction.LEFT, left);
            this.textures.put(Direction.RIGHT, right);
        }

        public Texture get(Direction direction) {
            return textures.get(direction);
        }
    }

}
