package de.athalion.game.twodgame.graphics.ui;

import java.awt.*;
import java.util.List;

public class TextBoxStack {

    List<TextBox> textBoxes;
    int index = 0;

    public TextBoxStack(TextBox... textBoxes) {
        this.textBoxes = List.of(textBoxes);
    }

    public void update() {
        textBoxes.forEach(textBox -> textBox.update(index));
        index++;
    }

    public void render(Graphics2D g2) {
        textBoxes.removeIf(textBox -> textBox.render(g2));
    }

}
