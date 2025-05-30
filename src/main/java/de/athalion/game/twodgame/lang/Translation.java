package de.athalion.game.twodgame.lang;

import com.google.gson.JsonParser;
import de.athalion.game.twodgame.utility.Requirements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

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
