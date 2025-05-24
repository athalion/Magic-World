package de.athalion.game.twodgame.save;

public class SaveState {

    String name;
    Progress progress;

    public SaveState(String name, Progress progress) {
        this.name = name;
        this.progress = progress;
    }

    public SaveState(String name) {
        this.name = name;
    }

    public static SaveState loadGameState(String name) {
        Progress loadedProgress = Progress.loadProgress(name);
        return new SaveState(name, loadedProgress);
    }

    public String getName() {
        return name;
    }

    public Progress getProgress() {
        return progress;
    }

}
