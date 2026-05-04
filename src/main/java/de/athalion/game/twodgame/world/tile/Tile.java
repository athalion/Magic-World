package de.athalion.game.twodgame.world.tile;

import de.athalion.game.twodgame.resources.texture.Texture;

public class Tile {

    Texture texture;
    public boolean collision = false;

    public Tile(Texture texture, boolean collision) {
        this.texture = texture;
        this.collision = collision;
    }

    public Texture getTexture() {
        return texture;
    }

    public void nextFrame() {
        texture.nextFrame();
    }

}
