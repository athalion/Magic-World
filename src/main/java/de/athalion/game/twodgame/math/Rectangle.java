package de.athalion.game.twodgame.math;

public class Rectangle {

    double x;
    double y;
    double width;
    double height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Vector location, Vector size) {
        this.x = location.x;
        this.y = location.y;
        this.width = size.x;
        this.height = size.y;
    }

    /**
     * Moves the rectangle by the given offset.
     *
     * @param vector The offset to move the rectangle.
     */
    public void move(Vector vector) {
        move(vector.x, vector.y);
    }

    /**
     * Moves the rectangle by the given x and y offsets.
     *
     * @param x The x offset to move the rectangle.
     * @param y The y offset to move the rectangle.
     */
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Checks if the given point is within the bounds of the rectangle.
     *
     * @param point The point to check.
     * @return True if the point is within the rectangle's bounds, false otherwise.
     */
    public boolean contains(Vector point) {
        return contains(point.x, point.y);
    }

    /**
     * Checks if the given point (x, y) is within the bounds of the rectangle.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return True if the point is within the rectangle's bounds, false otherwise.
     */
    public boolean contains(double x, double y) {
        return x >= this.x &&
                x <= this.x + this.width &&
                y >= this.y &&
                y <= this.y + this.height;
    }

    public boolean intersects(Rectangle other) {
        if (this.x <= 0 || other.x <= 0 || this.y <= 0 || other.y <= 0) return false;
        return ((other.width + other.x < other.x || other.width + other.x > this.x) &&
                (other.height + other.y < other.y || other.height + other.y > this.y) &&
                (this.width + this.x < this.x || this.width + this.x > other.x) &&
                (this.height + this.y < this.y || this.height + this.y > other.y));
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
