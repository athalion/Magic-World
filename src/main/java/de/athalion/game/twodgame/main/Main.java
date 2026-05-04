package de.athalion.game.twodgame.main;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.resources.Identifier;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public static String VERSION;

    public static void main(String[] args) {
        Logger.createSession();

        try {
            Properties properties = new Properties();
            properties.load(Identifier.forPath("/project.properties").stream());
            VERSION = properties.getProperty("version");
        } catch (IOException e) {
            Logger.error("Error loading project properties!");
            Logger.stackTrace(e.getStackTrace());
        }

        GameInstance gameInstance = new GameInstance();
        gameInstance.init();

//        Logger.log("Creating blank cursor...");
//        Texture cursorTexture = Textures.setup(Identifier.forPath(CURSOR_PATH), false);
//        Cursor blankCursor = gameFrame.getToolkit().createCustomCursor(cursorTexture.getFrame(), new Point(), "blank");
//        gameFrame.setCursor(blankCursor);

        gameInstance.startGameThread();
    }

}
