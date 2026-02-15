package de.athalion.game.twodgame.graphics;

import com.badlogic.gdx.math.Vector2;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.particle.Particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EnvironmentEffects {

    private final GamePanel gamePanel;

    final List<Vector2> rainPositions = new ArrayList<>();

    public EnvironmentEffects(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2, Type... effects) {

        List<Type> effectTypes = Arrays.stream(effects).toList();

        if (effectTypes.contains(Type.RAIN)) {
            Random random = new Random();
            for (int i = 0; i < gamePanel.worldManager.getCurrentWorld().getMaxWorldCol() / 2; i++) {
                rainPositions.add(new Vector2(random.nextInt(gamePanel.worldManager.getCurrentWorld().getMaxWorldCol() * gamePanel.tileSize), -10));
            }
            g2.setColor(new Color(100, 100, 255, 100));
            for (Vector2 rainPosition : rainPositions) {
                int screenX = (int) (rainPosition.x - gamePanel.tileManager.cameraX + gamePanel.halfWidth);
                int screenY = (int) (rainPosition.y - gamePanel.tileManager.cameraY + gamePanel.halfHeight);

                if (screenX + gamePanel.tileSize >= 0
                        && screenX - gamePanel.tileSize < gamePanel.screenWidth
                        && screenY + gamePanel.tileSize >= 0
                        && screenY - gamePanel.tileSize < gamePanel.screenHeight) {
                    g2.fillRect(screenX, screenY, 3, 15);
                }

                rainPosition.y += 10;
            }
            Vector2 rainPosition = rainPositions.get(random.nextInt(rainPositions.size()));
            gamePanel.worldManager.getCurrentWorld().spawnParticle(Particle.Textures.SPLASH, (int) rainPosition.x, (int) rainPosition.y);
            rainPositions.removeIf(positions -> positions.y - 20 > gamePanel.worldManager.getCurrentWorld().getMaxWorldRow() * gamePanel.tileSize);
        }

    }

    public enum Type {
        RAIN, RAIN_DULL, DAYLIGHT_CYCLE
    }

}
