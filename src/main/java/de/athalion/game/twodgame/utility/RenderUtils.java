package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class RenderUtils {

    public static void fillScreenBlack(float alpha, Graphics2D g2, GamePanel gamePanel) {

        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }

    public static BufferedImage loadImage(String path, Class<?> aClass) {

        BufferedImage image = null;
        InputStream inputStream = aClass.getResourceAsStream(path + ".png");

        try {
            image = ImageIO.read(Requirements.notNull(inputStream, "Cannot load image because " + path + " is null!"));
        } catch (IOException e) {
            Logger.error("Error reading texture " + path + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        return image;

    }

    public static void drawSubWindow(int x, int y, int width, int height, int arc, int alpha, Color color, Graphics2D g2) {
        Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, arc, arc);

        g2.setColor(color.darker());
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, arc - 10, arc - 10);
    }

    public static int getXForCenteredText(String text, Graphics2D g2, GamePanel gamePanel) {
        int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - width / 2;
    }

}
