package de.athalion.game.twodgame.input;

import com.studiohartman.jamepad.ControllerState;

public interface InputStateExtractor {

    InputActionState extract(ControllerState controllerState);

}
