package net.anvian.sodiumextrainformation.util;

import net.anvian.sodiumextrainformation.Constants;
import org.slf4j.Logger;

public class SessionManager {
    private static final Logger LOGGER = Constants.LOG;

    private long sessionStartTime;
    private long totalTimePlayed;
    private boolean inSession;
    private boolean isPaused;

    public synchronized void startSession() {
        sessionStartTime = System.currentTimeMillis();
        inSession = true;
        isPaused = false;
        LOGGER.info("Session started");
    }

    public synchronized void endSession() {
        inSession = false;
        LOGGER.info("Session ended");
    }

    public synchronized void pauseSession() {
        isPaused = true;
        totalTimePlayed += (System.currentTimeMillis() - sessionStartTime);
        LOGGER.info("Session paused");
    }

    public synchronized void resumeSession() {
        isPaused = false;
        sessionStartTime = System.currentTimeMillis();
        LOGGER.info("Session resumed");
    }

    public synchronized void updateSessionTime() {
        long currentTime = System.currentTimeMillis();
        totalTimePlayed += (currentTime - sessionStartTime);
        sessionStartTime = currentTime;
    }

    public void resetSession() {
        sessionStartTime = 0;
        totalTimePlayed = 0;
        inSession = false;
        isPaused = false;
    }

    public boolean isInSession() {
        return inSession;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public long getTotalTimePlayed() {
        return totalTimePlayed / 1000;
    }
}