package de.athalion.game.twodgame.entity;

import com.google.gson.*;

import java.util.List;

public class EntitySerializer {

    public static JsonElement serialize(List<Entity> entities) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonObject = new JsonObject();

        for (Entity entity : entities) {
            JsonObject entityObject = new JsonObject();

            entityObject.add("worldX", new JsonPrimitive(entity.worldX));
            entityObject.add("worldY", new JsonPrimitive(entity.worldY));
            entityObject.add("speed", new JsonPrimitive(entity.speed));
            entityObject.add("direction", new JsonPrimitive(entity.direction.name()));
            entityObject.add("alive", new JsonPrimitive(entity.alive));
            entityObject.add("solidArea", gson.toJsonTree(entity.solidArea));
            entityObject.add("attackArea", gson.toJsonTree(entity.attackArea));
            entityObject.add("collisionOn", new JsonPrimitive(entity.collisionOn));
            entityObject.add("actionLockTimer", new JsonPrimitive(entity.actionLockTimer));
            entityObject.add("dyingTimer", new JsonPrimitive(entity.dyingTimer));
            entityObject.add("health", new JsonPrimitive(entity.life));
            entityObject.add("level", new JsonPrimitive(entity.level));
            entityObject.add("exp", new JsonPrimitive(entity.exp));
            entityObject.add("strength", new JsonPrimitive(entity.strength));
            entityObject.add("dexterity", new JsonPrimitive(entity.dexterity));
            entityObject.add("attack", new JsonPrimitive(entity.attack));
            entityObject.add("defence", new JsonPrimitive(entity.defence));
            entityObject.add("invincible", new JsonPrimitive(entity.invincible));
            entityObject.add("attacking", new JsonPrimitive(entity.attacking));
            entityObject.add("dying", new JsonPrimitive(entity.dying));
            entityObject.add("hpBarOn", new JsonPrimitive(entity.hpBarOn));
            entityObject.add("hpBarTimer", new JsonPrimitive(entity.hpBarTimer));
            entityObject.add("solidAreaDefaultX", new JsonPrimitive(entity.solidAreaDefaultX));
            entityObject.add("solidAreaDefaultY", new JsonPrimitive(entity.solidAreaDefaultY));

            jsonObject.add(entity.id, entityObject);
        }

        return jsonObject;

    }

}
