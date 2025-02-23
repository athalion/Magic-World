package de.athalion.game.twodgame.event;

import com.google.gson.JsonElement;
import de.athalion.game.twodgame.world.World;

public class EventDeserializer {

    public static void deserialize(JsonElement json, World world) {
        json.getAsJsonObject().asMap().forEach((s, jsonElement) -> world.getEvent(s).setCanTrigger(jsonElement.getAsJsonObject().get("canTrigger").getAsBoolean()).setCalls(jsonElement.getAsJsonObject().get("calls").getAsInt()));
    }

}
