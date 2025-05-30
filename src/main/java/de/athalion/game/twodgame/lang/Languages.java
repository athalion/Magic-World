package de.athalion.game.twodgame.lang;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Languages {

    public static final Language EN_US = new Language("English (US)", "en_us");
    public static final Language DE = new Language("Deutsch", "de");

    public static List<Language> list() {
        return List.of(
                EN_US,
                DE
        );
    }

    public static @Nullable Language get(String id) {
        for (Language language : list()) {
            if (language.getId().equals(id)) return language;
        }
        return null;
    }

}
