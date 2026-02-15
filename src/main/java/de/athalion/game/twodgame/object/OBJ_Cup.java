package de.athalion.game.twodgame.object;

import de.athalion.game.twodgame.entity.EntityType;
import de.athalion.game.twodgame.item.Item;
import de.athalion.game.twodgame.main.GamePanel;

import java.awt.*;

public class OBJ_Cup extends Item {

    private final GamePanel gamePanel;

    public OBJ_Cup(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = EntityType.OBJECT;
        name = "cup";
        id = "OBJ_cup";

        down1 = setup("/objects/cup", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use() {
        if (gamePanel.player.solidArea.intersects(new Rectangle()))
            gamePanel.simpleDialog("");
    }

}
