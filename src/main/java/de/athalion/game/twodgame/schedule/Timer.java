package de.athalion.game.twodgame.schedule;

import de.athalion.game.twodgame.utility.Requirements;

import java.util.function.Consumer;

public class Timer extends TimedTask {

    double start;
    double end;
    int duration;
    Consumer<Double> callback;

    int time;

    public Timer(double start, double end, int duration, Consumer<Double> callback) {
        Requirements.requires(end > start, "End (" + end + ") musst be grater than start (" + start + ")");
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.callback = callback;
    }

    public boolean tick() {
        time++;
        callback.accept(start + ((end - start) / duration * time));
        return time == duration;
    }

}
