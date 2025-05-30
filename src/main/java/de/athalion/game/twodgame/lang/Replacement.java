package de.athalion.game.twodgame.lang;

public class Replacement {

    String oldString;
    String newString;

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
