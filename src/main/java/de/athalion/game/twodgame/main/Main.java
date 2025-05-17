package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.sound.Sound;
import de.athalion.game.twodgame.utility.Requirements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main {

    public static JFrame window;
    public static JLabel logo;
    public static Cursor blankCursor;

    public static void main(String[] args) {

        Logger.createSession();

        GamePanel gamePanel = new GamePanel();
        gamePanel.setupGame();

        Logger.log("Loading logo...");
        URL img = Main.class.getResource("/menu/logo.gif");
        ImageIcon icon = new ImageIcon(Requirements.notNull(img, "Could not load logo because /menu/logo.gif is null!"));

        icon.setImage(icon.getImage().getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_FAST));
        logo = new JLabel("", icon, SwingConstants.CENTER);

        gamePanel.add(logo);
        gamePanel.playSoundEffect(Sound.EFFECT_LOGO);
        window.pack();

        BufferedImage cursorImage = null;
        try {
            Logger.log("Creating blank cursor...");
            cursorImage = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/blank_cursor.png")));
        } catch (IOException e) {
            Logger.error("Error loading blank cursor: " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
        blankCursor = window.getToolkit().createCustomCursor(cursorImage, new Point(), "blank");

        gamePanel.startGameThread();

    }

}