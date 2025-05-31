package de.athalion.game.twodgame.lang;

public class Language {

    String name;
    String id;
    String file;

    public Language(String name, String id) {
        this.name = name;
        this.id = id;
        this.file = "/lang/" + id + ".json";
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getFile() {
        return file;
    }

}
