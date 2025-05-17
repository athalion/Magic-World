package de.athalion.game.twodgame.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Scheduler {

    List<TimedTask> tasks = new ArrayList<>();

    public void runTaskLater(Runnable runnable, int delay) {
        tasks.add(new Task(runnable, delay));
    }

    public void runTimer(double start, double end, int duration, Consumer<Double> callback) {
        tasks.add(new Timer(start, end, duration, callback));
    }

    public void tick() {
        tasks.removeIf(TimedTask::tick);
    }

}
