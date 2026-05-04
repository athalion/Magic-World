package de.athalion.game.twodgame.graphics.menu;

import de.athalion.game.twodgame.main.GameInstance;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MenuUtils {

    public static void drawControlTips(Graphics2D g2, String... tips) {

        List<String> strings = Arrays.stream(tips).toList();

        int x = 20;
        int y = GameInstance.getInstance().getGameFrame().SCREEN_HEIGHT - 20;

        g2.setColor(Color.ORANGE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));

        for (String string : strings) {
            g2.drawString(string, x, y);
            x += (int) (g2.getFontMetrics().getStringBounds(string, g2).getWidth() + 15);
        }

    }

    public static int getXForCenteredSomething(int width) {
        return GameInstance.getInstance().getGameFrame().SCREEN_WIDTH / 2 - width / 2;
    }

}
