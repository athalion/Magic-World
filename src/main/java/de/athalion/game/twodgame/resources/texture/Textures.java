package de.athalion.game.twodgame.resources.texture;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Textures {

    public static Texture setup(Identifier identifier, boolean animated) {
        try {
            Texture texture;
            BufferedImage image = ImageIO.read(identifier.stream());
            if (animated) {
                int frames = image.getHeight() / image.getWidth();
                BufferedImage[] images = new BufferedImage[frames];
                for (int i = 0; i < frames; i++) {
                    images[i] = UtilityTool.scaleImage(
                            image.getSubimage(0, image.getWidth() * i, image.getWidth(), image.getWidth()),
                            GameInstance.getInstance().getGameFrame().TILE_SIZE,
                            GameInstance.getInstance().getGameFrame().TILE_SIZE
                    );
                }
                texture = new Texture(images);
            } else texture = new Texture(new BufferedImage[] {image});
            return texture;
        } catch (IOException e) {
            Logger.error("Error reading texture: " + identifier.getPath() + "!");
            Logger.stackTrace(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

}
