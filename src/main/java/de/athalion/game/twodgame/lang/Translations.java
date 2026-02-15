package de.athalion.game.twodgame.lang;

public class Translations {

    static Translation translation;

    /**
     * Switches the active language by initializing a new {@link Translation} with the given {@link Language}.
     *
     * @param language the language to use for translations
     */
    public static void changeLanguage(Language language) {
        translation = new Translation(language);
    }

    /**
     * Retrieves the {@link Language} currently in use by the application.
     *
     * @return the active {@link Language} instance
     */
    public static Language getCurrentLanguage() {
        return translation.getLanguage();
    }

    /**
     * Retrieves the localized string corresponding to the given identifier.
     *
     * @param id the translation key
     * @return the localized string, or a default missing translation message if the key is not found
     */
    public static String get(String id) {
        return translation.getString(id);
    }

    /**
     * Retrieves the localized string identified by {@code id} and applies the supplied
     * {@link Replacement} objects in order.
     *
     * @param id the translation key
     * @param replacements optional replacements to apply to the retrieved string
     * @return the processed string after all replacements have been applied
     */
    public static String get(String id, Replacement... replacements) {
        return Replacement.replaceAll(translation.getString(id), replacements);
    }

}
