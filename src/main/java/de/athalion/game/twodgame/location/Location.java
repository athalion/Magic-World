package de.athalion.game.twodgame.location;

import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.world.WorldInstance;

public class Location {

    WorldInstance worldInstance;
    public int x, y;

    public Location(WorldInstance worldInstance, int x, int y) {
        this.worldInstance = worldInstance;
        this.x = x;
        this.y = y;
    }

    public Location(WorldInstance worldInstance, Vector vector) {
        this.worldInstance = worldInstance;
        this.x = (int) vector.getX();
        this.y = (int) vector.getY();
    }

    public void move(Direction direction, int distance) {
        switch (direction) {
            case RIGHT -> x += distance;
            case LEFT -> x -= distance;
            case UP -> y += distance;
            case DOWN -> y -= distance;
        }
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector toVector() {
        return new Vector(x, y);
    }

}
