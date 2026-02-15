package de.athalion.game.twodgame.particle;

import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.Requirements;
import de.athalion.game.twodgame.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Particle {

    private final GamePanel gamePanel;

    BufferedImage[] images;
    int frame = 0;
    int frames;
    int x, y;

    public Particle(Texture texture, int x, int y, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.images = texture.images;
        this.frames = texture.frames;
        this.x = x;
        this.y = y;
    }

    public boolean update() {
        frame++;
        return frame >= frames;
    }

    public void draw(Graphics2D g2) {
        int screenX = (int) (x - gamePanel.tileManager.cameraX + gamePanel.halfWidth);
        int screenY = (int) (y - gamePanel.tileManager.cameraY + gamePanel.halfHeight);

        if (screenX + images[0].getWidth() >= 0
                && screenX - images[0].getWidth() <= gamePanel.screenWidth
                && screenY + images[0].getWidth() >= 0
                && screenY - images[0].getWidth() <= gamePanel.screenHeight)
            g2.drawImage(images[frame], screenX, screenY, null);
    }

    public static class Texture {
        BufferedImage[] images;
        int frames;

        public Texture(String texture) {
            try {
                BufferedImage image = ImageIO.read(Requirements.notNull(getClass().getResourceAsStream(texture), "Could not load Texture " + texture + "!"));
                int frames = image.getHeight() / image.getWidth();
                this.frames = frames;
                images = new BufferedImage[frames];
                for (int i = 0; i < frames; i++) {
                    images[i] = UtilityTool.scaleImage(image.getSubimage(0, image.getWidth() * i, image.getWidth(), image.getWidth()), image.getWidth() * 3, image.getWidth() * 3);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Textures {
        public final static Texture ENTITY_DEATH = new Texture("/particle/entity_death.png");
        public final static Texture SPLASH = new Texture("/particle/splash.png");
    }

}
