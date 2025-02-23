package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public double cameraX = 0;
    public double cameraY = 0;
    int targetX = 1000;
    int targetY = 1000;
    public double zoom = 1;
    double targetZoom = 2;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[50];
        loadImages();
    }

    public void loadImages() {

        setup(0, "000", true, false);
        setup(1, "timber_framed_wall/horizontal", true, false);
        setup(2, "timber_framed_wall/vertical", true, false);
        setup(3, "timber_framed_wall/left_up", true, false);
        setup(4, "timber_framed_wall/right_up", true, false);
        setup(5, "timber_framed_wall/left_down", true, false);
        setup(6, "timber_framed_wall/right_down", true, false);
        setup(7, "floor_planks", false, false);
        setup(8, "002", false, false);
        setup(9, "016", true, false);
        setup(10, "033", false, false);
        setup(11, "test", true, false);

    }

    public void setup(int index, String pathName, boolean collision, boolean animated) {

        if (animated) {
            try {
                tiles[index] = new Tile();
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + pathName + ".png")));
                int frames = image.getHeight() / image.getWidth();
                tiles[index].frames = frames;
                tiles[index].image = new BufferedImage[frames];
                for (int i = 0; i < frames; i++) {
                    tiles[index].image[i] = UtilityTool.scaleImage(image.getSubimage(0, image.getWidth() * i, image.getWidth(), image.getWidth()), gamePanel.tileSize, gamePanel.tileSize);
                }
                tiles[index].collision = collision;
            } catch (IOException e) {
                UtilityTool.openErrorWindow(e);
            }
        } else {
            try {
                tiles[index] = new Tile();
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + pathName + ".png")));
                tiles[index].frames = 1;
                tiles[index].image = new BufferedImage[1];
                tiles[index].image[0] = UtilityTool.scaleImage(image, image.getWidth() * 3, image.getHeight() * 3);
                tiles[index].collision = collision;
            } catch (IOException e) {
                UtilityTool.openErrorWindow(e);
            }
        }

    }

    public void updateAnimations() {
        for (Tile tile : tiles) {
            if (tile == null) continue;
            tile.frame++;
            if (tile.frame >= tile.frames) tile.frame = 0;
        }
    }

    public void update() {
        if (gamePanel.worldManager.getCurrentWorld().cameraFixed) {
            targetX = gamePanel.worldManager.getCurrentWorld().maxWorldCol * gamePanel.tileSize / 2;
            targetY = gamePanel.worldManager.getCurrentWorld().maxWorldRow * gamePanel.tileSize / 2;
        }

        cameraX += (targetX - cameraX) / 16;
        cameraY += (targetY - cameraY) / 16;
        zoom += (targetZoom - zoom) / 16;
    }

    public void draw(Graphics2D g2, World world) {

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < world.maxWorldCol && worldRow < world.maxWorldRow) {

            int tileNum = world.mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = (int) (worldX - cameraX + gamePanel.halfWidth);
            int screenY = (int) (worldY - cameraY + gamePanel.halfHeight);

            if (worldX + gamePanel.tileSize > cameraX - gamePanel.halfWidth
                    && worldX - gamePanel.tileSize < cameraX + gamePanel.halfWidth
                    && worldY + gamePanel.tileSize > cameraY - gamePanel.halfHeight
                    && worldY - gamePanel.tileSize < cameraY + gamePanel.halfHeight) {

                int x = screenX - tiles[tileNum].image[tiles[tileNum].frame].getWidth() + gamePanel.tileSize;
                int y = screenY - tiles[tileNum].image[tiles[tileNum].frame].getHeight() + gamePanel.tileSize;
                g2.drawImage(tiles[tileNum].image[tiles[tileNum].frame], x, y, null);
            }
            worldCol++;

            if (worldCol == world.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

    }

    public void setTarget(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void setTargetZoom(double targetZoom) {
        this.targetZoom = targetZoom;
    }

}
