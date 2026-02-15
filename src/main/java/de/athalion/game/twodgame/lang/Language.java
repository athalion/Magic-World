package de.athalion.game.twodgame.lang;

/**
 * Represents a language used by the application. Each language has a human‑readable
 * name, a unique identifier and the path to the JSON file that holds its
 * translations.  Typical instances are defined in {@link Languages} and can be
 * looked up by identifier via {@link Languages#get(String)}.
 *
 * <p>The {@link #getFile()} method returns the relative resource path
 * (e.g. {@code "/lang/en_us.json"}) used when loading translations.</p>
 *
 * @see Languages
 * @see Translation
 */
public class Language {

    String name;
    String id;
    String file;

    public Language(String name, String id) {
        this.name = name;
        this.id = id;
        this.file = "/lang/" + id + ".json";
    }

    /**
     * Returns the human‑readable name of the language.
     *
     * @return the language name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique identifier for this language.
     *
     * @return the language identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the relative resource path of the language JSON file.
     *
     * @return the file path of the language resource
     */
    public String getFile() {
        return file;
    }

}
