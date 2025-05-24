package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.Player;
import de.athalion.game.twodgame.entity.Projectile;
import de.athalion.game.twodgame.graphics.EnvironmentEffects;
import de.athalion.game.twodgame.graphics.ui.UI;
import de.athalion.game.twodgame.input.KeyHandler;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.save.Settings;
import de.athalion.game.twodgame.schedule.Scheduler;
import de.athalion.game.twodgame.sound.Sound;
import de.athalion.game.twodgame.utility.CollisionChecker;
import de.athalion.game.twodgame.utility.Requirements;
import de.athalion.game.twodgame.utility.UtilityTool;
import de.athalion.game.twodgame.world.WorldManager;
import de.athalion.game.twodgame.world.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    //debug
    public boolean pauseUpdates = false;

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int halfWidth = screenWidth / 2;
    public final int halfHeight = screenHeight / 2;
    final int FPS = 60;

    private int drawWidth;
    private int drawHeight;

    public int logoTimer = 0;

    GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage tempScreen;
    BufferedImage tempUIImage;
    BufferStrategy bs;
    boolean pauseDrawing = false;

    Scheduler scheduler = new Scheduler();

    int animationTimer = 0;
    public TileManager tileManager = new TileManager(this);
    public WorldManager worldManager = new WorldManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public Settings settings = new Settings();

    Sound music = new Sound();
    Sound soundEffect = new Sound();
    Sound environment = new Sound();
    double musicTimer = 0;
    boolean musicEnd = false;
    boolean musicPlaying = false;
    double environmentEffectTimer = 0;
    boolean environmentEffectEnd = false;
    boolean environmentEffectPlaying = false;

    public UI ui = new UI(this);
    EnvironmentEffects environmentEffects = new EnvironmentEffects(this);
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public Player player = new Player(this, keyHandler);

    public GameState gameState;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        gameState = GameState.LOGO;

        settings = Settings.loadSettings();
        setFullScreen(settings.fullscreen, GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[settings.screen]);

        tempScreen = graphicsConfiguration.createCompatibleImage(screenWidth, screenHeight, Transparency.TRANSLUCENT);
    }

    public void setFullScreen(boolean fullScreen, GraphicsDevice targetDevice) {
        pauseDrawing = true;

        if (Main.window == null) {
            Main.window = new JFrame("Magic World SNAPSHOT 2.3");
            Main.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Main.window.add(this);
        }

        if (fullScreen) {
            Main.window.dispose();
            Main.window.setUndecorated(true);

            Dimension screenSize = targetDevice.getDefaultConfiguration().getBounds().getSize();
            Main.window.setSize(screenSize);
            Main.window.setResizable(false);
            Main.window.setCursor(Main.blankCursor);

            targetDevice.setFullScreenWindow(Main.window);

            drawWidth = (int) screenSize.getWidth();
            drawHeight = (int) screenSize.getHeight();

        } else {
            if (targetDevice.getFullScreenWindow() == Main.window) {
                targetDevice.setFullScreenWindow(null);
            }

            Main.window.dispose();
            Main.window.setUndecorated(false);

            Main.window.setSize(screenWidth, screenHeight);
            Main.window.setResizable(false);
            Main.window.setCursor(Cursor.getDefaultCursor());
            Main.window.setLocationRelativeTo(null);

            drawWidth = screenWidth;
            drawHeight = screenHeight;
        }

        Main.window.setVisible(true);

        Main.window.createBufferStrategy(3);
        bs = Main.window.getBufferStrategy();

        pauseDrawing = false;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final double drawInterval = 1_000_000_000.0 / FPS;
        long lastTime = System.nanoTime();
        double delta = 0;
        long timer = 0;
        int frames = 0;

        while (gameThread != null) {
            long now = System.nanoTime();
            delta += (now - lastTime) / drawInterval;
            timer += (now - lastTime);
            lastTime = now;

            boolean updated = false;
            while (delta >= 1) {
                if (settings.enableController) {
                    keyHandler.checkControllerInput();
                }
                update();
                delta--;
                updated = true;
            }

            if (updated) {
                drawScreen();
                frames++;
            }

            if (timer >= 1_000_000_000) {
                frames = 0;
                timer = 0;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void update() {

        scheduler.tick();

        if (gameState != GameState.LOGO && gameState != GameState.TITLE) {
            if (animationTimer >= 6) {
                animationTimer = 0;
                tileManager.getTileSet().updateAnimations();
                worldManager.getCurrentWorld().updateParticles();
            }
            animationTimer++;
            tileManager.update();
        }

        if (gameState == GameState.PLAY) {

            //player
            player.update();

            //NPC
            if (!pauseUpdates) {
                worldManager.getCurrentWorld().getNPCs().stream().filter(Objects::nonNull).forEach(Entity::update);

                List<Entity> monsters = worldManager.getCurrentWorld().getMonsters();
                monsters.stream().filter(m -> m.alive && !m.dying).forEach(Entity::update);
                monsters.removeIf(m -> !m.alive);

                List<Projectile> projectilesList = worldManager.getCurrentWorld().getProjectiles();
                projectilesList.stream().filter(p -> p.alive).forEach(Projectile::update);
                projectilesList.removeIf(p -> !p.alive);
            }

            tileManager.setTarget(player.worldX, player.worldY);
        }

        if (gameState == GameState.PAUSE) {

        }

        if (gameState == GameState.LOGO) {
            logoTimer++;
            if (logoTimer > 800) {
                remove(Main.logo);
            }
            if (logoTimer >= 900) {
                gameState = GameState.TITLE;
                playMusic(Sound.MUSIC_MENU, true);
                logoTimer = 0;
            }
        }

        musicEnd = false;
        if (musicPlaying) {
            musicTimer -= (double) 1 / 60;
            if (musicTimer <= 0) {
                if (music.isLooped) {
                    playMusic(music.file, true);
                } else if (!music.next.isEmpty()) {
                    playMusic(music.next, music.nextLooped);
                    music.next = "";
                }

                musicEnd = true;
            }
        }

        environmentEffectEnd = false;
        if (environmentEffectPlaying) {
            environmentEffectTimer -= (double) 1 / 60;

            if (environmentEffectTimer <= 0) {
                if (environment.isLooped) {
                    playEnvironmentEffect(environment.file, true);
                } else if (!environment.next.isEmpty()) {
                    playEnvironmentEffect(environment.next, environment.nextLooped);
                    environment.next = "";
                }

                environmentEffectEnd = true;
            }
        }

    }

    public void draw(Graphics2D g2) {

        if (gameState != GameState.LOGO && gameState != GameState.TITLE) {

            //Tiles
            tileManager.draw(g2, worldManager.getCurrentWorld());

            //sort entities
            List<Entity> entityList = new ArrayList<>();

            entityList.add(player);

            for (Entity value : worldManager.getCurrentWorld().getObjects()) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            for (Entity value : worldManager.getCurrentWorld().getNPCs()) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            for (Entity value : worldManager.getCurrentWorld().getMonsters()) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            for (Projectile value : worldManager.getCurrentWorld().getProjectiles()) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            //draw entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            //draw particles
            worldManager.getCurrentWorld().drawParticles(g2);

            //draw environment effects
            environmentEffects.draw(g2, worldManager.getCurrentWorld().getEffectFlags().toArray(EnvironmentEffects.Type[]::new));

        }

    }

    public void drawScreen() {
        if (gameState != GameState.LOGO && !pauseDrawing) {
            draw((Graphics2D) tempScreen.getGraphics());
            double x = drawWidth * (tileManager.zoom - 1) / 2;
            double y = drawHeight * (tileManager.zoom - 1) / 2;
            bs.getDrawGraphics().drawImage(tempScreen, (int) -x, (int) -y, (int) (drawWidth * tileManager.zoom), (int) (drawHeight * tileManager.zoom), null);
            tempUIImage = graphicsConfiguration.createCompatibleImage(screenWidth, screenHeight, Transparency.TRANSLUCENT);
            ui.draw((Graphics2D) tempUIImage.getGraphics());
            bs.getDrawGraphics().drawImage(tempUIImage, 0, 0, drawWidth, drawHeight, null);
            bs.show();
        }
    }

    public void loadGame(String name) {
        worldManager.load(System.getProperty("user.dir") + "/saves/" + name);
    }

    public void newGame(String name) {

        createSaveDir();
        File file = new File(System.getProperty("user.dir") + "/saves", name);

        if (!file.exists()) {
            file.mkdir();
            try {
                UtilityTool.copyFolder(new File(Requirements.notNull(getClass().getResource("/new_game/"), "Failed creating new game. /new_game/ is null!").toURI().getPath()), file);
            } catch (URISyntaxException e) {
                Logger.error("Error while copying game files to new game " + name + ": " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
            loadGame(name);
        }

    }

    public void simpleDialog(String dialog) {
        ui.currentSimpleDialog = dialog;
        gameState = GameState.MESSAGE;
    }

    public void createSaveDir() {
        File file = new File(System.getProperty("user.dir"), "saves");
        if (!file.exists()) file.mkdir();
    }

    public void saveSettings() {
        Settings.saveSettings(settings);
    }

    public void updateVolume() {
        if (settings.enableSound) {
            music.updateVolume(settings.musicVolume);
            soundEffect.updateVolume(settings.FXVolume);
            environment.updateVolume(settings.environmentVolume);
        } else {
            music.updateVolume(0);
            soundEffect.updateVolume(0);
            environment.updateVolume(0);
        }
    }

    public void playMusicIntro(String sound, String next, boolean looped) {
        playMusic(sound, false);

        music.next = next;
        music.nextLooped = looped;
    }

    public void playMusic(String sound, boolean looped) {
        music.setFile(sound);
        music.setLooped(looped);
        updateVolume();
        music.play();

        musicPlaying = true;

        musicTimer = (double) music.getLength() / 1000000;
    }

    public void stopMusic() {
        music.stop();
        music.setLooped(false);

        musicPlaying = false;
    }

    public boolean scheduleMusic(String sound, boolean looped) {
        if (musicEnd) {
            stopMusic();
            playMusic(sound, looped);
            return true;
        } else return false;
    }

    public void playEnvironmentEffect(String sound, boolean looped) {
        environment.setFile(sound);
        environment.setLooped(looped);
        updateVolume();
        environment.play();

        environmentEffectPlaying = true;

        environmentEffectTimer = (double) environment.getLength() / 1000000;
    }

    public void stopEnvironmentEffect() {
        environment.stop();
        environment.setLooped(false);

        environmentEffectPlaying = false;
    }

    public boolean scheduleEnvironmentEffect(String sound, boolean looped) {
        if (environmentEffectEnd) {
            stopEnvironmentEffect();
            playEnvironmentEffect(sound, looped);
            return true;
        } else return false;
    }

    public void playSoundEffect(String sound) {
        soundEffect.setFile(sound);
        soundEffect.updateVolume(settings.FXVolume);
        soundEffect.play();
    }

    public void stopSoundEffect() {
        soundEffect.stop();
    }

    public void doControllerVibration(float leftMagnitude, float rightMagnitude, int duration) {
        for (int i = 0; i < keyHandler.controllers.getNumControllers(); i++) {
            keyHandler.controllers.doVibration(i, leftMagnitude, rightMagnitude, duration);
        }
    }

    public void doScreenShake(int amount, int duration) {
        Random random = new Random();
        double x = tileManager.cameraX;
        double y = tileManager.cameraY;
        tileManager.cameraX = x + (random.nextInt(amount) - ((double) amount / 2));
        tileManager.cameraY = y + (random.nextInt(amount) - ((double) amount / 2));
        for (int i = 0; i < duration; i++) {
            scheduler.runTaskLater(() -> {
                tileManager.cameraX = x + (random.nextInt(amount) - ((double) amount / 2));
                tileManager.cameraY = y + (random.nextInt(amount) - ((double) amount / 2));
            }, 10 * i);
        }
    }

    public void quit() {
        Logger.log("Exiting...");
        saveSettings();
        settings.enableController = false;
        keyHandler.quitGamepad();
        Logger.saveLog();
        System.exit(0);
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

}
