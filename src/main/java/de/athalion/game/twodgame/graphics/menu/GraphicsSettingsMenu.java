package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.input.KeyState;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.utility.RenderUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GraphicsSettingsMenu implements MenuPage {

    GamePanel gamePanel;

    int commandNum = 0;
    int maxCommandNum = 2;

    public GraphicsSettingsMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        RenderUtils.fillScreenBlack(1F, g2, gamePanel);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = "Einstellungen - Grafik";
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 3;
        g2.drawString(text, x, y);

        maxCommandNum = 2;

        text = "Vollbild - " + (gamePanel.settings.fullscreen ? "Ein" : "Aus");
        y = gamePanel.tileSize * 5;
        if (commandNum == 0) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "Hardware Beschleunigung - " + (gamePanel.settings.hardwareAcceleration ? "Ein" : "Aus");
        y = gamePanel.tileSize * 6;
        if (commandNum == 1) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "Placeholder option";
        y = gamePanel.tileSize * 7;
        if (commandNum == 2) {
            g2.setColor(Color.ORANGE);
        } else g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        MenuUtils.drawControlTips(g2, gamePanel, "[W] hoch", "[S] runter", "[ENTER] auswählen", "[ESC] zurück");

    }

    @Override
    public MenuPage acceptInput(KeyState keyState, KeyEvent keyEvent) {

        MenuPage newMenuPage = null;

        if (keyState.isMenuUpPressed()) {
            commandNum--;
            if (commandNum < 0) commandNum = maxCommandNum;
        }
        if (keyState.isMenuDownPressed()) {
            commandNum++;
            if (commandNum > maxCommandNum) commandNum = 0;
        }
        if (keyState.isMenuOKPressed()) {
            switch (commandNum) {
                case 0:
                    if (gamePanel.settings.fullscreen) {
                        gamePanel.settings.fullscreen = false;
                        gamePanel.setFullScreen(false, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
                    } else {
                        GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
                        if (screenDevices.length == 1) {
                            List<JFrame> frames = new ArrayList<>();
                            for (int i = 0; i < screenDevices.length; i++) {
                                GraphicsDevice screenDevice = screenDevices[i];
                                JFrame frame = new JFrame();
                                frame.setSize(400, 200);
                                frame.setUndecorated(true);

                                Rectangle bounds = screenDevice.getDefaultConfiguration().getBounds();

                                int x = bounds.x + (bounds.width - frame.getWidth()) / 2;
                                int y = bounds.y + (bounds.height - frame.getHeight()) / 2;

                                frame.setLocation(x, y);

                                Button button = new Button("Click to open window here!");
                                int finalI = i;
                                button.addActionListener(_ -> {
                                    frames.forEach(Window::dispose);
                                    gamePanel.settings.fullscreen = true;
                                    gamePanel.settings.screen = finalI;
                                    gamePanel.setFullScreen(true, screenDevice);
                                });
                                frame.add(button);
                                frame.setVisible(true);

                                frames.add(frame);
                            }
                        } else {
                            gamePanel.setFullScreen(true, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
                            gamePanel.settings.fullscreen = true;
                            gamePanel.settings.screen = 0;
                        }
                    }
                    break;
                case 1:
                    gamePanel.settings.hardwareAcceleration = !gamePanel.settings.hardwareAcceleration;
                    System.setProperty("sun.java2d.opengl", gamePanel.settings.hardwareAcceleration ? "true" : "false");
                    Logger.log("Hardware acceleration " + (gamePanel.settings.hardwareAcceleration ? "enabled" : "disabled"));
                    break;
                case 2:

                    break;
            }

        }
        if (keyState.isMenuBackPressed()) {
            newMenuPage = new SettingsMenu(gamePanel);
        }

        return newMenuPage;

    }

}
