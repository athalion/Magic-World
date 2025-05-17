package de.athalion.game.twodgame.schedule;

public class Task extends TimedTask {

    int ticks;
    Runnable runnable;

    public Task(Runnable runnable, int delay) {
        ticks = delay;
        this.runnable = runnable;
    }

    @Override
    public boolean tick() {
        ticks--;
        if (ticks <= 0) {
            runnable.run();
            return true;
        }
        return false;
    }

}
