package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.item.container.Inventory;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.TextureStack;
import de.athalion.game.twodgame.resources.texture.TextureStackPointer;
import de.athalion.game.twodgame.resources.texture.Textures;

public class Player extends Entity {

    public static final TextureStack PLAYER_TEXTURE_STACK = new TextureStack(
            Textures.setup(Identifier.forPath("/textures/entity/player/character.png"), false),
            new TextureStack.TextureSet(
                    Textures.setup(Identifier.forPath("/textures/entity/player/idle/up.png"), false),
                    Textures.setup(Identifier.forPath("/textures/entity/player/idle/down.png"), false),
                    Textures.setup(Identifier.forPath("/textures/entity/player/idle/left.png"), false),
                    Textures.setup(Identifier.forPath("/textures/entity/player/idle/right.png"), false)
            )
    );

    public boolean attackCanceled = false;

    Inventory inventory = new Inventory(20);

    public Player() {
        type = EntityType.PLAYER;

        textureStackPointer = new TextureStackPointer(PLAYER_TEXTURE_STACK);

        hitboxSize = new Vector(32, 32);
    }

    @Override
    public void update() {
        Vector inputVector = InputSystem.getVector(InputAction.MOVE_UP, InputAction.MOVE_DOWN, InputAction.MOVE_LEFT, InputAction.MOVE_RIGHT);

        if (inputVector.length() > 0) {
            velocity = Vector.lerp(velocity, inputVector.multiply(stats.get(Stats.SPEED)), 0.7);
        } else velocity = Vector.lerp(velocity, inputVector, 0.85);

        move();

        getWorld().getEventHandler().checkEvent();
    }

//    private void attack() {
//
//        spriteCounter++;
//        if (spriteCounter <= 5) spriteNumber = 1;
//        if (spriteCounter > (5 * currentWeapon.attackSpeedMultiplier) && spriteCounter < (25 * currentWeapon.attackSpeedMultiplier)) {
//            spriteNumber = 2;
//
//            int currentWorldX = worldX;
//            int currentWorldY = worldY;
//            int solidAreaWidth = hitbox.width;
//            int solidAreaHeight = hitbox.height;
//
//            switch (direction) {
//                case UP:
//                    worldY -= attackArea.height;
//                    break;
//                case DOWN:
//                    worldY += attackArea.height;
//                    break;
//                case LEFT:
//                    worldX -= attackArea.width;
//                    break;
//                case RIGHT:
//                    worldX += attackArea.width;
//                    break;
//            }
//
//            hitbox.width = attackArea.width;
//            hitbox.height = attackArea.height;
//
//            Entity monster = Main.gamePanel.collisionChecker.checkEntities(this, getWorld().getMonsters().toArray(Entity[]::new));
//            damageMonster(monster, false);
//
//            worldX = currentWorldX;
//            worldY = currentWorldY;
//            hitbox.width = solidAreaWidth;
//            hitbox.height = solidAreaHeight;
//
//            if (spriteCounter == (15 * currentWeapon.attackSpeedMultiplier)) {
//                if (currentWeapon.weaponType == WeaponType.BOW) {
//                    if (!getWorld().getProjectiles().contains(currentWeapon.projectile)) {
//                        currentWeapon.projectile.set(worldX, worldY, direction, true, this);
//
//                        getWorld().getProjectiles().add(currentWeapon.projectile);
//                    }
//                }
//            }
//
//        }
//
//        if (spriteCounter > (25 * currentWeapon.attackSpeedMultiplier)) {
//            spriteNumber = 1;
//            spriteCounter = 0;
//            attacking = false;
//        }
//
//    }

}
