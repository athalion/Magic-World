package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class UtilityTool {

    public static BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;

    }

    public static int calculateDamage(Entity attacker, Entity attacked) {

        int damage;
        int levelDifference;
        int criticalChance;
        Random random = new Random();

        levelDifference = attacker.level - attacked.level;

        criticalChance = (levelDifference + 1) * 7;

        if (levelDifference < 0) {
            criticalChance = 0;
        }

        if (random.nextInt(100) + 1 <= criticalChance) {
            damage = (int) ((attacker.attack / 2 + (random.nextInt(attacker.attack) + 1)) * random.nextDouble(2) + 1);
        } else {
            damage = attacker.attack / 2 + (random.nextInt(attacker.attack) + 1);
        }

        damage -= attacked.defence / 2 + (random.nextInt(attacked.defence + 1));

        if (damage < 0) damage = 0;

        return damage;

    }

    public static void copyFolder(File source, File destination) {

        if (source.isDirectory()) {
            if (!destination.exists()) destination.mkdirs();

            String[] files = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void openErrorWindow(Exception e) {

        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
        System.exit(1);

    }

    public static boolean isIgnoredKey(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Sondertasten (keine Zeichen)
        if (keyCode == KeyEvent.VK_SHIFT ||
                keyCode == KeyEvent.VK_CONTROL ||
                keyCode == KeyEvent.VK_ALT ||
                keyCode == KeyEvent.VK_META ||
                keyCode == KeyEvent.VK_CAPS_LOCK ||
                keyCode == KeyEvent.VK_TAB ||
                keyCode == KeyEvent.VK_ESCAPE ||
                keyCode == KeyEvent.VK_ENTER ||
                keyCode == KeyEvent.VK_BACK_SPACE ||
                keyCode == KeyEvent.VK_DELETE ||
                keyCode == KeyEvent.VK_INSERT ||
                keyCode == KeyEvent.VK_HOME ||
                keyCode == KeyEvent.VK_END ||
                keyCode == KeyEvent.VK_PAGE_UP ||
                keyCode == KeyEvent.VK_PAGE_DOWN ||
                keyCode == KeyEvent.VK_UP ||
                keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_LEFT ||
                keyCode == KeyEvent.VK_RIGHT) {
            return true;
        }

        // Funktionstasten (F1 bis F12)
        if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12) {
            return true;
        }

        // Ungültige Zeichen für Dateinamen in Windows
        char keyChar = e.getKeyChar();
        if (keyChar == '\\' ||  // Backslash
                keyChar == '/' ||   // Forward slash
                keyChar == ':' ||   // Doppelpunkt
                keyChar == '*' ||   // Stern
                keyChar == '?' ||   // Fragezeichen
                keyChar == '"' ||   // Anführungszeichen
                keyChar == '<' ||   // Kleiner-als-Zeichen
                keyChar == '>' ||   // Größer-als-Zeichen
                keyChar == '|'      // Pipe
        ) {
            return true;
        }

        // Ignoriere sonstige Zeichen, die nicht druckbar sind
        return !Character.isLetterOrDigit(keyChar) && !Character.isSpaceChar(keyChar) && !Character.isDefined(keyChar);// Alle anderen Tasten sind erlaubt
    }

}