package de.athalion.game.twodgame.graphics.font;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.resources.Identifier;

import java.awt.*;
import java.io.IOException;

public class Fonts {

    public static Font DEFAULT;

    public static void load() {
        try {
            Logger.log("Loading Font...");
            DEFAULT = Font.createFont(Font.TRUETYPE_FONT, Identifier.forPath("/font/atlantis.ttf").stream());
        } catch (FontFormatException | IOException e) {
            Logger.error("Error creating custom font: " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }
    }

}
