package de.athalion.game.twodgame.input.controller;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.math.Vector;
import de.athalion.game.twodgame.sound.Sounds;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class AdvancedVibrationExecutor {

    private final Consumer<Vector> sampleConsumer;
    private final HashMap<Sounds, AdvancedVibration> advancedVibrationPatterns = new HashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final Queue<Vector> sampleQueue = new LinkedList<>();

    public AdvancedVibrationExecutor(Consumer<Vector> sampleConsumer) {
        this.sampleConsumer = sampleConsumer;
    }

    public void init() {
        for (Sounds sound : Sounds.values()) {
            if (sound.isHapticsEnabled()) {
                try {
                    advancedVibrationPatterns.put(sound, new AdvancedVibration(sound));
                } catch (UnsupportedAudioFileException | IOException e) {
                    Logger.error("Failed to load advanced vibration patterns for sound " + sound.name() + "!");
                    Logger.stackTrace(e.getStackTrace());
                }
            }
        }

        scheduler.scheduleAtFixedRate(() -> {
            if (sampleQueue.peek() != null) sampleConsumer.accept(sampleQueue.poll());
        }, 0, 20, TimeUnit.MILLISECONDS);
    }

    public void executeVibration(Sounds sound) {
        AdvancedVibration advancedVibration = advancedVibrationPatterns.get(sound);
        sampleQueue.clear();
        sampleQueue.addAll(Arrays.asList(advancedVibration.getVibrationPattern()));
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

}
