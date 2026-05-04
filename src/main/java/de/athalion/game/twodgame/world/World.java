package de.athalion.game.twodgame.world;

import de.athalion.game.twodgame.event.Event;
import de.athalion.game.twodgame.graphics.effects.PostProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class World {

    String nameKey;
    List<Event> events = new ArrayList<>();
    List<PostProcessor> postProcessors = new ArrayList<>();

    public World(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<PostProcessor> getPostProcessors() {
        return postProcessors;
    }

}
