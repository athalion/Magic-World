package de.athalion.game.twodgame.save;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.utility.Requirements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaveStateManager {

    public static List<String> getSaves() {
        List<String> saves = new ArrayList<>();
        File saveDirectory = new File(System.getProperty("user.dir"), "saves");
        if (!saveDirectory.exists()) saveDirectory.mkdirs();

        if (saveDirectory.isDirectory()) {
            for (File file : Requirements.notNull(saveDirectory.listFiles(File::isDirectory), "")) {
                saves.add(file.getName());
            }
        } else
            Logger.warn("This is awkward. The save directory is... well... not a directory. How can this even happen?");

        return saves;
    }

    public static void createSaveState(String name) {
        File saveFile = new File(System.getProperty("user.dir") + "/saves", name);
        saveFile.mkdirs();
    }

}
