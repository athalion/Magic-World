package de.athalion.game.twodgame.graphics;

import de.athalion.game.twodgame.graphics.font.Fonts;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.resources.texture.Texture;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DrawContext {

    private final Graphics2D g2;

    private final int screenWidth;
    private final int screenHeight;

    public DrawContext(Graphics2D g2) {
        this.g2 = g2;
        screenWidth = GameInstance.getInstance().getGameFrame().SCREEN_WIDTH;
        screenHeight = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT;
        this.g2.setFont(Fonts.DEFAULT);
    }

    public void drawTexture(@NotNull Texture texture, int x, int y) {
        g2.drawImage(texture.getFrame(), x, y, null);
    }

    public void drawTexture(@NotNull Texture texture, int x, int y, int width, int height) {
        g2.drawImage(texture.getFrame(), x, y, width, height, null);
    }

    public void fillRect(int x, int y, int width, int height) {
        g2.fillRect(x, y, width, height);
    }

    public void fillScreen(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        fillRect(0, 0, screenWidth, screenHeight);
    }

    public void fillScreen(float alpha, Color color) {
        setColor(color);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        fillRect(0, 0, screenWidth, screenHeight);
    }

    public void setColor(Color color) {
        g2.setColor(color);
    }

    public void setFont(Font font) {
        g2.setFont(font);
    }

    public void setFontSize(float size) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, size));
    }

    public void drawString(String s, int x, int y) {
        g2.drawString(s, x, y);
    }

    public void drawString(String s, int x, int y, Font font) {
        setFont(font);
        g2.drawString(s, x, y);
    }

    public void drawString(String s, int x, int y, Color color) {
        setColor(color);
        g2.drawString(s, x, y);
    }

    public int calculateXForCenteredText(String text) {
        int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return screenWidth / 2 - width / 2;
    }

    public Color getColor() {
        return g2.getColor();
    }

}
