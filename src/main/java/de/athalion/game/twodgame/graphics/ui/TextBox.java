package de.athalion.game.twodgame.graphics.ui;

import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;

public class TextBox {

    int x, y, width, height;
    int timer = -1;
    int openTime;
    String[] strings;
    String currentString;
    String currentCombinedString;

    public TextBox(int x, int y, int width, int height, int openTime, String... strings) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.openTime = openTime;
        this.strings = strings;
    }

    private void open() {
        timer = 0;
    }

    private void close() {
        timer = 51;
    }

    public void update(int index) {
        if (index == openTime) open();
        else if (index == openTime + strings.length) close();
        else currentString = strings[index];
    }

    public boolean render(Graphics2D g2) {
        if (timer != 50 && timer >= 0) timer++;

        int currentTime = timer;
        if (timer > 50) currentTime = 100 - timer;
        RenderUtils.drawSubWindow(x, y, width, height, 35, 130, Color.BLUE, g2);

        return timer >= 100;
    }

}
