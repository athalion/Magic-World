package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.entity.stats.StatSet;
import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.location.Direction;
import de.athalion.game.twodgame.location.LocatableObject;
import de.athalion.game.twodgame.location.Location;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.math.Rectangle;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.resources.texture.TextureStackPointer;
import de.athalion.game.twodgame.world.WorldInstance;

public abstract class Entity extends LocatableObject {

    public EntityType type;

    Vector velocity;
    public Direction lookingDirection = Direction.DOWN;

    public Vector hitboxSize = new Vector(GameInstance.getInstance().getGameFrame().TILE_SIZE, GameInstance.getInstance().getGameFrame().TILE_SIZE);

    public TextureStackPointer textureStackPointer;

    public int dyingTimer;

    public boolean invincible;
    public boolean alive = true;
    public boolean dying = false;

    public StatSet stats = new StatSet();

    public int health;
    public int level;
    public int exp;

    public void teleport(Location location) {
        teleport(location, lookingDirection);
    }

    public void teleport(Location location, Direction direction) {
        this.location.getWorldInstance().getEntities().remove(this);
        location.getWorldInstance().getEntities().add(this);
        this.location = location;
        this.lookingDirection = direction;
    }

    public void rotate(Direction direction) {
        this.lookingDirection = direction;
    }

    public void damage(DamageType damageType, int amount) {
        if (!invincible) {
            switch (damageType) {
                case NORMAL -> health -= amount - stats.get(Stats.DEFENCE);
                case MAGIC -> health -= amount - stats.get(Stats.MAGIC_RESISTANCE);
                case ABSOLUTE -> health -= amount;
            }
            invincible = true;
        }
    }

    public void update() {

    }

    @Override
    public void draw(DrawContext context, int screenX, int screenY) {
        context.drawImage(textureStackPointer.getImage(lookingDirection), screenX, screenY);
    }

    void move() {
        this.location.move(velocity);
    }

    public TextureStackPointer getTextureStackPointer() {
        return textureStackPointer;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Direction getLookingDirection() {
        return lookingDirection;
    }

    /**
     * Retrieves the entities' hitbox with its current position added.
     * This rectangle represents the area where the entity is currently located.
     * @return The hitbox at the entities' location
     */
    public Rectangle getHitbox() {
        return new Rectangle(location.toVector(), hitboxSize);
    }

    public double getStat(Stats stat) {
        return stats.get(stat);
    }

    public WorldInstance getWorld() {
        return getLocation().getWorldInstance();
    }

}
