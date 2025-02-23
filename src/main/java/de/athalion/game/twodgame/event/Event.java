package de.athalion.game.twodgame.event;

import de.athalion.game.twodgame.main.Direction;

import java.awt.*;
import java.util.function.Predicate;

public class Event {

    String id;
    boolean canTrigger;
    Direction requiredDirection;
    Rectangle eventRectangle;
    boolean cooldown;
    int calls = 0;
    Predicate<Event> condition;
    Runnable callback;

    public Event(Rectangle eventRectangle, Direction requiredDirection) {
        this.eventRectangle = eventRectangle;
        this.requiredDirection = requiredDirection;
    }

    public Event setId(String id) {
        this.id = id;
        return this;
    }

    public Event setWorldX(int worldX) {
        eventRectangle.x = worldX;
        return this;
    }

    public Event setWorldY(int worldY) {
        eventRectangle.y = worldY;
        return this;
    }

    public Event setCanTrigger(boolean canTrigger) {
        this.canTrigger = canTrigger;
        return this;
    }

    public Event setCalls(int calls) {
        this.calls = calls;
        return this;
    }

    public Event callback(Runnable callback) {
        this.callback = callback;
        return this;
    }

    public Event cooldown() {
        cooldown = true;
        return this;
    }

    public Event runIf(Predicate<Event> condition) {
        this.condition = condition;
        return this;
    }

    public void trigger() {
        if (condition == null || condition.test(this)) {
            if (canTrigger) {
                if (callback != null) {
                    callback.run();
                    calls++;
                }
            }
            if (cooldown) canTrigger = false;
        }
    }

    public boolean canTrigger() {
        return canTrigger;
    }

    public String getId() {
        return id;
    }

    public int getWorldX() {
        return eventRectangle.x;
    }

    public int getWorldY() {
        return eventRectangle.y;
    }

    public Direction getRequiredDirection() {
        return requiredDirection;
    }

    public Rectangle getEventRectangle() {
        return eventRectangle;
    }

    public int getCalls() {
        return calls;
    }

}
