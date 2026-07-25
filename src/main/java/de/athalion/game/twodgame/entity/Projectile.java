package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.location.Direction;

public class Projectile extends Entity {

    Entity user;

    public Projectile() {
        type = EntityType.PROJECTILE;
    }

    public void set(Direction direction, boolean alive, Entity user) {
        this.lookingDirection = direction;
        this.alive = alive;
        this.user = user;
    }

    public void update() {

        health--;
        if (health <= 0) {
            alive = false;
        }

    }

}
