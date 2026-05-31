package de.athalion.game.twodgame.location;

import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.world.WorldInstance;

public class Location {

    WorldInstance worldInstance;
    Vector location;

    public Location(WorldInstance worldInstance, double x, double y) {
        this.worldInstance = worldInstance;
        this.location = new Vector(x, y);
    }

    public Location(WorldInstance worldInstance, Vector vector) {
        this.worldInstance = worldInstance;
        this.location = vector;
    }

    public void move(Vector vector) {
        this.location.add(vector);
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public Vector toVector() {
        return location.clone();
    }

}
