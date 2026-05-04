package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.entity.stats.StatSet;
import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.input.controller.ControllerSystem;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.location.Direction;
import de.athalion.game.twodgame.location.LocatableObject;
import de.athalion.game.twodgame.location.Location;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.utility.CollisionChecker;
import de.athalion.game.twodgame.world.WorldInstance;

import java.awt.*;

public abstract class Entity extends LocatableObject {

    public EntityType type;

    public Texture up, down, left, right;
    public Texture attackUp, attackDown, attackLeft, attackRight;
    public Texture characterImage;
    public Direction direction = Direction.DOWN;

    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);

    public int actionLockTimer;
    public int dyingTimer;

    public boolean invincible;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;

    public String name;
    public String id;
    public boolean collision = false;

    public StatSet stats = new StatSet();

    public int health;
    public int level;
    public int exp;

    public int nextLevelExp;
    public int gold;
    public Item currentWeapon;

    public void setAction() {
    }

    public void damageReaction() {
    }

    public void onInteract() {
    }

    public void speak() {
    }


    public void teleport(Location location) {
        teleport(location, direction);
    }

    public void teleport(Location location, Direction direction) {
        this.location.getWorldInstance().getEntities().remove(this);
        location.getWorldInstance().getEntities().add(this);
        this.location = location;
        this.direction = direction;
    }

    public void rotate(Direction direction) {
        this.direction = direction;
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
        setAction();

        boolean colliding = CollisionChecker.checkTile(this);
        // TODO fix 🙏
//        Main.gamePanel.collisionChecker.checkObject(this, false);
        Entity collidingEntity = CollisionChecker.checkEntities(this, GameInstance.getInstance().getCamera().getWorldInstance().getEntities());
        colliding = colliding || collidingEntity != null;

        if (this.type == EntityType.MONSTER && collidingEntity != null &&  collidingEntity.type == EntityType.PLAYER && this.dyingTimer == 0) {
            if (!GameInstance.getInstance().getPlayer().invincible) {
//                SoundSystem.playSound(Sounds.EFFECT_RECEIVE_DAMAGE); // TODO
                ControllerSystem.doVibration(0.5f, 0.5f, 700);

                GameInstance.getInstance().getPlayer().damage(DamageType.NORMAL, stats.get(Stats.STRENGTH));
            }
        }

        if (!colliding) location.move(direction, stats.get(Stats.SPEED));
    }

    @Override
    public void draw(DrawContext context, int screenX, int screenY) {
        switch (direction) {
            case UP -> context.drawTexture(up, screenX, screenY);
            case DOWN -> context.drawTexture(down, screenX, screenY);
            case LEFT -> context.drawTexture(left, screenX, screenY);
            case RIGHT -> context.drawTexture(right, screenX, screenY);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Retrieves the entities' hitbox with its current position added.
     * This rectangle represents the area where the entity is currently located.
     * @return The hitbox at the entities' location
     */
    public Rectangle getHitbox() {
        return new Rectangle((int) (location.getX() + hitbox.getX()), (int) (location.getY() + hitbox.getY()), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }

    public double getStat(Stats stat) {
        return stats.get(stat);
    }

    public WorldInstance getWorld() {
        return getLocation().getWorldInstance();
    }

}
