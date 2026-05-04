package de.athalion.game.twodgame.entity.stats;

import java.util.HashMap;
import java.util.List;

public class StatSet {

    HashMap<Stats, Integer> stats = new HashMap<>();

    public void setBase(Stats stat, int base) {
        stats.put(stat, base);
    }

    public int get(Stats stat) {
        return stats.get(stat);
    }

    public int getWithModifiers(Stats stat, List<StatModifier> modifiers) {
        int v = stats.get(stat);
        for (StatModifier modifier : modifiers) {
            if (modifier.stat.equals(stat)) v += modifier.amount;
        }
        return v;
    }

}
