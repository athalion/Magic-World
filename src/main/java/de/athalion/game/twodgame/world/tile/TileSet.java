package de.athalion.game.twodgame.world.tile;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Textures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class TileSet {

    HashMap<Integer, Tile> tiles = new HashMap<>();

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
        tiles.put(index, new Tile(Textures.setup(Identifier.forPath(pathName), animated), collision));
    }

    public void updateAnimations() {
        tiles.values().forEach(Tile::nextFrame);
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

}
