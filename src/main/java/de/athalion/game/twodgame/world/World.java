package de.athalion.game.twodgame.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityDeserializer;
import de.athalion.game.twodgame.entity.EntitySerializer;
import de.athalion.game.twodgame.entity.Projectile;
import de.athalion.game.twodgame.event.Event;
import de.athalion.game.twodgame.event.EventDeserializer;
import de.athalion.game.twodgame.event.EventHandler;
import de.athalion.game.twodgame.event.EventSerializer;
import de.athalion.game.twodgame.graphics.EnvironmentEffects;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.particle.Particle;
import de.athalion.game.twodgame.sound.Sound;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class World {

    GamePanel gamePanel;
    String mapName;
    String mapDirPath;
    File mapDir;
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

    public World(GamePanel gamePanel, String mapDirPath) {

        this.mapDirPath = mapDirPath;
        this.gamePanel = gamePanel;

        eventHandler = new EventHandler(gamePanel);

        mapDir = new File(mapDirPath);

    }

    public World load() {
        loadWorld(mapDirPath);
        return this;
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

    public void spawnParticle(String texture, int x, int y) {
        particles.add(new Particle(texture, x, y, gamePanel));
    }

    public void checkEvent() {
        eventHandler.checkEvent();
    }

    public void loadWorld(String mapDirPath) {
        Logger.log("Preparing to load world " + mapName + "...");

        //PREPARE
        String mapPath = "/maps/" + Arrays.stream(mapDirPath.split("/")).toList().getLast() + ".txt";

        String objectPath = mapDirPath + "/objects.json";
        String monsterPath = mapDirPath + "/monsters.json";
        String NPCPath = mapDirPath + "/NPCs.json";
        String eventPath = mapDirPath + "/events.json";

        InputStream mapInputStream = getClass().getResourceAsStream(mapPath);
        BufferedReader mapReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(mapInputStream)));

        //READ ENTITY AND EVENT DATA
        Logger.log("Loading entities and events...");
        try {

            File objectFile = new File(objectPath);
            if (objectFile.exists()) {
                JsonReader reader = new JsonReader(new FileReader(objectFile));
                objectList = EntityDeserializer.deserialize(JsonParser.parseReader(reader), gamePanel);
            }

            File monsterFile = new File(monsterPath);
            if (monsterFile.exists()) {
                JsonReader reader = new JsonReader(new FileReader(monsterFile));
                monsterList = EntityDeserializer.deserialize(JsonParser.parseReader(reader), gamePanel);
            }

            File NPCFile = new File(NPCPath);
            if (NPCFile.exists()) {
                JsonReader reader = new JsonReader(new FileReader(NPCFile));
                NPCList = EntityDeserializer.deserialize(JsonParser.parseReader(reader), gamePanel);
            }

            File eventFile = new File(eventPath);
            if (eventFile.exists()) {
                JsonReader reader = new JsonReader(new FileReader(eventFile));
                EventDeserializer.deserialize(JsonParser.parseReader(reader), this);
            }

        } catch (FileNotFoundException e) {
            Logger.error("Error loading entities and events for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        //READ MAP
        Logger.log("Loading map...");
        try {

            mapName = mapReader.readLine();
            String environmentFlags = mapReader.readLine();
            if (!environmentFlags.equals("NONE")) {
                for (String s : environmentFlags.split(", ")) {
                    effectFlags.add(EnvironmentEffects.Type.valueOf(s));
                }
            }
            String cameraOptions = mapReader.readLine();
            if (cameraOptions.startsWith("FIXED")) {
                cameraFixed = true;
                zoom = Double.parseDouble(cameraOptions.substring(6));
            } else zoom = Double.parseDouble(cameraOptions);

            int col = 0;
            int row = 0;

            maxWorldCol = 1;
            maxWorldRow = 1;

            while (row < maxWorldRow) {

                String line = mapReader.readLine();
                String[] numbers = line.split(" ");

                if (mapTileNumber == null) {
                    Path path = Paths.get(Objects.requireNonNull(getClass().getResource(mapPath)).toURI());
                    mapTileNumber = new int[numbers.length][Files.readAllLines(path).size() - 3];
                    maxWorldCol = numbers.length;
                    maxWorldRow = Files.readAllLines(path).size() - 3;
                }

                while (col < maxWorldCol) {
                    mapTileNumber[col][row] = Integer.parseInt(numbers[col]);
                    col++;
                }

                col = 0;
                row++;

            }

            mapReader.close();
            Logger.log("Done!");

        } catch (IOException | URISyntaxException e) {
            Logger.error("Error loading map for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

    }

    public void saveWorld() {
        Logger.log("Preparing to save world " + mapName + "...");

        File objectFile = new File(mapDirPath, "/objects.json");
        File monsterFile = new File(mapDirPath, "/monsters.json");
        File NPCFile = new File(mapDirPath, "/NPCs.json");
        File eventFile = new File(mapDirPath, "/events.json");

        //SAVE ENTITY AND EVENT DATA
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (!mapDir.exists()) mapDir.mkdirs();
            if (!objectFile.exists()) objectFile.createNewFile();
            if (!monsterFile.exists()) monsterFile.createNewFile();
            if (!NPCFile.exists()) NPCFile.createNewFile();
            if (!eventFile.exists()) eventFile.createNewFile();
        } catch (IOException e) {
            Logger.error("Error creating save files for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
        try (Writer writer = new FileWriter(objectFile)) {
            writer.write(gson.toJson(EntitySerializer.serialize(objectList)));
        } catch (IOException e) {
            Logger.error("Error saving objects for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        try (Writer writer = new FileWriter(monsterFile)) {
            writer.write(gson.toJson(EntitySerializer.serialize(monsterList)));
        } catch (IOException e) {
            Logger.error("Error saving monsters for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        try (Writer writer = new FileWriter(NPCFile)) {
            writer.write(gson.toJson(EntitySerializer.serialize(NPCList)));
        } catch (IOException e) {
            Logger.error("Error saving NPCs for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        try (Writer writer = new FileWriter(eventFile)) {
            writer.write(gson.toJson(EventSerializer.serialize(eventHandler.getEvents())));
        } catch (IOException e) {
            Logger.error("Error saving events for world " + mapName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        Logger.log("Done!");

    }

    public void playEffects() {
        if (effectFlags.contains(EnvironmentEffects.Type.RAIN)) {
            gamePanel.playEnvironmentEffect(Sound.ENVIRONMENT_RAIN, true);
        }
        if (effectFlags.contains(EnvironmentEffects.Type.RAIN_DULL)) {
            gamePanel.playEnvironmentEffect(Sound.ENVIRONMENT_RAIN_DULL, true);
        }
    }

    public Event getEvent(String id) {
        for (Event event : eventHandler.getEvents()) {
            if (event.getId().equals(id)) return event;
        }
        return null;
    }

    public String getName() {
        return mapName;
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

}
