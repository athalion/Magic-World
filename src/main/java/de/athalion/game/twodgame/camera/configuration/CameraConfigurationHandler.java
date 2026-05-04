package de.athalion.game.twodgame.camera.configuration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.resources.Identifier;

import java.awt.*;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class CameraConfigurationHandler {

    CameraConfiguration defaultCameraConfiguration;
    List<LocationalCameraConfiguration> cameraConfigurations;

    public CameraConfigurationHandler(CameraConfiguration defaultCameraConfiguration, List<LocationalCameraConfiguration> cameraConfigurations) {
        this.defaultCameraConfiguration = defaultCameraConfiguration;
        this.cameraConfigurations = cameraConfigurations;
    }

    public CameraConfiguration getCameraConfiguration(Vector position) {
        List<LocationalCameraConfiguration> matchingConfigurations = new ArrayList<>();
        cameraConfigurations.forEach((locationalCameraConfiguration) -> {
            if (locationalCameraConfiguration.isInArea(position)) matchingConfigurations.add(locationalCameraConfiguration);
        });

        if (matchingConfigurations.isEmpty()) return defaultCameraConfiguration;

        matchingConfigurations.sort(Comparator.comparingInt(LocationalCameraConfiguration::getPriority));
        return matchingConfigurations.getFirst().getCameraConfiguration();
    }

    public static CameraConfigurationHandler fromJson(Identifier identifier) {
        JsonObject jsonObject = JsonParser.parseReader(new JsonReader(new InputStreamReader(identifier.stream()))).getAsJsonObject();
        Map<String, CameraConfiguration> configurations = new HashMap<>();
        jsonObject.getAsJsonObject("configurations").asMap().forEach((key, cameraConfiguration) -> configurations.put(key, CameraConfiguration.fromJson(cameraConfiguration.getAsJsonObject())));

        CameraConfiguration defaultConfiguration = configurations.get(jsonObject.get("default").getAsString());
        List<LocationalCameraConfiguration> cameraConfigurations = new ArrayList<>();

        jsonObject.get("areas").getAsJsonArray().forEach(jsonElement -> {
            JsonObject arealConfigurationObject = jsonElement.getAsJsonObject();
            JsonObject areaObject = arealConfigurationObject.getAsJsonObject("area");

            Rectangle area = new Rectangle(areaObject.get("x").getAsInt(), areaObject.get("y").getAsInt(), areaObject.get("width").getAsInt(), areaObject.get("height").getAsInt());
            String configurationKey = arealConfigurationObject.get("configuration").getAsString();
            int priority = arealConfigurationObject.get("priority").getAsInt();

            cameraConfigurations.add(new LocationalCameraConfiguration(area, configurations.get(configurationKey), priority));
        });

        return new CameraConfigurationHandler(defaultConfiguration, cameraConfigurations);
    }

}
