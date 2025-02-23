package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.sound.Sound;
import de.athalion.game.twodgame.utility.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static JFrame window;
    public static JLabel logo;
    public static Cursor blankCursor;

    public static void main(String[] args) {

        GamePanel gamePanel = new GamePanel();
        gamePanel.setupGame();

        URL img = null;

        try {
            img = new URL(Main.class.getResource("/menu/logo.gif").toString());
        } catch (MalformedURLException e) {
            UtilityTool.openErrorWindow(e);
        }
        ImageIcon icon = new ImageIcon(img);

        icon.setImage(icon.getImage().getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_FAST));
        logo = new JLabel("", icon, 0);

        gamePanel.add(logo);
        gamePanel.playSoundEffect(Sound.EFFECT_LOGO);
        window.pack();

        BufferedImage cursorImage = null;
        try {
            cursorImage = ImageIO.read(Main.class.getResourceAsStream("/blank_cursor.png"));
        } catch (IOException e) {
            UtilityTool.openErrorWindow(e);
        }
        blankCursor = window.getToolkit().createCustomCursor(cursorImage, new Point(), "blank");

        gamePanel.startGameThread();

    }

}