package de.athalion.game.twodgame.item;

import de.athalion.game.twodgame.item.material.Material;

public class ItemStack {

    Material material;
    int amount;
    boolean toggled;

    /**
     * Represents an item while in a container. The stack will be initialized with an amount of one item.
     * @param material the material of this stack
     */
    public ItemStack(Material material) {
        this.material = material;
        this.amount = 1;
    }

    /**
     * Represents an item while in a container.
     * @param material the material of this stack
     * @param amount the amount of this stack
     */
    public ItemStack(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
