package de.athalion.game.twodgame.entity.npc;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    GamePanel gamePanel;

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = EntityType.NPC;
        name = "Alter Mann";
        id = "NPC_oldMan";

        direction = Direction.DOWN;
        speed = 1;

        getImage();
        setDialog();

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void getImage() {

        up1 = setup("/npc/oldman/up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/oldman/up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/oldman/down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/oldman/down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/oldman/left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/oldman/left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/oldman/right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/oldman/right_2", gamePanel.tileSize, gamePanel.tileSize);

        characterImage = setup("/npc/oldman/character", 32, 32);

    }

    public void setDialog() {

        dialogues[0][0][0] = "Guten Tag.";
        dialogues[0][0][1] = "001Hallo!";
        dialogues[0][0][2] = "001...";

        dialogues[0][1][0] = "Was führt Dich an solch einen abgelegenen Ort?";
        dialogues[0][1][1] = "002Also eigentlich bin ich zufällig hier...";
        dialogues[0][1][2] = "004Ich versuche die Welt zu retten!";
        dialogues[0][1][3] = "003...";

        dialogues[0][2][0] = "Ich weiß nicht, du wirkst eher wie ein Abenteurer auf\nmich.";
        dialogues[0][2][1] = "004Nagut, du hast Recht.";
        dialogues[0][2][2] = "003...";

        dialogues[0][3][0] = "Lass mich raten. Du bist hier um zu verhindern, dass\nER die Macht übernimmt!?";
        dialogues[0][3][1] = "004...";

        dialogues[0][4][0] = "Lass Dir gesagt sein, dass Du unmöglich Erfolg haben\nkannst.";
        dialogues[0][4][1] = "005Das werden wir ja sehen!";
        dialogues[0][4][2] = "005...";

        dialogues[0][5][0] = "Ich möchte dir einen Rat geben.";
        dialogues[0][5][1] = "006...";

        dialogues[0][6][0] = "Lass es sein! Du kannst nichts ausrichten!";
        dialogues[0][6][1] = "099...";

    }

    public void setAction() {

        actionLockTimer++;

        if (actionLockTimer >= 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = Direction.UP;
            }
            if (i > 25 && i <= 50) {
                direction = Direction.DOWN;
            }
            if (i > 50 && i <= 75) {
                direction = Direction.LEFT;
            }
            if (i > 75) {
                direction = Direction.RIGHT;
            }

            actionLockTimer = 0;

        }

    }

    public void speak() {

        facePlayer();
        startDialog(this, dialogueSet);

    }

}