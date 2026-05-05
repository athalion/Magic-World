package de.athalion.game.twodgame.input.controller;

import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.sound.Sounds;
import de.athalion.game.twodgame.utility.RMSHelper;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AdvancedVibration {

    private final Vector[] vibrationPattern;

    public AdvancedVibration(Sounds sound) throws UnsupportedAudioFileException, IOException {
        Vector[] extractedPattern = RMSHelper.extractStereoRms(sound.getIdentifier().stream());
        extractedPattern = RMSHelper.applyCurves(extractedPattern);
        vibrationPattern = RMSHelper.applyExponentialSmoothing(extractedPattern);
    }

    public Vector[] getVibrationPattern() {
        return vibrationPattern;
    }

}
