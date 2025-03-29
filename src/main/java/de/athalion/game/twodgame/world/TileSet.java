package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class TileSet {

    GamePanel gamePanel;

    HashMap<Integer, Tile> tiles = new HashMap<>();

    public TileSet(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void loadTileSet(String pathName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Objects.requireNonNull(getClass().getResource(pathName)).getPath()))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                loadTile(Integer.parseInt(line.split("#")[0]), "tiles" + File.separator + line.split("#")[1], Boolean.parseBoolean(line.split("#")[3]), Boolean.parseBoolean(line.split("#")[2]));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            Logger.error("Could not load Tile Set: " + pathName + "! " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
    }

    private void loadTile(int index, String pathName, boolean collision, boolean animated) {
        if (animated) {
            try {
                Tile tile = new Tile();
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + pathName + ".png")));
                int frames = image.getHeight() / image.getWidth();
                tile.frames = frames;
                tile.images = new BufferedImage[frames];
                for (int i = 0; i < frames; i++) {
                    tile.images[i] = UtilityTool.scaleImage(image.getSubimage(0, image.getWidth() * i, image.getWidth(), image.getWidth()), gamePanel.tileSize, gamePanel.tileSize);
                }
                tile.collision = collision;
                tiles.put(index, tile);
            } catch (IOException e) {
                Logger.error("Error reading texture " + pathName + ": " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
        } else {
            try {
                Tile tile = new Tile();
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + pathName + ".png")));
                tile.frames = 1;
                tile.images = new BufferedImage[1];
                tile.images[0] = UtilityTool.scaleImage(image, image.getWidth() * 3, image.getHeight() * 3);
                tile.collision = collision;
                tiles.put(index, tile);
            } catch (IOException e) {
                Logger.error("Error reading texture " + pathName + ": " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
        }
    }

    public void updateAnimations() {
        tiles.values().forEach(Tile::nextFrame);
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

}
