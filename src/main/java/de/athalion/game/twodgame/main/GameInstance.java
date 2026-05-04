package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.entity.Player;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.graphics.menu.MenuManager;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.save.Settings;
import de.athalion.game.twodgame.schedule.Scheduler;
import de.athalion.game.twodgame.sound.SoundSystem;
import de.athalion.game.twodgame.world.WorldManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameInstance implements Runnable {

    private static GameInstance instance;

    Thread gameThread;
    GameFrame gameFrame;

    final int FPS = 60;
    int currentFPS = 0;

    GameState gameState = GameState.MENU;
    MenuManager menuManager = new MenuManager();
    WorldManager worldManager = new WorldManager();
    Camera camera;

    Player player;

    /**
     * Creates a new game instance and initializes a {@link Thread}.
     */
    public GameInstance() {
        instance = this;
        gameThread = new Thread(this);
    }

    /**
     * Initializes the game. <br>
     * This includes loading and applying the settings, initializing the sound system, initializing the controller system
     * and setting up fullscreen.
     */
    public void init() {
        gameFrame = new GameFrame();

        Settings.loadSettings();
        Settings.applySettings();
        SoundSystem.init();
        InputSystem.init();

        Runtime.getRuntime().addShutdownHook(new Thread(this::quit));
    }

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
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
                update();
                delta--;
                updated = true;
            }

            if (updated) {
                render();
                frames++;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
                currentFPS = frames;
                frames = 0;
            }

            long sleepTime = Math.max(2, (long) (drawInterval - (System.nanoTime() - lastTime)) / 1_000_000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void update() {
        InputSystem.processInput();
        Scheduler.tick();

        // TODO fix spaghetti
//        if (gameState != GameState.LOGO && gameState != GameState.TITLE) {
//            if (animationTimer >= 6) {
//                animationTimer = 0;
//                worldManager.getCurrentWorld().updateParticles();
//            }
//            animationTimer++;
//        }

        gameState.getUpdater().update();
        InputSystem.reset();
    }

    private void render() {
        GraphicsConfiguration defaultConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferStrategy bs = gameFrame.getBufferStrategy();

        BufferedImage tempImage = defaultConfiguration.createCompatibleImage(gameFrame.SCREEN_WIDTH, gameFrame.SCREEN_HEIGHT, Transparency.TRANSLUCENT);
        DrawContext drawContext = new DrawContext((Graphics2D) tempImage.getGraphics());
        gameState.getRenderPipeline().render(drawContext, camera);

        bs.getDrawGraphics().drawImage(tempImage, 0, 0, gameFrame.getDrawWidth(), gameFrame.getDrawHeight(), null);
        bs.show();
    }

    private void quit() {
        Logger.log("Exiting...");
        Settings.saveSettings();
        SoundSystem.shutdown();
        Settings.getSettings().enableController = false;
        InputSystem.quit();
        Logger.saveLog();
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public GameState getGameState() {
        return gameState;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public static GameInstance getInstance() {
        return instance;
    }

}
