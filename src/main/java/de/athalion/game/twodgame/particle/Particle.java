package de.athalion.game.twodgame.particle;

import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.location.LocatableObject;
import de.athalion.game.twodgame.location.Location;
import de.athalion.game.twodgame.resources.texture.Texture;

public class Particle extends LocatableObject {

    Texture texture;

    public Particle(Texture texture, Location location) {
        this.texture = texture;
        this.location = location;
    }

    public boolean update() {
        texture.nextFrame();
        return texture.getFrameNumber() >= texture.getFrames();
    }

    @Override
    public void draw(DrawContext context, int screenX, int screenY) {
        context.drawTexture(texture, screenX, screenY);
    }

}
