package de.athalion.game.twodgame.camera.configuration;

import com.google.gson.JsonObject;
import de.athalion.game.twodgame.math.Vector;

public class CameraConfiguration {

    double zoom;
    boolean fixed;
    Vector position;

    public CameraConfiguration(double zoom, boolean fixed, Vector position) {
        this.zoom = zoom;
        this.fixed = fixed;
        this.position = position;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getZoom() {
        return zoom;
    }

    public boolean isFixed() {
        return fixed;
    }

    public Vector getPosition() {
        return position;
    }

    public static CameraConfiguration fromJson(JsonObject jsonObject) {
        double zoom = jsonObject.get("zoom").getAsDouble();
        boolean fixed = jsonObject.get("fixed").getAsBoolean();
        Vector position  = new Vector(0, 0);
        if (fixed) {
            JsonObject positionObject = jsonObject.getAsJsonObject("position");
            position.setX(positionObject.get("x").getAsDouble());
            position.setY(positionObject.get("y").getAsDouble());
        }
        return new CameraConfiguration(zoom, fixed, position);
    }

}
