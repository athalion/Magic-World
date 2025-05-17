package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.Projectile;
import de.athalion.game.twodgame.event.Event;
import de.athalion.game.twodgame.event.EventHandler;
import de.athalion.game.twodgame.graphics.EnvironmentEffects;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.particle.Particle;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class World {

    GamePanel gamePanel;
    String name;
    String displayName;
    int maxWorldCol, maxWorldRow;
    public int[][] mapTileNumber;
    EventHandler eventHandler;
    List<Entity> objectList = new ArrayList<>();
    List<Entity> monsterList = new ArrayList<>();
    List<Entity> NPCList = new ArrayList<>();
    List<Projectile> projectileList = new ArrayList<>();
    List<Particle> particles = new ArrayList<>();
    List<EnvironmentEffects.Type> effectFlags = new ArrayList<>();
    boolean cameraFixed = false;
    double zoom = 1;

    public World(GamePanel gamePanel, String name) {
        this.gamePanel = gamePanel;
        this.name = name;
    }

    public World addEvent(Event event) {
        eventHandler.addEvent(event.setId(String.valueOf(eventHandler.getEvents().size())));
        return this;
    }

    public void updateParticles() {
        particles.removeIf(Particle::update);
    }

    public void drawParticles(Graphics2D g2) {
        particles.forEach(particle -> particle.draw(g2));
    }

    public void spawnParticle(Particle.Texture texture, int x, int y) {
        particles.add(new Particle(texture, x, y, gamePanel));
    }

    public void checkEvent() {
        eventHandler.checkEvent();
    }

    public void loadWorld() {
        Logger.log("Loading map " + name + "...");
        try {
            List<String> lines = Files.readAllLines(Paths.get(
                    Objects.requireNonNull(getClass().getResource("maps/" + name + ".txt")).toURI()));

            maxWorldRow = lines.size();
            maxWorldCol = lines.getFirst().split(" ").length;
            mapTileNumber = new int[maxWorldCol][maxWorldRow];

            for (int row = 0; row < maxWorldRow; row++) {
                String[] numbers = lines.get(row).split(" ");
                for (int col = 0; col < maxWorldCol; col++) {
                    mapTileNumber[col][row] = Integer.parseInt(numbers[col]);
                }
            }

            Logger.log("Done!");
        } catch (IOException | URISyntaxException e) {
            Logger.error("Error loading map for world " + name + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
    }

    public String getName() {
        return name;
    }

    public List<Entity> getObjects() {
        return objectList;
    }

    public List<Entity> getNPCs() {
        return NPCList;
    }

    public List<Entity> getMonsters() {
        return monsterList;
    }

    public List<Projectile> getProjectiles() {
        return projectileList;
    }

    public List<EnvironmentEffects.Type> getEffectFlags() {
        return effectFlags;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public boolean isCameraFixed() {
        return cameraFixed;
    }

}
