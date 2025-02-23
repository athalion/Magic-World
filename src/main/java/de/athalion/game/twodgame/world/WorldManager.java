package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.event.Event;
import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.object.OBJ_Cup;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final GamePanel gamePanel;
    List<World> worlds = new ArrayList<>();
    World currentWorld;

    public WorldManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void load(String savePath) {
        worlds.add(
                new World(gamePanel, savePath + "/maps/home")
                        .addEvent(new Event(new Rectangle(432, 98, 48, 48), Direction.LEFT).callback(() -> gamePanel.player.teleport(
                                getWorld("Welt"),
                                6 * gamePanel.tileSize,
                                3 * gamePanel.tileSize - 1,
                                true
                        ))).addEvent(new Event(new Rectangle(192, 192, 48, 48), Direction.ANY).runIf(event -> event.getCalls() == 0).callback(() ->
                                gamePanel.simpleDialog("*gähn* Ich bin müde. Ein Kaffee währe jetzt perfekt.")
                        )).addEvent(new Event(new Rectangle(384, 240, 48, 48), Direction.UP).callback(() -> {
                            if (gamePanel.keyHandler.enterPressed) {
                                gamePanel.player.inventory.add(new OBJ_Cup(gamePanel));
                            }
                        })).load()
        );
        worlds.add(
                new World(gamePanel, savePath + "/maps/world")
                        .addEvent(new Event(new Rectangle(288, 144, 48, 48), Direction.RIGHT).callback(() -> gamePanel.player.teleport(
                                getWorld("Zuhause"),
                                9 * gamePanel.tileSize,
                                2 * gamePanel.tileSize - 1,
                                true
                        ))).load()
        );

        changeWorld(getWorld("Zuhause"));
    }

    public void save() {
        worlds.forEach(World::saveWorld);
    }

    public World getWorld(String name) {
        for (World world : worlds) {
            if (world.getName().equals(name)) return world;
        }
        return null;
    }

    public void changeWorld(World world) {
        gamePanel.stopEnvironmentEffect();
        currentWorld = world;
        gamePanel.tileManager.setTargetZoom(world.zoom);
        world.playEffects();
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

}
