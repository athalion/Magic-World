package de.athalion.game.twodgame.graphics.ui;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.graphics.font.Fonts;
import de.athalion.game.twodgame.graphics.menu.MenuManager;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.resources.texture.Textures;
import de.athalion.game.twodgame.utility.Requirements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Massive 500-line-spaghetti-class
    Beware!
 */

public class UI {

    Font font;

    Texture blankHeartTexture = Textures.setup(Identifier.forPath("/textures/ui/heart_blank.png"), false);
    Texture fullHeartTexture = Textures.setup(Identifier.forPath("/textures/ui/heart_full.png"), false);
    Texture halfHeartTexture = Textures.setup(Identifier.forPath("/textures/ui/heart_half.png"), false);

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

    public Entity npc;

    public int charIndex;
    String combinedText = "";
    public String[] response = new String[3];
    public int[] responseCMD = new int[3];
    public int responses = 0;

    public UI() {
        Logger.log("Initializing UI...");

        Fonts.load();

        menuManager = new MenuManager();
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(DrawContext context) {

        //title state
        if (GameInstance.getInstance().getGameState() == GameState.MENU) {
            menuManager.render(context);
        }
        // play state
        if (GameInstance.getInstance().getGameState() == GameState.PLAY) {
            if (playerHasTarget) drawTarget();
            drawPlayerLife(context);
            drawMessage(context);
        }
        // pause state
        if (GameInstance.getInstance().getGameState() == GameState.PAUSE) {
            drawPauseScreen(context);
            drawPlayerLife(context);
        }
        //cutScene state
        if (GameInstance.getInstance().getGameState() == GameState.CUTSCENE) {
            //TODO cutscene system
        }

    }

    private void drawPlayerLife(DrawContext context) {
        int x = GameInstance.getInstance().getGameFrame().TILE_SIZE
                / 2;
        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE
                / 2;
        int i = 0;

        //draw blank hearts
        while (i < GameInstance.getInstance().getPlayer().getStat(Stats.MAX_HEALTH) / 2) {
            context.drawTexture(blankHeartTexture, x, y);
            i++;
            x += GameInstance.getInstance().getGameFrame().TILE_SIZE
            ;
        }

        //reset
        x = GameInstance.getInstance().getGameFrame().TILE_SIZE
                / 2;
        i = 0;

        //draw current life
        while (i < GameInstance.getInstance().getPlayer().health) {
            context.drawTexture(halfHeartTexture, x, y);
            i++;
            if (i < GameInstance.getInstance().getPlayer().health) {
                context.drawTexture(halfHeartTexture, x, y);
            }
            i++;
            x += GameInstance.getInstance().getGameFrame().TILE_SIZE
            ;
        }
    }

    public void drawMessage(DrawContext context) {
        int messageX = GameInstance.getInstance().getGameFrame().TILE_SIZE
                ;
        int messageY = GameInstance.getInstance().getGameFrame().TILE_SIZE
                * 4;

        for (int i = 0; i < message.size(); i++) {

            if (message.get(i) != null) {

                context.drawString(message.get(i), messageX + 2, messageY + 2, Color.BLACK);

                context.drawString(message.get(i), messageX, messageY, Color.WHITE);

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

    public void drawPauseScreen(DrawContext context) {
        context.setFontSize(96F);

        String text = "PAUSED!";
        int x = context.calculateXForCenteredText(text);
        int y = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2 + GameInstance.getInstance().getGameFrame().TILE_SIZE * 2;

        context.drawString(text, x, y, Color.WHITE);
    }

    public void drawDialog() {

        //window
//        int x = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        ;
//        int y = Main.gamePanel.screenHeight - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 5);
//        int width = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 4;
//        int height = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 4;
//
//        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
//        g2.drawImage(npc.characterImage, x, y, width, height, null);
//        RenderUtils.drawSubWindow(x, y, width, height, 35, 0, Color.BLUE, g2);
//
//        x = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 6;
//        width = Main.gamePanel.screenWidth - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 7);
//
//        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
//
//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
//        x += GameInstance.getInstance().getGameFrame().TILE_SIZE
//        ;
//        y += GameInstance.getInstance().getGameFrame().TILE_SIZE
//        ;
//
//        if (npc.dialogues[npc.dialogueSet][npc.dialoguePage][0] != null) {
//
//            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialoguePage][0].toCharArray();
//
//            if (charIndex < characters.length) {
//                String s = String.valueOf(characters[charIndex]);
//                combinedText = combinedText + s;
//                currentDialog = combinedText;
//                charIndex++;
//                SoundSystem.playSound(Sounds.EFFECT_TEXT);
//            }
//
//            if (Main.gamePanel.keyHandler.enterPressed) {
//
//                charIndex = 0;
//                combinedText = "";
//
//                if (GameInstance.getInstance().getGameState() == GameState.DIALOG) {
//
//                    npc.dialoguePage = responseCMD[commandNum];
//                    Main.gamePanel.keyHandler.enterPressed = false;
//
//                    responses = 0;
//                    Arrays.fill(response, "");
//                    Arrays.fill(responseCMD, 0);
//
//                    commandNum = 0;
//
//                }
//
//            }
//
//        } else {
//
//            if (GameInstance.getInstance().getGameState() == GameState.DIALOG) {
//                GameInstance.getInstance().getGameState() = GameState.PLAY;
//            }
//
//            npc.dialoguePage = 0;
//
//        }
//
//        for (String line : currentDialog.split("\n")) {
//            g2.drawString(line, x, y);
//            y += 25;
//        }
//
//        responses = 0;
//        for (int i = 0; i < 3; i++) {
//            if (npc.dialogues[npc.dialogueSet][npc.dialoguePage][i + 1] != null) {
//                responseCMD[i] = Integer.parseInt(npc.dialogues[npc.dialogueSet][npc.dialoguePage][i + 1].substring(0, 3));
//                response[i] = npc.dialogues[npc.dialogueSet][npc.dialoguePage][i + 1].substring(3);
//                responses++;
//            } else {
//                responseCMD[i] = 99;
//                response[i] = "";
//            }
//        }
//
//        y += 15;
//
//        for (int i = 0; i < responses; i++) {
//
//            if (commandNum == i) {
//                g2.setColor(Color.ORANGE);
//                g2.drawString(response[i], x, y);
//            } else {
//                g2.setColor(Color.WHITE);
//                g2.drawString(response[i], x, y);
//            }
//
//            y += 25;
//
//        }

    }

    public void drawSimpleDialog(int y, int height, String text, float textSize, boolean silent) {

//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, textSize));
//
//        //window
//        int width = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 16;
//        int x = Main.gamePanel.screenWidth / 2 - (width / 2);
//
//        currentSimpleDialog = addLineBreaks(text, width - 40, g2);
//
//        int lineHeight = (int) (g2.getFontMetrics().getStringBounds(text, g2).getHeight() / 1.3);
//
//        if (height == -1) {
//
//            int lines = currentSimpleDialog.split("\n").length;
//
//            height = lineHeight * lines + 37;
//
//            y = (Main.gamePanel.screenHeight - GameInstance.getInstance().getGameFrame().TILE_SIZE
//            ) - (height);
//
//        }
//
//        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
//
//        x += 25;
//        y += 37;
//
//        char[] characters = currentSimpleDialog.toCharArray();
//
//        if (charIndex < characters.length) {
//            String s = String.valueOf(characters[charIndex]);
//            combinedText = combinedText + s;
//            currentSimpleDialog = combinedText;
//            charIndex++;
//            if (!silent) SoundSystem.playSound(Sounds.EFFECT_TEXT);
//        }
//
//        for (String line : currentSimpleDialog.split("\n")) {
//            g2.drawString(line, x, y);
//            y += lineHeight;
//        }
//
//        if (Main.gamePanel.keyHandler.enterPressed) {
//
//            charIndex = 0;
//            currentSimpleDialog = "";
//            combinedText = "";
//
//        }

    }

    public void drawInfo() {

//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
//
//        //window
//        int width = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 16;
//        int x = Main.gamePanel.screenWidth / 2 - (width / 2);
//
//        String message = addLineBreaks(currentSimpleDialog, width - 40, g2);
//
//        int lineHeight = (int) (g2.getFontMetrics().getStringBounds(currentSimpleDialog, g2).getHeight() / 1.3);
//
//        int lines = message.split("\n").length;
//
//        int height = lineHeight * lines + 37;
//
//        int y = (Main.gamePanel.screenHeight - GameInstance.getInstance().getGameFrame().TILE_SIZE
//        ) - (height);
//
//        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
//
//        x += 25;
//        y += 37;
//
//        char[] characters = message.toCharArray();
//
//        if (charIndex < characters.length) {
//            String s = String.valueOf(characters[charIndex]);
//            combinedText = combinedText + s;
//            message = combinedText;
//            charIndex++;
//            SoundSystem.playSound(Sounds.EFFECT_TEXT);
//        }
//
//        for (String line : message.split("\n")) {
//            g2.drawString(line, x, y);
//            y += lineHeight;
//        }
//
//        if (Main.gamePanel.keyHandler.enterPressed) {
//
//            charIndex = 0;
//            combinedText = "";
//
//            GameInstance.getInstance().getGameState() = GameState.PLAY;
//
//            Main.gamePanel.player.attackCanceled = true;
//
//        }

    }

    public void drawInventoryScreen() {

//        int frameX = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 8 - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 4);
//        int frameY = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 3 - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 8);
//        int frameWidth = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 6 - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 6);
//        int frameHeight = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 5 - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 6);
//
//        RenderUtils.drawSubWindow(frameX, frameY, frameWidth, frameHeight, 35, 0, Color.BLUE, g2);
//
//        //slot
//        final int slotXStart = frameX + 20;
//        final int slotYStart = frameY + 20;
//        int slotX = slotXStart;
//        int slotY = slotYStart;
//        int slotSize = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        ;
//
//        //draw item
//        for (int i = 0; i < Main.gamePanel.player.inventory.size(); i++) {
//
//            if (Main.gamePanel.player.inventory.get(i) == Main.gamePanel.player.currentWeapon || Main.gamePanel.player.inventory.get(i) == Main.gamePanel.player.currentOffhandItem) {
//
//                g2.setColor(new Color(218, 137, 27, 130));
//                g2.fillRoundRect(slotX, slotY, GameInstance.getInstance().getGameFrame().TILE_SIZE
//                , GameInstance.getInstance().getGameFrame().TILE_SIZE
//                , 10, 10);
//
//            }
//
//            g2.drawImage(Main.gamePanel.player.inventory.get(i).down1, slotX, slotY, slotSize, slotSize, null);
//            slotX += slotSize;
//
//            if (i == 4 || i == 9 || i == 14) {
//                slotX = slotXStart;
//                slotY += slotSize;
//            }
//        }
//
//        //cursor
//        int cursorX = slotXStart + (slotSize * slotCol);
//        int cursorY = slotYStart + (slotSize * slotRow);
//
//        g2.setColor(Color.WHITE);
//        g2.setStroke(new BasicStroke(3));
//        g2.drawRoundRect(cursorX, cursorY, slotSize, slotSize, 10, 10);
//
//        //description
//        frameX += (int) (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 6.2);
//
//        RenderUtils.drawSubWindow(frameX, frameY, frameWidth, frameHeight, 35, 0, Color.BLUE, g2);
//
//        //draw text
//        int textX = frameX + 20;
//        int textY = frameY + GameInstance.getInstance().getGameFrame().TILE_SIZE
//        ;
//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
//
//        int itemIndex = getItemIndex(slotCol, slotRow);
//
//        if (!(itemIndex >= Main.gamePanel.player.inventory.size())) {
//
//            String originalDescription = Main.gamePanel.player.inventory.get(itemIndex).description;
//            String description;
//
//            description = addLineBreaks(originalDescription, frameWidth - 10, g2);
//
//            for (String line : description.split("\n")) {
//                g2.drawString(line, textX, textY);
//                textY += 32;
//            }
//
//        }

    }

    public void drawTarget() {

//        playerHasTarget = true;
//
//        int x = (int) (Main.gamePanel.screenWidth - (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 5.2));
//        int y = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 5;
//        int width = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 5;
//        int height = GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 2;
//
//        RenderUtils.drawSubWindow(x, y, width, height, 35, 200, Color.BLUE, g2);
//        g2.drawImage(currentTarget.characterImage, x + 15, y + 15, null);
//        RenderUtils.drawSubWindow(x + 10, y + 10, height - 20, height - 20, 25, 0, Color.BLUE, g2);
//
//        double oneScale = (double) GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 2.5 / currentTarget.maxHealth;
//        double hpBarValue = oneScale * currentTarget.health;
//
//        g2.setColor(Color.BLACK);
//        g2.fillRect(x + GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 2 + 1, y + 16, (int) (GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 2.5 + 2), GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 2 + 2);
//        g2.setColor(Color.RED);
//        g2.fillRect(x + GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 2, y + 15, (int) hpBarValue, GameInstance.getInstance().getGameFrame().TILE_SIZE
//        / 2);
//
//        int xDistance = Math.abs(Main.gamePanel.player.worldX - currentTarget.worldX);
//        int yDistance = Math.abs(Main.gamePanel.player.worldY - currentTarget.worldY);
//        int distance = Math.max(xDistance, yDistance);
//        if (distance > GameInstance.getInstance().getGameFrame().TILE_SIZE
//        * 10) playerHasTarget = false;

    }

    public int getXForCenteredImage(BufferedImage image) {
        return GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2 - (image.getWidth() / 2);
    }

    public int getXForCenteredSomething(int centeredSomethingWidth) {
        return GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2 - (centeredSomethingWidth / 2);
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

        try {
            image = ImageIO.read(Requirements.notNull(inputStream, "Cannot load image because " + path + " is null!"));
        } catch (IOException e) {
            Logger.error("Error reading texture " + path + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        return image;
    }

}