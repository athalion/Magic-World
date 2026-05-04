package de.athalion.game.twodgame.main.update;

import de.athalion.game.twodgame.main.GameInstance;

public class MenuUpdater implements Updater {

    @Override
    public void update() {
        GameInstance.getInstance().getMenuManager().update();
    }

}
