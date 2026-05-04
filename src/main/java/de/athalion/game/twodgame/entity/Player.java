package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.entity.stats.Stats;
import de.athalion.game.twodgame.input.InputAction;
import de.athalion.game.twodgame.input.InputSystem;
import de.athalion.game.twodgame.input.KeyHandler;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.location.Direction;
import de.athalion.game.twodgame.resources.Identifier;
import de.athalion.game.twodgame.resources.texture.Textures;
import de.athalion.game.twodgame.utility.CollisionChecker;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyHandler;

    public boolean attackCanceled = false;

    public ArrayList<Item> inventory = new ArrayList<>();
    public final int inventorySize = 20;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;

        hitbox = new Rectangle(8, 16, 32, 32);

        setupTextures();
    }

    private void setupTextures() {
        up = Textures.setup(Identifier.forPath("/player/boy_up_1.png"), false);
        down = Textures.setup(Identifier.forPath("/player/boy_down_1.png"), false);
        left = Textures.setup(Identifier.forPath("/player/boy_left_1.png"), false);
        right = Textures.setup(Identifier.forPath("/player/boy_right_1.png"), false);
    }

    @Override
    public void update() {

        if (attacking) {
//            attack();
        } else if (InputSystem.isActionPressed(InputAction.MOVE_UP) ||
                InputSystem.isActionJustPressed(InputAction.MOVE_DOWN) ||
                InputSystem.isActionPressed(InputAction.MOVE_LEFT) ||
                InputSystem.isActionPressed(InputAction.MOVE_RIGHT) ||
                InputSystem.isActionPressed(InputAction.ATTACK)) {

            if (InputSystem.isActionPressed(InputAction.MOVE_UP)) {
                direction = Direction.UP;
            } else if (InputSystem.isActionPressed(InputAction.MOVE_DOWN)) {
                direction = Direction.DOWN;
            } else if (InputSystem.isActionPressed(InputAction.MOVE_LEFT)) {
                direction = Direction.LEFT;
            } else if (InputSystem.isActionPressed(InputAction.MOVE_RIGHT)) {
                direction = Direction.RIGHT;
            }

            //check world collision
            boolean collision = CollisionChecker.checkTile(this);

            //check object collision
//            int objIndex = CollisionChecker.checkObject(this, true);
//            pickupObject(objIndex);

            //check monster collision
            Entity monster = CollisionChecker.checkEntities(this, getWorld().getEntities());
//            contactMonster(monster);

            //check event
            getWorld().getEventHandler().checkEvent();

            //move player if no collision
            if (!collision && !InputSystem.isActionPressed(InputAction.ATTACK)) location.move(direction, stats.get(Stats.SPEED));

            if (InputSystem.isActionPressed(InputAction.ATTACK) && !attackCanceled) {

//                if (currentWeapon.itemToConsume != null) {
//                    for (Item item : inventory) {
//                        if (item.getClass() == currentWeapon.itemToConsume.getClass()) {
//
//                            inventory.remove(item);
//
//                            SoundSystem.playSound(Sounds.EFFECT_SWING_WEAPON);
//                            ControllerSystem.doVibration(0.2f, 0.2f, 100);
//                            attacking = true;
//
//                            break;
//                        }
//                    }
//                } else {
//
//                    SoundSystem.playSound(Sounds.EFFECT_SWING_WEAPON);
//                    ControllerSystem.doVibration(0.2f, 0.2f, 100);
//                    attacking = true;
//
//                }

            }

            attackCanceled = false;
        }
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

    public void pickupObject(int index) {

        if (index != 999) {

            String text;

            if (inventory.size() != inventorySize) {

                inventory.add((Item) getWorld().getEntities().toArray(Entity[]::new)[index]);
//                SoundSystem.playSound(Sounds.EFFECT_COIN); // TODO

                text = "You picket up " + getWorld().getEntities().toArray(Entity[]::new)[index].name + "!";

            } else {
                text = "You cannot carry anymore!";
            }

            // TODO
//            Main.gamePanel.ui.addMessage(text);
            getWorld().getEntities().toArray(Entity[]::new)[index] = null;

        }

    }

}
