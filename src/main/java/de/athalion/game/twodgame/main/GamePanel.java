package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.Player;
import de.athalion.game.twodgame.entity.Projectile;
import de.athalion.game.twodgame.graphics.EnvironmentEffects;
import de.athalion.game.twodgame.graphics.ui.UI;
import de.athalion.game.twodgame.input.KeyHandler;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.schedule.Scheduler;
import de.athalion.game.twodgame.sound.Sound;
import de.athalion.game.twodgame.utility.CollisionChecker;
import de.athalion.game.twodgame.utility.UtilityTool;
import de.athalion.game.twodgame.world.TileManager;
import de.athalion.game.twodgame.world.WorldManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    //DEV
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
        setFullScreen(settings.fullscreen);

        tempScreen = graphicsConfiguration.createCompatibleImage(screenWidth, screenHeight, Transparency.TRANSLUCENT);
    }

    public void setFullScreen(boolean fullScreen) {

        if (Main.window != null) {
            Main.window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Main.window.dispatchEvent(new WindowEvent(Main.window, WindowEvent.WINDOW_CLOSING));
        }

        Main.window = new JFrame("Magic World SNAPSHOT 2.1");
        Main.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main.window.add(this);

        if (fullScreen) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            Main.window.setPreferredSize(screenSize);
            Main.window.setUndecorated(true);
            Main.window.setCursor(Main.blankCursor);

            drawWidth = (int) screenSize.getWidth();
            drawHeight = (int) screenSize.getHeight();
        } else {
            drawWidth = screenWidth;
            drawHeight = screenHeight;
        }

        Main.window.setResizable(false);
        Main.window.setVisible(true);
        Main.window.pack();

        Main.window.createBufferStrategy(3);
        bs = Main.window.getBufferStrategy();

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {

                if (settings.enableController) {
                    keyHandler.checkControllerInput();
                }

                update();
                drawScreen();

                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        scheduler.tick();

        if (gameState != GameState.LOGO && gameState != GameState.TITLE) {
            if (animationTimer >= 6) {
                animationTimer = 0;
                tileManager.updateAnimations();
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
                    playMusic(music.index, true);
                } else if (music.next != 999) {
                    playMusic(music.next, music.nextLooped);
                    music.next = 999;
                }

                musicEnd = true;
            }
        }

        environmentEffectEnd = false;
        if (environmentEffectPlaying) {
            environmentEffectTimer -= (double) 1 / 60;

            if (environmentEffectTimer <= 0) {
                if (environment.isLooped) {
                    playEnvironmentEffect(environment.index, true);
                } else if (environment.next != 999) {
                    playEnvironmentEffect(environment.next, environment.nextLooped);
                    environment.next = 999;
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
        if (gameState != GameState.LOGO) {
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

    public void saveGame() {
        worldManager.save();
    }

    public void newGame(String name) {

        createSaveDir();
        File file = new File(System.getProperty("user.dir") + "/saves", name);

        if (!file.exists()) {
            file.mkdir();
            try {
                UtilityTool.copyFolder(new File(getClass().getResource("/new_game/").toURI().getPath()), file);
            } catch (URISyntaxException e) {
                Logger.error("Error while copying game files to new game " + name + ": " + e.getMessage());
                Logger.stackTrace(e.getStackTrace());
            }
            loadGame(name);
        }

    }

    public List<String> getSaves() {

        createSaveDir();
        List<String> saves = new ArrayList<>();
        File saveDirectory = new File(System.getProperty("user.dir"), "saves");

        if (saveDirectory.exists() && saveDirectory.isDirectory()) {
            if (saveDirectory.listFiles() != null) {
                for (File file : saveDirectory.listFiles()) {
                    if (file.isDirectory()) {
                        saves.add(file.getName());
                    }
                }
            }
        }

        return saves;

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

    public void playMusicIntro(int i, int next, boolean looped) {
        playMusic(i, false);

        music.next = next;
        music.nextLooped = looped;
    }

    public void playMusic(int i, boolean looped) {
        music.setFile(i);
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

    public boolean scheduleMusic(int i, boolean looped) {
        if (musicEnd) {
            stopMusic();
            playMusic(i, looped);
            return true;
        } else return false;
    }

    public void playEnvironmentEffect(int i, boolean looped) {
        environment.setFile(i);
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

    public boolean scheduleEnvironmentEffect(int i, boolean looped) {
        if (environmentEffectEnd) {
            stopEnvironmentEffect();
            playEnvironmentEffect(i, looped);
            return true;
        } else return false;
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
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
            scheduler.runTaskLater(new Task(() -> {
                tileManager.cameraX = x + (random.nextInt(amount) - ((double) amount / 2));
                tileManager.cameraY = y + (random.nextInt(amount) - ((double) amount / 2));
            }, 10 * i));
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
