package de.athalion.game.twodgame.main;

public class Task {

    int ticks;
    Runnable runnable;

    public Task(Runnable runnable, int delay) {
        ticks = delay;
        this.runnable = runnable;
    }

    public boolean tick() {
        ticks--;
        if (ticks <= 0) {
            runnable.run();
            return true;
        }
        return false;
    }

}
