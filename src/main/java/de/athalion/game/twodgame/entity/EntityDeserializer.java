package de.athalion.game.twodgame.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.athalion.game.twodgame.entity.monster.MON_greenSlime;
import de.athalion.game.twodgame.entity.npc.NPC_OldMan;
import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.object.*;
import de.athalion.game.twodgame.object.projectile.OBJ_ArrowProjectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityDeserializer {

    public static List<Entity> deserialize(JsonElement json, GamePanel gamePanel) throws JsonParseException {

        List<Entity> entities = new ArrayList<>();

        json.getAsJsonObject().asMap().forEach((s, jsonElement) -> {
            Entity entity = switch (s) {
                case "MON_greenSlime" -> new MON_greenSlime(gamePanel);
                case "NPC_oldMan" -> new NPC_OldMan(gamePanel);
                case "PRO_arrow" -> new OBJ_ArrowProjectile(gamePanel);
                case "OBJ_arrow" -> new OBJ_Arrow(gamePanel);
                case "OBJ_axe" -> new OBJ_Axe(gamePanel);
                case "OBJ_blueShield" -> new OBJ_BlueShield(gamePanel);
                case "OBJ_boots" -> new OBJ_Boots(gamePanel);
                case "OBJ_bow" -> new OBJ_Bow(gamePanel);
                case "OBJ_chest" -> new OBJ_Chest(gamePanel);
                case "OBJ_door" -> new OBJ_Door(gamePanel);
                case "OBJ_heart" -> new OBJ_Heart(gamePanel);
                case "OBJ_key" -> new OBJ_Key(gamePanel);
                case "OBJ_redPotion" -> new OBJ_RedPotion(gamePanel);
                case "OBJ_woodenShield" -> new OBJ_ShieldWood(gamePanel);
                case "OBJ_swordNormal" -> new OBJ_SwordNormal(gamePanel);
                default -> throw new IllegalStateException("Unexpected value: " + s);
            };

            entity.worldX = jsonElement.getAsJsonObject().get("worldX").getAsInt();
            entity.worldY = jsonElement.getAsJsonObject().get("worldY").getAsInt();
            entity.speed = jsonElement.getAsJsonObject().get("speed").getAsInt();
            entity.direction = Direction.valueOf(jsonElement.getAsJsonObject().get("direction").getAsString());
            entity.alive = jsonElement.getAsJsonObject().get("alive").getAsBoolean();
            JsonObject solidArea = jsonElement.getAsJsonObject().get("solidArea").getAsJsonObject();
            entity.solidArea = new Rectangle(solidArea.get("x").getAsInt(), solidArea.get("y").getAsInt(), solidArea.get("width").getAsInt(), solidArea.get("height").getAsInt());
            JsonObject attackArea = jsonElement.getAsJsonObject().get("attackArea").getAsJsonObject();
            entity.attackArea = new Rectangle(attackArea.get("x").getAsInt(), attackArea.get("y").getAsInt(), attackArea.get("width").getAsInt(), attackArea.get("height").getAsInt());
            entity.collisionOn = jsonElement.getAsJsonObject().get("collisionOn").getAsBoolean();
            entity.actionLockTimer = jsonElement.getAsJsonObject().get("actionLockTimer").getAsInt();
            entity.dyingTimer = jsonElement.getAsJsonObject().get("dyingTimer").getAsInt();
            entity.life = jsonElement.getAsJsonObject().get("health").getAsInt();
            entity.level = jsonElement.getAsJsonObject().get("level").getAsInt();
            entity.exp = jsonElement.getAsJsonObject().get("exp").getAsInt();
            entity.strength = jsonElement.getAsJsonObject().get("strength").getAsInt();
            entity.dexterity = jsonElement.getAsJsonObject().get("dexterity").getAsInt();
            entity.attack = jsonElement.getAsJsonObject().get("attack").getAsInt();
            entity.defence = jsonElement.getAsJsonObject().get("defence").getAsInt();
            entity.invincible = jsonElement.getAsJsonObject().get("invincible").getAsBoolean();
            entity.attacking = jsonElement.getAsJsonObject().get("attacking").getAsBoolean();
            entity.dying = jsonElement.getAsJsonObject().get("dying").getAsBoolean();
            entity.hpBarOn = jsonElement.getAsJsonObject().get("hpBarOn").getAsBoolean();
            entity.hpBarTimer = jsonElement.getAsJsonObject().get("hpBarTimer").getAsInt();
            entity.solidAreaDefaultX = jsonElement.getAsJsonObject().get("solidAreaDefaultX").getAsInt();
            entity.solidAreaDefaultY = jsonElement.getAsJsonObject().get("solidAreaDefaultY").getAsInt();

            entities.add(entity);
        });

        return entities;

    }

}
