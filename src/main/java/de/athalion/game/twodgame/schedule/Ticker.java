package de.athalion.game.twodgame.schedule;

public class Ticker extends TimedTask {

    Runnable callback;

    public Ticker(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public boolean tick() {
        callback.run();
        return false;
    }

}
