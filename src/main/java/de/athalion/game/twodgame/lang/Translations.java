package de.athalion.game.twodgame.lang;

public class Translations {

    static Translation translation;

    public static void changeLanguage(Language language) {
        translation = new Translation(language);
    }

    public static Language getCurrentLanguage() {
        return translation.getLanguage();
    }

    public static String get(String id) {
        return translation.getString(id);
    }

    public static String get(String id, Replacement... replacements) {
        return Replacement.replaceAll(translation.getString(id), replacements);
    }

}
