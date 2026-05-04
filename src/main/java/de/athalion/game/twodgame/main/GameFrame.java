package de.athalion.game.twodgame.main;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int TILE_SIZE = originalTileSize * scale;
    public final int MAX_SCREEN_COLUMNS = 20;
    public final int MAX_SCREEN_ROWS = 12;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMNS;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    int drawWidth = 0;
    int drawHeight = 0;

    public GameFrame() throws HeadlessException {
        super("Magic World " + Main.VERSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Opens the window in fullscreen or windowed mode.
     * This method also updates {@code drawWidth} and {@code drawHeight}.
     */
    public void setFullscreen(boolean fullscreen) {
        dispose();
        setUndecorated(fullscreen);
        setResizable(false);

        if (fullscreen) {
            GraphicsDevice targetDevice = getCurrentScreen();
            if (targetDevice == null) targetDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            Rectangle screenBounds = targetDevice.getDefaultConfiguration().getBounds();
            setLocation(screenBounds.getLocation());
            setSize(screenBounds.getSize());
            setCursor(new BlankCursor());

            drawWidth = (int) screenBounds.getWidth();
            drawHeight = (int) screenBounds.getHeight();
        } else {
            setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
            setCursor(Cursor.getDefaultCursor());
            setLocationRelativeTo(null);

            drawWidth = SCREEN_WIDTH;
            drawHeight = SCREEN_HEIGHT;
        }

        setVisible(true);

        createBufferStrategy(3);
    }

    private @Nullable GraphicsDevice getCurrentScreen() {
        GraphicsDevice[] list = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice graphicsDevice : list) {
            if (graphicsDevice.getDefaultConfiguration().getBounds().contains(getLocation())) return graphicsDevice;
        }
        return null;
    }

    public int getDrawWidth() {
        return drawWidth;
    }

    public int getDrawHeight() {
        return drawHeight;
    }

}
