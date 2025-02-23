package de.athalion.game.twodgame.schedule;

import de.athalion.game.twodgame.main.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    List<Task> tasks = new ArrayList<>();

    public void runTaskLater(Task task) {
        tasks.add(task);
    }

    public void tick() {
        tasks.removeIf(Task::tick);
    }

}
