package de.athalion.game.twodgame.event;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class EventSerializer {

    public static JsonElement serialize(List<Event> events) {
        JsonObject jsonObject = new JsonObject();
        events.forEach(e -> {
            JsonObject event = new JsonObject();
            event.add("canTrigger", new JsonPrimitive(e.canTrigger()));
            event.add("calls", new JsonPrimitive(e.getCalls()));
            jsonObject.add(e.getId(), event);
        });
        return jsonObject;
    }

}
