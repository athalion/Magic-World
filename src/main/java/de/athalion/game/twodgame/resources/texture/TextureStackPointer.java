package de.athalion.game.twodgame.resources.texture;

import de.athalion.game.twodgame.location.Direction;

import java.awt.image.BufferedImage;

public class TextureStackPointer {

    TextureStack textureStack;
    String state = "idle";
    String animation;
    int frame = 0;

    public TextureStackPointer(TextureStack textureStack) {
        this.textureStack = textureStack;
    }

    public void setState(String state) {
        this.state = state;
        frame = 0;
    }

    public void playAnimation(String state) {
        animation = state;
        frame = 0;
    }

    public BufferedImage getImage(Direction lookingDirection) {
        BufferedImage image = textureStack.getTexture((animation != null) ? animation : state, lookingDirection).getFrame();
        nextFrame();
        return image;
    }

    private void nextFrame() {
        frame++;
        if (animation != null && frame >= textureStack.getTexture(animation, Direction.UP).getFrames()) {
            animation = null;
            frame = 0;
        } else if (frame >= textureStack.getTexture(state, Direction.UP).getFrames()) frame = 0;
    }

}
