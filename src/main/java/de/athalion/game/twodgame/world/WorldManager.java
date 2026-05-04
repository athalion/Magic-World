package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.utility.Requirements;
import de.athalion.game.twodgame.worlds.Home;

import java.util.HashMap;

public class WorldManager {

    private final HashMap<String, World> WORLDS = new HashMap<>();
    private final HashMap<String, WorldInstance> loadedWorlds = new HashMap<>();

    public WorldManager() {
        registerWorlds();
    }

    private void registerWorlds() {
        WORLDS.put(Home.NAME_KEY, new Home());
    }

    public WorldInstance loadWorld(String nameKey) {
        WorldInstance worldInstance = new WorldInstance(WORLDS.get(nameKey));
        loadedWorlds.put(nameKey, worldInstance);
        return worldInstance;
    }

    public WorldInstance getWorld(String nameKey) {
        Requirements.requires(loadedWorlds.containsKey(nameKey), "Failed to get world: " + nameKey + "! The world is not loaded!");
        return loadedWorlds.get(nameKey);
    }

    public void unloadWorld(String nameKey) {
        loadedWorlds.remove(nameKey);
    }

}
