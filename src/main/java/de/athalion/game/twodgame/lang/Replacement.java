package de.athalion.game.twodgame.lang;

/**
 * Represents a pair of strings used to perform a textual replacement.
 */
public class Replacement {

    String oldString;
    String newString;

    /**
     * Creates a replacement mapping from {@code oldString} to {@code newString}.
     *
     * @param oldString the substring to be replaced
     * @param newString the replacement text
     */
    public Replacement(String oldString, String newString) {
        this.oldString = oldString;
        this.newString = newString;
    }

    public String replace(String s) {
        return s.replace(oldString, newString);
    }

    public static String replaceAll(String s, Replacement... replacements) {
        for (Replacement replacement : replacements) {
            s = replacement.replace(s);
        }
        return s;
    }

}
