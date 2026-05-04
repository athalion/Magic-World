package de.athalion.game.twodgame.item;

import de.athalion.game.twodgame.entity.Entity;
import de.athalion.game.twodgame.graphics.DrawContext;
import de.athalion.game.twodgame.item.material.Material;

public class Item extends Entity {

    Material material;
    int amount;

    public Item(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    @Override
    public void draw(DrawContext context, int screenX, int screenY) {
        context.drawTexture(material.getTexture(), screenX, screenY);
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

}
