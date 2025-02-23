package de.athalion.game.twodgame.graphics.ui;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.graphics.CutSceneManager;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.menu.MenuManager;
import de.athalion.game.twodgame.object.OBJ_Heart;
import de.athalion.game.twodgame.sound.Sound;
import de.athalion.game.twodgame.utility.RenderUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font atlantis;

    BufferedImage heart_full, heart_half, heart_blank;

    public MenuManager menuManager;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialog;

    public String currentSimpleDialog;

    public Entity currentTarget;
    boolean playerHasTarget = false;

    public int commandNum = 0;
    public int maxCommandNum = 0;

    public int slotCol = 0;
    public int slotRow = 0;

    public CutSceneManager cutSceneManager;
    public int cutSceneIndex = 0;

    public Entity npc;

    public int charIndex;
    String combinedText = "";
    public String[] response = new String[3];
    public int[] responseCMD = new int[3];
    public int responses = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        Logger.log("Initializing UI...");

        menuManager = new MenuManager(gamePanel);
        cutSceneManager = new CutSceneManager(gamePanel, this);

        try {
            Logger.log("Loading Font...");
            InputStream is = getClass().getResourceAsStream("/font/atlantis.ttf");
            atlantis = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            Logger.error("Error reading custom font: " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        //create hud object
        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(atlantis);
        g2.setColor(Color.WHITE);

        //title state
        if (gamePanel.gameState == GameState.TITLE) {
            menuManager.render(g2);
        }
        // play state
        if (gamePanel.gameState == GameState.PLAY) {
            if (playerHasTarget) drawTarget();
            drawPlayerLife();
            drawMessage();
        }
        // pause state
        if (gamePanel.gameState == GameState.PAUSE) {
            drawPauseScreen();
            drawPlayerLife();
        }
        //dialog state
        if (gamePanel.gameState == GameState.DIALOG) {
            drawPlayerLife();
            drawDialog();
        }
        //message state
        if (gamePanel.gameState == GameState.MESSAGE) {
            drawInfo();
        }
        //inventory state
        if (gamePanel.gameState == GameState.INVENTORY) {
            drawInventoryScreen();
        }
        //cutScene state
        if (gamePanel.gameState == GameState.CUTSCENE) {
            drawCutScene();
        }

    }

    private void drawCutScene() {
        cutSceneManager.playCutScene(cutSceneIndex, g2);
    }

    private void drawPlayerLife() {

        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        //draw blank hearts
        while (i < gamePanel.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        //reset
        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        //draw current life
        while (i < gamePanel.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gamePanel.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }

    }

    public void drawMessage() {

        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));

        for (int i = 0; i < message.size(); i++) {

            if (message.get(i) != null) {

                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 42;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }

            }

        }

    }

    public void drawPauseScreen() {
        g2.setFont(atlantis);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 96F));
        g2.setColor(Color.WHITE);

        String text = "PAUSED!";
        int x = RenderUtils.getXForCenteredText(text, g2, gamePanel);
        int y = gamePanel.screenHeight / 2 + gamePanel.tileSize * 2;

        g2.drawString(text, x, y);
    }

    public void drawDialog() {

        //window
        int x = gamePanel.tileSize;
        int y = gamePanel.screenHeight - (gamePanel.tileSize * 5);
        int width = gamePanel.tileSize * 4;
        int height = gamePanel.tileSize * 4;

        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
        g2.drawImage(npc.characterImage, x, y, width, height, null);
        RenderUtils.drawSubWindow(x, y, width, height, 35, 0, Color.BLUE, g2);

        x = gamePanel.tileSize * 6;
        width = gamePanel.screenWidth - (gamePanel.tileSize * 7);

        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        if (npc.dialogues[npc.dialogueSet][npc.dialoguePage][0] != null) {

            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialoguePage][0].toCharArray();

            if (charIndex < characters.length) {
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialog = combinedText;
                charIndex++;
                gamePanel.playSoundEffect(Sound.EFFECT_TEXT);
            }

            if (gamePanel.keyHandler.enterPressed) {

                charIndex = 0;
                combinedText = "";

                if (gamePanel.gameState == GameState.DIALOG) {

                    npc.dialoguePage = responseCMD[commandNum];
                    gamePanel.keyHandler.enterPressed = false;

                    responses = 0;
                    Arrays.fill(response, "");
                    Arrays.fill(responseCMD, 0);

                    commandNum = 0;

                }

            }

        } else {

            if (gamePanel.gameState == GameState.DIALOG) {
                gamePanel.gameState = GameState.PLAY;
            }

            npc.dialoguePage = 0;

        }

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, x, y);
            y += 25;
        }

        responses = 0;
        for (int i = 0; i < 3; i++) {
            if (npc.dialogues[npc.dialogueSet][npc.dialoguePage][i + 1] != null) {
                responseCMD[i] = Integer.parseInt(npc.dialogues[npc.dialogueSet][npc.dialoguePage][i + 1].substring(0, 3));
                response[i] = npc.dialogues[npc.dialogueSet][npc.dialoguePage][i + 1].substring(3);
                responses++;
            } else {
                responseCMD[i] = 99;
                response[i] = "";
            }
        }

        y += 15;

        for (int i = 0; i < responses; i++) {

            if (commandNum == i) {
                g2.setColor(Color.ORANGE);
                g2.drawString(response[i], x, y);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString(response[i], x, y);
            }

            y += 25;

        }

    }

    public void drawSimpleDialog(int y, int height, String text, float textSize, boolean silent) {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, textSize));

        //window
        int width = gamePanel.tileSize * 16;
        int x = gamePanel.screenWidth / 2 - (width / 2);

        currentSimpleDialog = addLineBreaks(text, width - 40, g2);

        int lineHeight = (int) (g2.getFontMetrics().getStringBounds(text, g2).getHeight() / 1.3);

        if (height == -1) {

            int lines = currentSimpleDialog.split("\n").length;

            height = lineHeight * lines + 37;

            y = (gamePanel.screenHeight - gamePanel.tileSize) - (height);

        }

        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);

        x += 25;
        y += 37;

        char[] characters = currentSimpleDialog.toCharArray();

        if (charIndex < characters.length) {
            String s = String.valueOf(characters[charIndex]);
            combinedText = combinedText + s;
            currentSimpleDialog = combinedText;
            charIndex++;
            if (!silent) gamePanel.playSoundEffect(Sound.EFFECT_TEXT);
        }

        for (String line : currentSimpleDialog.split("\n")) {
            g2.drawString(line, x, y);
            y += lineHeight;
        }

        if (gamePanel.keyHandler.enterPressed) {

            charIndex = 0;
            currentSimpleDialog = "";
            combinedText = "";

        }

    }

    public void drawInfo() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

        //window
        int width = gamePanel.tileSize * 16;
        int x = gamePanel.screenWidth / 2 - (width / 2);

        String message = addLineBreaks(currentSimpleDialog, width - 40, g2);

        int lineHeight = (int) (g2.getFontMetrics().getStringBounds(currentSimpleDialog, g2).getHeight() / 1.3);

        int lines = message.split("\n").length;

        int height = lineHeight * lines + 37;

        int y = (gamePanel.screenHeight - gamePanel.tileSize) - (height);

        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);

        x += 25;
        y += 37;

        char[] characters = message.toCharArray();

        if (charIndex < characters.length) {
            String s = String.valueOf(characters[charIndex]);
            combinedText = combinedText + s;
            message = combinedText;
            charIndex++;
            gamePanel.playSoundEffect(Sound.EFFECT_TEXT);
        }

        for (String line : message.split("\n")) {
            g2.drawString(line, x, y);
            y += lineHeight;
        }

        if (gamePanel.keyHandler.enterPressed) {

            charIndex = 0;
            combinedText = "";

            gamePanel.gameState = GameState.PLAY;

            gamePanel.player.attackCanceled = true;

        }

    }

    public void drawInventoryScreen() {

        int frameX = gamePanel.tileSize * 8 - (gamePanel.tileSize / 4);
        int frameY = gamePanel.tileSize * 3 - (gamePanel.tileSize / 8);
        int frameWidth = gamePanel.tileSize * 6 - (gamePanel.tileSize / 6);
        int frameHeight = gamePanel.tileSize * 5 - (gamePanel.tileSize / 6);

        RenderUtils.drawSubWindow(frameX, frameY, frameWidth, frameHeight, 35, 0, Color.BLUE, g2);

        //slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize;

        //draw item
        for (int i = 0; i < gamePanel.player.inventory.size(); i++) {

            if (gamePanel.player.inventory.get(i) == gamePanel.player.currentWeapon || gamePanel.player.inventory.get(i) == gamePanel.player.currentOffhandItem) {

                g2.setColor(new Color(218, 137, 27, 130));
                g2.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);

            }

            g2.drawImage(gamePanel.player.inventory.get(i).down1, slotX, slotY, slotSize, slotSize, null);
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = slotSize;
        int cursorHeight = slotSize;

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //description
        frameX += (int) (gamePanel.tileSize * 6.2);

        RenderUtils.drawSubWindow(frameX, frameY, frameWidth, frameHeight, 35, 0, Color.BLUE, g2);

        //draw text
        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

        int itemIndex = getItemIndex(slotCol, slotRow);

        if (!(itemIndex >= gamePanel.player.inventory.size())) {

            String originalDescription = gamePanel.player.inventory.get(itemIndex).description;
            String description;

            description = addLineBreaks(originalDescription, frameWidth - 10, g2);

            for (String line : description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }

        }

    }

    public void drawTarget() {

        playerHasTarget = true;

        int x = (int) (gamePanel.screenWidth - (gamePanel.tileSize * 5.2));
        int y = gamePanel.tileSize / 5;
        int width = gamePanel.tileSize * 5;
        int height = gamePanel.tileSize * 2;

        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
        g2.drawImage(currentTarget.characterImage, x + 15, y + 15, null);
        RenderUtils.drawSubWindow(x + 10, y + 10, height - 20, height - 20, 25, 0, Color.BLUE, g2);

        double oneScale = (double) gamePanel.tileSize * 2.5 / currentTarget.maxLife;
        double hpBarValue = oneScale * currentTarget.life;

        g2.setColor(Color.BLACK);
        g2.fillRect(x + gamePanel.tileSize * 2 + 1, y + 16, (int) (gamePanel.tileSize * 2.5 + 2), gamePanel.tileSize / 2 + 2);
        g2.setColor(Color.RED);
        g2.fillRect(x + gamePanel.tileSize * 2, y + 15, (int) hpBarValue, gamePanel.tileSize / 2);

        int xDistance = Math.abs(gamePanel.player.worldX - currentTarget.worldX);
        int yDistance = Math.abs(gamePanel.player.worldY - currentTarget.worldY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.tileSize * 10) playerHasTarget = false;

    }

    public int getXForCenteredImage(BufferedImage image) {
        return gamePanel.screenWidth / 2 - (image.getWidth() / 2);
    }

    public int getXForCenteredSomething(int centeredSomethingWidth) {
        return gamePanel.screenWidth / 2 - (centeredSomethingWidth / 2);
    }

    public int getItemIndex(int col, int row) {
        return col + (row * 5);
    }

    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * 5);
    }

    public String addLineBreaks(String text, int maxWidth, Graphics2D graphics) {

        String finalText = "";

        if (text != null) {
            StringBuilder textLineBreaks = new StringBuilder();
            StringBuilder textCombined = new StringBuilder();

            List<String> words = new ArrayList<>(Arrays.stream(text.split(" ")).toList());
            words.add(" ");

            for (int i = 0; i < words.size() - 1; i++) {
                textLineBreaks.append(words.get(i));
                textLineBreaks.append(" ");
                String nextWord = words.get(i + 1);
                int textWidth = (int) graphics.getFontMetrics().getStringBounds(textLineBreaks + nextWord, graphics).getWidth();
                if (textWidth > maxWidth) {
                    textLineBreaks.append("\n");
                    textCombined.append(textLineBreaks);
                    textLineBreaks = new StringBuilder();
                }
            }

            finalText = textCombined.append(textLineBreaks).toString();

        }

        return finalText;
    }

    public BufferedImage loadImage(String path) {
        Logger.log("Loading image " + path);

        BufferedImage image = null;
        InputStream inputStream = getClass().getResourceAsStream(path + ".png");

        if (inputStream == null)
            Logger.error("Cannot load image because " + path + " is null!");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            Logger.error("Error reading texture " + path + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        return image;
    }

}