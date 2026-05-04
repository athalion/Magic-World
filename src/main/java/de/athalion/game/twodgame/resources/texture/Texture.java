package de.athalion.game.twodgame.resources.texture;

import java.awt.image.BufferedImage;

public class Texture {

    BufferedImage[] images;
    int frame = 0;

    public Texture(BufferedImage[] images) {
        this.images = images;
    }

    public void nextFrame() {
        frame++;
        if (frame >= images.length) frame = 0;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public BufferedImage getFrame() {
        return images[frame];
    }

    public int getFrameNumber() {
        return frame;
    }

    public int getFrames() {
        return images.length;
    }

    public int getWidth() {
        return images[0].getWidth();
    }

    public int getHeight() {
        return images[0].getHeight();
    }

    public boolean isAnimated() {
        return images.length > 1;
    }

}
