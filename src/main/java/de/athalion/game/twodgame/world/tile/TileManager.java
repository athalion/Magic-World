package de.athalion.game.twodgame.world.tile;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.resources.Identifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TileManager {

    TileSet tileSet;
    int worldWidth, worldHeight;
    int[][] mapTileNumber;

    public TileManager() {
        tileSet = new TileSet();
    }

    public void loadTileSet(Identifier identifier) {
        tileSet.loadTileSet(identifier.getPath());
    }

    public void loadMap(Identifier identifier) {
        Logger.log("Loading map " + identifier.getPath() + "...");
        try {
            List<String> lines = Files.readAllLines(Paths.get(identifier.getResource().toURI()));

            worldHeight = lines.size();
            worldWidth = lines.getFirst().split(" ").length;
            mapTileNumber = new int[worldWidth][worldHeight];

            for (int row = 0; row < worldHeight; row++) {
                String[] numbers = lines.get(row).split(" ");
                for (int col = 0; col < worldWidth; col++) {
                    mapTileNumber[col][row] = Integer.parseInt(numbers[col]);
                }
            }

            Logger.log("Done!");
        } catch (IOException | URISyntaxException e) {
            Logger.error("Error loading map " + identifier.getPath() + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int[][] getMap() {
        return mapTileNumber;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

}
