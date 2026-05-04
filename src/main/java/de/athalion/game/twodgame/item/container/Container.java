package de.athalion.game.twodgame.item.container;

import de.athalion.game.twodgame.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Container {

    ItemStack[] items;

    /**
     * Initializes a container with the specified capacity.
     * The actual capacity is also determined by the {@link de.athalion.game.twodgame.item.material.Material Material} of the items and its stack limit.
     * @param size the capacity of the container
     */
    public Container(int size) {
        items = new ItemStack[size];
    }

    /**
     * Adds the items to this container. The items are added to the first slot which is empty
     * or contains the same items. If the slot can not hold all the items, the rest is then
     * distributed using the same procedure.
     * @param item the {@link ItemStack} to add to the container
     * @return all items that did not fit into the container or null if they all fit
     */
    public @Nullable ItemStack addItem(ItemStack item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                return null;
            } else if (items[i].getMaterial().equals(item.getMaterial())) {
                int freeSpace = items[i].getMaterial().getStackLimit() - items[i].getAmount();
                int moved = Math.min(freeSpace, item.getAmount());
                item.setAmount(item.getAmount() - moved);
                items[i].setAmount(items[i].getAmount() + moved);
            }
        }
        return item.getAmount() == 0 ? null : item;
    }

    /**
     * Adds a {@link List} of {@link ItemStack ItemStacks} into the container.
     * The method iterates over the items and tries to add each using {@link Container#addItem(ItemStack)}.
     * @param items the list of items to add
     * @return all items that did not fit into the container or an empty list if they all fit
     */
    public List<ItemStack> addItems(List<ItemStack> items) {
        List<ItemStack> notAddedItems = new ArrayList<>();
        items.forEach(itemStack -> {
            ItemStack leftItems = addItem(itemStack);
            if (leftItems != null) notAddedItems.add(leftItems);
        });
        return notAddedItems;
    }

    /**
     * Retrieves the content of this container exactly how it is in the container.
     * This includes empty slots.
     * @return the items as they are in the container
     */
    public ItemStack[] getContent() {
        return items;
    }

    /**
     * Retrieves the items in this container. This method will not return empty slots.
     * @return the items in this container without empty slots
     */
    public List<ItemStack> getItems() {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack item : items) {
            if (item != null) list.add(item);
        }
        return list;
    }

}
