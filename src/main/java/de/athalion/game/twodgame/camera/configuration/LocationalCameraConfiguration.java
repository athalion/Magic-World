package de.athalion.game.twodgame.camera.configuration;

import de.athalion.game.twodgame.math.Vector;

import java.awt.*;

public class LocationalCameraConfiguration {

    Rectangle area;
    CameraConfiguration cameraConfiguration;
    int priority;

    public LocationalCameraConfiguration(Rectangle area, CameraConfiguration cameraConfiguration, int priority) {
        this.area = area;
        this.cameraConfiguration = cameraConfiguration;
        this.priority = priority;
    }

    public boolean isInArea(Vector position) {
        return area.contains(position.getX(), position.getY());
    }

    public CameraConfiguration getCameraConfiguration() {
        return cameraConfiguration;
    }

    public int getPriority() {
        return priority;
    }

}
