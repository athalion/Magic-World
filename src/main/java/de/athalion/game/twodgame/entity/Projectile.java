package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.location.Direction;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.utility.CollisionChecker;

public class Projectile extends Entity {

    Entity user;

    public Projectile() {
        type = EntityType.PROJECTILE;
    }

    public void set(Direction direction, boolean alive, Entity user) {
        this.direction = direction;
        this.alive = alive;
        this.user = user;
    }

    public void update() {

        if (user == GameInstance.getInstance().getPlayer()) {
            Entity monster = CollisionChecker.checkEntities(this, getWorld().getEntities());
            if (monster != null) {
                monster.damage(DamageType.NORMAL, 1);
                alive = false;
            }
        } else {

        }

        location.move(direction, stats.get(Stats.SPEED));

        health--;
        if (health <= 0) {
            alive = false;
        }

    }

}
