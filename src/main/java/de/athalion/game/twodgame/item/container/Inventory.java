package de.athalion.game.twodgame.item.container;

import de.athalion.game.twodgame.item.ItemStack;
import de.athalion.game.twodgame.item.material.Material;

public class Inventory extends Container {

    ItemStack equippedItem;

    /**
     * Initializes an inventory with the specified capacity.
     * The actual capacity is also determined by the {@link Material Material} of the items and its stack limit.
     *
     * @param size the capacity of the container
     */
    public Inventory(int size) {
        super(size);
    }

}
