package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.camera.configuration.CameraConfigurationHandler;
import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.event.EventHandler;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.location.Location;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.particle.Particle;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.world.tile.TileManager;

import java.util.ArrayList;
import java.util.List;

public class WorldInstance {

    World world;

    TileManager tileManager = new TileManager();
    EventHandler eventHandler;
    CameraConfigurationHandler cameraConfigurationHandler;

    List<Entity> entities = new ArrayList<>();
    List<Particle> particles = new ArrayList<>();

    public WorldInstance(World world) {
        this.world = world;
        tileManager.loadTileSet(Identifier.forPath("/maps/" + world.getNameKey() + "/tileset.json"));
        tileManager.loadMap(Identifier.forPath("/maps/" + world.getNameKey() + "/map.json"));
        eventHandler = new EventHandler(world.getEvents());
        cameraConfigurationHandler = CameraConfigurationHandler.fromJson(Identifier.forPath("/maps/" + world.getNameKey() + "/camera.json"));
    }

    public void spawnParticle(Texture particle, Vector location) {
        spawnParticle(particle, new Location(this, location));
    }

    public void spawnParticle(Texture particle, Location location) {
        particles.add(new Particle(particle, location));
    }

    public String getDisplayName() {
        return Translations.get("world." + world.getNameKey() + ".name");
    }

    public void postProcess(DrawContext context, Camera camera) {
        world.getPostProcessors().forEach(postProcessor -> postProcessor.process(context, camera));
    }

    public World getWorld() {
        return world;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public CameraConfigurationHandler getCameraConfigurationHandler() {
        return cameraConfigurationHandler;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Particle> getParticles() {
        return particles;
    }

}
