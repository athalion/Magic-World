package de.athalion.game.twodgame.math;

import de.athalion.game.twodgame.location.Location;
import de.athalion.game.twodgame.world.WorldInstance;

public class Vector {

    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    @Override
    public Vector clone() {
        return new Vector(this);
    }

    public Vector add(Vector vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vector add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector subtract(Vector vector) {
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vector subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector multiply(Vector vector) {
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vector multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector divide(Vector vector) {
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vector divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double distanceTo(Vector vector) {
        double dx = x - vector.x;
        double dy = y - vector.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Location toLocation(WorldInstance worldInstance) {
        return new Location(worldInstance, this);
    }

}
