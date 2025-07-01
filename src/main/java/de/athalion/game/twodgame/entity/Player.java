package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.input.ControllerSystem;
import de.athalion.game.twodgame.input.KeyHandler;
import de.athalion.game.twodgame.item.EquipType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.item.WeaponType;
import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.object.*;
import de.athalion.game.twodgame.sound.Sounds;
import de.athalion.game.twodgame.sound.SoundSystem;
import de.athalion.game.twodgame.utility.UtilityTool;
import de.athalion.game.twodgame.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public int screenX;
    public int screenY;

    public boolean attackCanceled = false;

    public ArrayList<Item> inventory = new ArrayList<>();
    public final int inventorySize = 20;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        solidArea = new Rectangle(8, 16, 32, 32);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();

    }

    public void setDefaultValues() {

        worldX = gamePanel.tileSize * 2;
        worldY = gamePanel.tileSize * 2;

        speed = 2;
        direction = Direction.DOWN;

        //player status
        maxLife = 6;
        life = maxLife;

        level = 0;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 10;
        gold = 0;
        currentWeapon = new OBJ_SwordNormal(gamePanel);
        currentOffhandItem = new OBJ_ShieldWood(gamePanel);
        attack = getAttack();
        defence = getDefence();

        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(currentOffhandItem);
        inventory.add(currentWeapon);
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Bow(gamePanel));
        inventory.add(new OBJ_Axe(gamePanel));
        inventory.add(new OBJ_RedPotion(gamePanel));

    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        attack = strength * currentWeapon.attackValue;
        return attack;
    }

    public int getDefence() {
        dexterity = dexterity * currentOffhandItem.defenceValue;
        return dexterity;
    }

    public void getPlayerImage() {

        up1 = setup("/player/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);

    }

    public void getPlayerAttackImage() {

        if (currentWeapon.weaponType == WeaponType.SWORD) {
            attackUp1 = setup("/player/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        } else if (currentWeapon.weaponType == WeaponType.AXE) {
            attackUp1 = setup("/player/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }

    }

    public void update() {

        if (attacking) {
            attack();
        } else if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {

            if (keyHandler.upPressed) {
                direction = Direction.UP;
            } else if (keyHandler.downPressed) {
                direction = Direction.DOWN;
            } else if (keyHandler.leftPressed) {
                direction = Direction.LEFT;
            } else if (keyHandler.rightPressed) {
                direction = Direction.RIGHT;
            }

            //check world collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            //check object collision
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickupObject(objIndex);

            //check NPC collision
            Entity npc = gamePanel.collisionChecker.checkEntity(this, gamePanel.worldManager.getCurrentWorld().getNPCs().toArray(Entity[]::new));
            interactNPC(npc);

            //check monster collision
            Entity monster = gamePanel.collisionChecker.checkEntity(this, gamePanel.worldManager.getCurrentWorld().getMonsters().toArray(Entity[]::new));
            contactMonster(monster);

            //check event
            gamePanel.worldManager.getCurrentWorld().checkEvent();

            //move player if no collision
            if (!collisionOn && !keyHandler.enterPressed) {

                switch (direction) {

                    case UP:
                        worldY -= speed;
                        break;
                    case DOWN:
                        worldY += speed;
                        break;
                    case LEFT:
                        worldX -= speed;
                        break;
                    case RIGHT:
                        worldX += speed;
                        break;

                }

            }

            if (keyHandler.enterPressed && !attackCanceled) {

                if (currentWeapon.itemToConsume != null) {
                    for (Item item : inventory) {
                        if (item.getClass() == currentWeapon.itemToConsume.getClass()) {

                            inventory.remove(item);

                            SoundSystem.playSound(Sounds.EFFECT_SWING_WEAPON);
                            ControllerSystem.doVibration(0.2f, 0.2f, 100);
                            attacking = true;
                            spriteCounter = 0;

                            break;
                        }
                    }
                } else {

                    SoundSystem.playSound(Sounds.EFFECT_SWING_WEAPON);
                    ControllerSystem.doVibration(0.2f, 0.2f, 100);
                    attacking = true;
                    spriteCounter = 0;

                }

            }

            attackCanceled = false;
            gamePanel.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }

        }

        if (invincible) {
            invincibleTimer++;
            if (invincibleTimer > 60) {
                invincible = false;
                invincibleTimer = 0;
            }
        }

        checkLevelUp();

    }

    private void attack() {

        spriteCounter++;
        if (spriteCounter <= 5) spriteNumber = 1;
        if (spriteCounter > (5 * currentWeapon.attackSpeedMultiplier) && spriteCounter < (25 * currentWeapon.attackSpeedMultiplier)) {
            spriteNumber = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case UP:
                    worldY -= attackArea.height;
                    break;
                case DOWN:
                    worldY += attackArea.height;
                    break;
                case LEFT:
                    worldX -= attackArea.width;
                    break;
                case RIGHT:
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            Entity monster = gamePanel.collisionChecker.checkEntity(this, gamePanel.worldManager.getCurrentWorld().getMonsters().toArray(Entity[]::new));
            damageMonster(monster, false);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

            if (spriteCounter == (15 * currentWeapon.attackSpeedMultiplier)) {
                if (currentWeapon.weaponType == WeaponType.BOW) {
                    if (!gamePanel.worldManager.getCurrentWorld().getProjectiles().contains(currentWeapon.projectile)) {
                        currentWeapon.projectile.set(worldX, worldY, direction, true, this);

                        gamePanel.worldManager.getCurrentWorld().getProjectiles().add(currentWeapon.projectile);
                    }
                }
            }

        }

        if (spriteCounter > (25 * currentWeapon.attackSpeedMultiplier)) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void pickupObject(int index) {

        if (index != 999) {

            String text;

            if (inventory.size() != inventorySize) {

                inventory.add((Item) gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[index]);
                SoundSystem.playSound(Sounds.EFFECT_COIN);

                text = "You picket up " + gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[index].name + "!";

            } else {
                text = "You cannot carry anymore!";
            }

            gamePanel.ui.addMessage(text);
            gamePanel.worldManager.getCurrentWorld().getObjects().toArray(Entity[]::new)[index] = null;

        }

    }

    public void interactNPC(Entity entity) {
        if (gamePanel.keyHandler.enterPressed) {
            if (entity != null) {
                attackCanceled = true;
                entity.speak();
            }
        }
    }

    public void contactMonster(Entity entity) {

        if (entity != null) {

            if (!invincible && entity.dyingTimer == 0) {
                SoundSystem.playSound(Sounds.EFFECT_RECEIVE_DAMAGE);
                ControllerSystem.doVibration(0.5f, 0.5f, 700);

                life -= UtilityTool.calculateDamage(entity, this);
                invincible = true;
            }

        }

    }

    public void damageMonster(Entity entity, boolean isFromProjectile) {

        if (entity != null) {
            if (!entity.invincible) {
                SoundSystem.playSound(Sounds.EFFECT_HIT_MONSTER);
                if (!isFromProjectile) {
                    int damage = UtilityTool.calculateDamage(this, entity);
                    entity.life -= damage;
                    gamePanel.ui.addMessage("Damage: " + damage);
                } else {
                    entity.life -= currentWeapon.projectile.attack;
                    gamePanel.ui.addMessage("Damage: " + currentWeapon.projectile.attack);
                }
                entity.invincible = true;
                entity.damageReaction();
                gamePanel.ui.currentTarget = entity;
                gamePanel.ui.drawTarget();
                if (entity.life <= 0) {
                    entity.dying = true;
                    gamePanel.ui.addMessage("You killed: " + entity.name);
                    gamePanel.ui.addMessage("EXP: " + entity.exp);
                    exp += entity.exp;
                }

            }

        }

    }

    public void checkLevelUp() {

        if (exp >= nextLevelExp) {

            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defence = getDefence();

            SoundSystem.playSound(Sounds.EFFECT_LEVEL_UP);

            gamePanel.ui.currentSimpleDialog = "Du bist jetzt Level " + level + "!";
            gamePanel.gameState = GameState.MESSAGE;

        }

    }

    public void selectItem() {

        int index = gamePanel.ui.getItemIndexOnSlot();

        if (index < inventory.size()) {

            Item selectedItem = inventory.get(index);

            if (selectedItem.equipType == EquipType.WEAPON) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            } else if (selectedItem.equipType == EquipType.OFFHAND) {
                currentOffhandItem = selectedItem;
                defence = getDefence();
            } else if (selectedItem.equipType == EquipType.CONSUMABLE) {
                selectedItem.use();
                inventory.remove(index);
            }

        }

    }

    public void teleport(World world, int x, int y, boolean teleportCamera) {

        int xOffset = (int) (gamePanel.tileManager.cameraX - gamePanel.player.worldX);
        int yOffset = (int) (gamePanel.tileManager.cameraY - gamePanel.player.worldY);

        gamePanel.worldManager.changeWorld(world);

        worldX = x;
        worldY = y;

        if (teleportCamera) {
            gamePanel.tileManager.cameraX = worldX + xOffset;
            gamePanel.tileManager.cameraY = worldY + yOffset;
        }

    }

    public void draw(Graphics2D g2) {

        int screenX = (int) (worldX - gamePanel.tileManager.cameraX + gamePanel.halfWidth);
        int screenY = (int) (worldY - gamePanel.tileManager.cameraY + gamePanel.halfHeight);

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case UP:
                if (!attacking) {
                    if (spriteNumber == 1) image = up1;
                    if (spriteNumber == 2) image = up2;
                }
                if (attacking) {
                    tempScreenY = screenY - gamePanel.tileSize;
                    if (spriteNumber == 1) image = attackUp1;
                    if (spriteNumber == 2) image = attackUp2;
                }
                break;
            case DOWN:
                if (!attacking) {
                    if (spriteNumber == 1) image = down1;
                    if (spriteNumber == 2) image = down2;
                }
                if (attacking) {
                    if (spriteNumber == 1) image = attackDown1;
                    if (spriteNumber == 2) image = attackDown2;
                }
                break;
            case LEFT:
                if (!attacking) {
                    if (spriteNumber == 1) image = left1;
                    if (spriteNumber == 2) image = left2;
                }
                if (attacking) {
                    tempScreenX = screenX - gamePanel.tileSize;
                    if (spriteNumber == 1) image = attackLeft1;
                    if (spriteNumber == 2) image = attackLeft2;
                }
                break;
            case RIGHT:
                if (!attacking) {
                    if (spriteNumber == 1) image = right1;
                    if (spriteNumber == 2) image = right2;
                }
                if (attacking) {
                    if (spriteNumber == 1) image = attackRight1;
                    if (spriteNumber == 2) image = attackRight2;
                }
                break;
        }

        if (invincible) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }

}
