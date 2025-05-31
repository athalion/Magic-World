package de.athalion.game.twodgame.utility;

import de.athalion.game.twodgame.logs.Logger;
import org.jetbrains.annotations.NotNull;

public class Requirements {

    public static @NotNull <T> T notNull(T obj, String message) {
        if (obj == null) {
            Logger.error(message);
            Logger.stackTrace(Thread.currentThread().getStackTrace());
            throw new NullPointerException();
        }
        return obj;
    }

    public static void requires(boolean fulfilled, String message) {
        if (!fulfilled) {
            Logger.error(message);
            Logger.stackTrace(Thread.currentThread().getStackTrace());
            throw new IllegalStateException(message);
        }
    }

}
