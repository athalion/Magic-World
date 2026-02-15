package de.athalion.game.twodgame.lang;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Provides access to predefined language instances.
 */
public class Languages {

    public static final Language EN_US = new Language("English (US)", "en_us");
    public static final Language DE = new Language("Deutsch", "de");

    /**
     * Returns an immutable list of the available language instances.
     *
     * @return an immutable {@link List} containing the predefined {@link Language} objects
     */
    public static List<Language> list() {
        return List.of(
                EN_US,
                DE
        );
    }

    /**
     * Returns the {@link Language} instance with the specified identifier, or {@code null} if no match is found.
     *
     * @param id the language identifier to search for
     * @return the matching {@link Language} instance, or {@code null} if no language with the given identifier exists
     */
    public static @Nullable Language get(String id) {
        for (Language language : list()) {
            if (language.getId().equals(id)) return language;
        }
        return null;
    }

}
