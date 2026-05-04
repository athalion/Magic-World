package de.athalion.game.twodgame.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Scheduler {

    private final static List<TimedTask> TASKS = new ArrayList<>();

    public static void runTaskLater(Runnable runnable, int delay) {
        TASKS.add(new Task(runnable, delay));
    }

    public static void runTimer(double start, double end, int duration, Consumer<Double> callback) {
        TASKS.add(new Timer(start, end, duration, callback));
    }

    public static void runTicker(Runnable runnable) {
        TASKS.add(new Ticker(runnable));
    }

    public static void tick() {
        TASKS.removeIf(TimedTask::tick);
    }

}
