package de.athalion.game.twodgame.lang;

import com.google.gson.JsonParser;
import de.athalion.game.twodgame.utility.Requirements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Provides translation support for a specific {@link Language}.
 * <p>
 * When instantiated, the constructor loads a JSON file specified by {@link Language#getFile()}.
 * Each key/value pair in that JSON becomes an entry in an internal map.
 * <p>
 * {@link #getString(String)} returns the string for a given identifier, or a
 * fallback message when the key is missing.
 * {@link #getLanguage()} returns the {@link Language} instance associated with this
 * translation set.
 */
public class Translation {

    Language language;
    HashMap<String, String> translations = new HashMap<>();

    public Translation(Language language) {
        this.language = language;

        JsonParser.parseReader(
                new BufferedReader(new InputStreamReader(
                        Requirements.notNull(getClass().getResourceAsStream(language.getFile()), "Could not find language file for " + language.getName() + "!")
                ))
        ).getAsJsonObject().asMap().forEach((s, jsonElement) -> translations.put(s, jsonElement.getAsString()));
    }

    public String getString(String id) {
        return translations.getOrDefault(id, "Missing translation!");
    }

    public Language getLanguage() {
        return language;
    }

}
