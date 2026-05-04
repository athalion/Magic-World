package de.athalion.game.twodgame.event;

import de.athalion.game.twodgame.location.Direction;
import de.athalion.game.twodgame.main.GameInstance;

import java.awt.*;
import java.util.List;

public class EventHandler {

    List<Event> eventList;

    public EventHandler(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void checkEvent() {
        eventList.forEach(event -> {
            int xDistance = Math.abs(GameInstance.getInstance().getPlayer().getLocation().getX() - event.getWorldX());
            int yDistance = Math.abs(GameInstance.getInstance().getPlayer().getLocation().getY() - event.getWorldY());
            int distance = Math.max(xDistance, yDistance);
            if (distance > GameInstance.getInstance().getGameFrame().TILE_SIZE) event.canTrigger = true;

            if (hit(event)) event.trigger();
        });
    }

    private boolean hit(Event event) {
        Rectangle hitbox = GameInstance.getInstance().getPlayer().getHitbox();

        if (hitbox.intersects(event.getEventRectangle())) {
            return GameInstance.getInstance().getPlayer().getDirection().equals(event.getRequiredDirection()) || event.getRequiredDirection().equals(Direction.ANY);
        }
        return false;
    }

    public List<Event> getEvents() {
        return eventList;
    }

}
