package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.sound.Sounds;
import de.athalion.game.twodgame.sound.SoundSystem;

import java.util.HashMap;

public class WorldManager {

    private final GamePanel gamePanel;

    final HashMap<String, Class<? extends World>> WORLDS = new HashMap<>();
    World currentWorld;

    public WorldManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void load(String savePath) {

    }

    public Class<? extends World> getWorld(String name) {
        return WORLDS.get(name);
    }

    public void changeWorld(World world) {
        SoundSystem.stopSound(Sounds.Type.ENVIRONMENT);
        currentWorld = world;
        gamePanel.tileManager.setTargetZoom(world.zoom);
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

}
