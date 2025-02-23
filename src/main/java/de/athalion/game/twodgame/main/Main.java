package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.sound.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
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

        URL img = null;

        try {
            Logger.log("Loading logo...");
            img = new URL(Main.class.getResource("/menu/logo.gif").toString());
        } catch (MalformedURLException e) {
            Logger.error("Error loading logo: " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
        ImageIcon icon = new ImageIcon(img);

        icon.setImage(icon.getImage().getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_FAST));
        logo = new JLabel("", icon, 0);

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