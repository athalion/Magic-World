package de.athalion.game.twodgame.world;

import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage[] images;
    public boolean collision = false;
    int frames;
    int frame = 0;

    public BufferedImage getCurrentFrame() {
        return images[frame];
    }

    public void nextFrame() {
        frame++;
        if (frame >= frames) frame = 0;
    }

}
