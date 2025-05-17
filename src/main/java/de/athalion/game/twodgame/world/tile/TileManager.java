package de.athalion.game.twodgame.world.tile;

import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileManager {

    GamePanel gamePanel;
    TileSet tileSet;
    public double cameraX = 0;
    public double cameraY = 0;
    int targetX = 1000;
    int targetY = 1000;
    public double zoom = 1;
    double targetZoom = 2;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tileSet = new TileSet(gamePanel);
        tileSet.loadTileSet("");
    }

    public void update() {
        if (gamePanel.worldManager.getCurrentWorld().isCameraFixed()) {
            targetX = gamePanel.worldManager.getCurrentWorld().getMaxWorldCol() * gamePanel.tileSize / 2;
            targetY = gamePanel.worldManager.getCurrentWorld().getMaxWorldRow() * gamePanel.tileSize / 2;
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

        while (worldCol < world.getMaxWorldCol() && worldRow < world.getMaxWorldRow()) {

            int tileNum = world.mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = (int) (worldX - cameraX + gamePanel.halfWidth);
            int screenY = (int) (worldY - cameraY + gamePanel.halfHeight);

            if (worldX + gamePanel.tileSize > cameraX - gamePanel.halfWidth
                    && worldX - gamePanel.tileSize < cameraX + gamePanel.halfWidth
                    && worldY + gamePanel.tileSize > cameraY - gamePanel.halfHeight
                    && worldY - gamePanel.tileSize < cameraY + gamePanel.halfHeight) {

                BufferedImage currentImage = tileSet.getTile(tileNum).getCurrentFrame();
                int x = screenX - currentImage.getWidth() + gamePanel.tileSize;
                int y = screenY - currentImage.getHeight() + gamePanel.tileSize;
                g2.drawImage(currentImage, x, y, null);
            }
            worldCol++;

            if (worldCol == world.getMaxWorldCol()) {
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

    public TileSet getTileSet() {
        return tileSet;
    }

}
