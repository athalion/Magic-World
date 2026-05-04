package de.athalion.game.twodgame.item.material;

import de.athalion.game.twodgame.entity.stats.StatModifier;
import de.athalion.game.twodgame.item.ItemActivationType;
import de.athalion.game.twodgame.lang.Translations;
import de.athalion.game.twodgame.resources.texture.Texture;
import de.athalion.game.twodgame.schedule.Scheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class Material {

    Texture texture;
    String key;
    int stackLimit = 99;
    List<StatModifier> statModifiers = new ArrayList<>();
    ItemActivationType activationType = ItemActivationType.NONE;

    Material(Texture texture, String key) {
        this.texture = texture;
        this.key = key;

        if (texture.isAnimated()) Scheduler.runTicker(texture::nextFrame);
    }

    public Texture getTexture() {
        return texture;
    }

    public String getName() {
        return Translations.get("item." + key + ".name");
    }

    public String[] getLore() {
        return Translations.get("item." + key + ".lore").split("\n");
    }

    public int getStackLimit() {
        return stackLimit;
    }

    public List<StatModifier> getStatModifiers() {
        return statModifiers;
    }

    public ItemActivationType getActivationType() {
        return activationType;
    }

    /**
     * Called when {@link Material#activationType} equals {@link ItemActivationType#SELECT} and the item is selected
     * or when {@link Material#activationType} equals {@link ItemActivationType#TOGGLE} and the item is toggled.
     * @param on true if the item is selected or toggled on, false otherwise
     */
    public void onToggle(boolean on) {
    }

    /**
     * Called when {@link Material#activationType} equals {@link ItemActivationType#CONSUME} and the item is used.
     * @return true if the item should be consumed, false otherwise
     */
    public boolean onUse() {
        return false;
    }

}
