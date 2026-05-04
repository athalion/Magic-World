package de.athalion.game.twodgame.location;

import de.athalion.game.twodgame.graphics.DrawContext;

public abstract class LocatableObject {
    public Location location;

    public void draw(DrawContext context, int screenX, int screenY) {}

    public Location getLocation() {
        return location;
    }
}
