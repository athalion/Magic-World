package de.athalion.game.twodgame.camera;

import de.athalion.game.twodgame.camera.configuration.CameraConfiguration;
import de.athalion.game.twodgame.camera.configuration.CameraConfigurationHandler;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.world.WorldInstance;

public class Camera {

    WorldInstance worldInstance;
    CameraConfiguration cameraConfiguration;
    CameraConfigurationHandler cameraConfigurationHandler;

    public Camera(WorldInstance world, CameraConfigurationHandler cameraConfigurationHandler) {
        this.worldInstance = world;
        this.cameraConfigurationHandler = cameraConfigurationHandler;
    }

    public void update(Vector playerPosition) {
        CameraConfiguration targetConfiguration = cameraConfigurationHandler.getCameraConfiguration(playerPosition);
        cameraConfiguration.setFixed(targetConfiguration.isFixed());

        int targetX;
        int targetY;
        if (cameraConfiguration.isFixed()) {
            targetX = (int) targetConfiguration.getPosition().getX();
            targetY = (int) targetConfiguration.getPosition().getY();
        } else {
            targetX = (int) playerPosition.getX();
            targetY = (int) playerPosition.getY();
        }

        Vector position = cameraConfiguration.getPosition();
        position.setX(position.getX() + (targetX - position.getX()) / 16);
        position.setY(position.getY() + (targetY - position.getY()) / 16);

        cameraConfiguration.setZoom(cameraConfiguration.getZoom() + (targetConfiguration.getZoom() - cameraConfiguration.getZoom()) / 16);
    }

    /**
     * Sets this camera as the currently active camera. It will be used to render the world.
     * Only one camera can be active. Calling this method will mark the last camera as inactive.
     */
    public void setActive() {
        GameInstance.getInstance().setCamera(this);
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
    }

    public void setCameraConfiguration(CameraConfiguration cameraConfiguration) {
        this.cameraConfiguration = cameraConfiguration;
    }

    public void setCameraConfigurationHandler(CameraConfigurationHandler cameraConfigurationHandler) {
        this.cameraConfigurationHandler = cameraConfigurationHandler;
    }

    public boolean isActive() {
        return GameInstance.getInstance().getCamera().equals(this);
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public CameraConfiguration getCameraConfiguration() {
        return cameraConfiguration;
    }

}
