package de.athalion.game.twodgame.graphics.renderer;

import de.athalion.game.twodgame.camera.Camera;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.main.GameInstance;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.world.WorldInstance;
import de.athalion.game.twodgame.world.tile.TileManager;

public class WorldRenderer implements Renderer {

    @Override
    public void draw(DrawContext context, Camera camera) {
        WorldInstance worldInstance = camera.getWorldInstance();
        TileManager tileManager = worldInstance.getTileManager();
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < tileManager.getWorldWidth() && worldRow < tileManager.getWorldHeight()) {
            int tileNum = worldInstance.getTileManager().getMap()[worldCol][worldRow];

            int worldX = worldCol * GameInstance.getInstance().getGameFrame().TILE_SIZE;
            int worldY = worldRow * GameInstance.getInstance().getGameFrame().TILE_SIZE;
            int screenX = (int) (worldX - camera.getCameraConfiguration().getPosition().getX() + (double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2);
            int screenY = (int) (worldY - camera.getCameraConfiguration().getPosition().getY() + (double) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2);

            if (worldX + GameInstance.getInstance().getGameFrame().TILE_SIZE > camera.getCameraConfiguration().getPosition().getX() - ((double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2)
                    && worldX - GameInstance.getInstance().getGameFrame().TILE_SIZE < camera.getCameraConfiguration().getPosition().getX() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2)
                    && worldY + GameInstance.getInstance().getGameFrame().TILE_SIZE > camera.getCameraConfiguration().getPosition().getY() - ((double) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2)
                    && worldY - GameInstance.getInstance().getGameFrame().TILE_SIZE < camera.getCameraConfiguration().getPosition().getY() + ((double) GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT / 2)) {

                Texture texture = tileManager.getTileSet().getTile(tileNum).getTexture();
                int x = screenX - texture.getWidth() + GameInstance.getInstance().getGameFrame().TILE_SIZE;
                int y = screenY - texture.getHeight() + GameInstance.getInstance().getGameFrame().TILE_SIZE;
                context.drawTexture(texture, x, y);
            }
            worldCol++;

            if (worldCol == tileManager.getWorldWidth()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
