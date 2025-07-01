package de.athalion.game.twodgame.entity;

import de.athalion.game.twodgame.input.ControllerSystem;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.particle.Particle;
import de.athalion.game.twodgame.sound.SoundSystem;
import de.athalion.game.twodgame.sound.Sounds;
import de.athalion.game.twodgame.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {

    final GamePanel gamePanel;

    public EntityType type;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public BufferedImage characterImage;
    public Direction direction = Direction.DOWN;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int worldX, worldY;
    public int speed;

    public int actionLockTimer;
    public int invincibleTimer;
    public int dyingTimer;
    public int hpBarTimer;

    public boolean invincible;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;

    public String name;
    public String id;
    public boolean collision = false;

    //Character status
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;

    public int nextLevelExp;
    public int gold;
    public Item currentWeapon;
    public Item currentOffhandItem;

    public final String[][][] dialogues = new String[100][100][4];
    public int dialogueSet = 0;
    public int dialoguePage = 0;

    public Entity(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

    }

    public void setAction() {
    }

    public void damageReaction() {
    }

    public void facePlayer() {

        switch (gamePanel.player.direction) {

            case Direction.UP:
                direction = Direction.DOWN;
                break;
            case Direction.DOWN:
                direction = Direction.UP;
                break;
            case Direction.RIGHT:
                direction = Direction.LEFT;
                break;
            case Direction.LEFT:
                direction = Direction.RIGHT;
                break;

        }

    }

    public void speak() {
    }

    public void startDialog(Entity entity, int setNumber) {

        gamePanel.keyHandler.enterPressed = false;
        gamePanel.ui.npc = entity;
        dialogueSet = setNumber;
        dialoguePage = 0;
        gamePanel.ui.commandNum = 0;
        gamePanel.gameState = GameState.DIALOG;

    }

    public void update() {

        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.worldManager.getCurrentWorld().getNPCs().toArray(Entity[]::new));
        gamePanel.collisionChecker.checkEntity(this, gamePanel.worldManager.getCurrentWorld().getMonsters().toArray(Entity[]::new));
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (this.type == EntityType.MONSTER && contactPlayer && this.dyingTimer == 0) {
            if (!gamePanel.player.invincible) {
                SoundSystem.playSound(Sounds.EFFECT_RECEIVE_DAMAGE);
                ControllerSystem.doVibration(0.5f, 0.5f, 700);

                gamePanel.player.life -= UtilityTool.calculateDamage(this, gamePanel.player);
                gamePanel.player.invincible = true;
            }
        }

        if (!collisionOn) {

            switch (direction) {
                case Direction.UP:
                    worldY -= speed;
                    break;
                case Direction.DOWN:
                    worldY += speed;
                    break;
                case Direction.LEFT:
                    worldX -= speed;
                    break;
                case Direction.RIGHT:
                    worldX += speed;
                    break;
            }

        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }

        if (invincible) {
            invincibleTimer++;
            if (invincibleTimer > 60) {
                invincible = false;
                invincibleTimer = 0;
            }

        }

    }

    public void draw(Graphics2D g2) {

        int screenX = (int) (worldX - gamePanel.tileManager.cameraX + gamePanel.halfWidth);
        int screenY = (int) (worldY - gamePanel.tileManager.cameraY + gamePanel.halfHeight);

        if (screenX + gamePanel.tileSize >= 0
                && screenX - gamePanel.tileSize <= gamePanel.screenWidth
                && screenY + gamePanel.tileSize >= 0
                && screenY - gamePanel.tileSize <= gamePanel.screenHeight) {

            BufferedImage image = null;
            switch (direction) {
                case Direction.UP:
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                    break;
                case Direction.DOWN:
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                    break;
                case Direction.LEFT:
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                    break;
                case Direction.RIGHT:
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                    break;
            }

            //monster HP bar
            if (type == EntityType.MONSTER && hpBarOn) {

                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(Color.BLACK);
                g2.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, gamePanel.tileSize / 6 + 2);
                g2.setColor(Color.RED);
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, gamePanel.tileSize / 6);

                hpBarTimer++;

                if (hpBarTimer > 600) {
                    hpBarOn = false;
                    hpBarTimer = 0;
                }

            }

            if (invincible) {
                hpBarOn = true;
                hpBarTimer = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
            }
            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, null);

            //reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        }

    }

    private void dyingAnimation(Graphics2D g2) {

        dyingTimer++;
        if (dyingTimer <= 5)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        if (dyingTimer > 5 && dyingTimer <= 10)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        if (dyingTimer > 10 && dyingTimer <= 15)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        if (dyingTimer > 15 && dyingTimer <= 20)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        if (dyingTimer > 20 && dyingTimer <= 25)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        if (dyingTimer > 25 && dyingTimer <= 30)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        if (dyingTimer > 30 && dyingTimer <= 35)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        if (dyingTimer > 35 && dyingTimer <= 40) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
            gamePanel.worldManager.getCurrentWorld().spawnParticle(Particle.Textures.ENTITY_DEATH, worldX, worldY);
        }
        if (dyingTimer > 40) {
            dying = false;
            alive = false;
        }
    }

    public BufferedImage setup(String pathName, int width, int height) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathName + ".png")));
            image = UtilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            Logger.error("Error while setting up image " + pathName + ": " + e.getMessage());
            Logger.stackTrace(e.getStackTrace());
        }

        return image;
    }

}
