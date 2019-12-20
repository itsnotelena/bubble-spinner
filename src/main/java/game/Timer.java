package game;

import config.Config;

public class Timer {

    private transient long startingTime;
    private transient long pauseTime;
    private transient long resumeTime;

    public Timer() {
        startingTime = now();
    }

    /**
     * Calculate the remaining time until the
     * game finishes.
     * @return a String with format minutes:seconds.
     */
    public String calculateRemainingTime() {
        long remainingTime = timeLeft();
        long minutes = remainingTime / 60;
        long seconds = remainingTime % 60;
        return new StringBuilder()
                .append((minutes < 10 ? '0' : ""))
                .append(minutes)
                .append(':')
                .append((seconds < 10 ? '0' : ""))
                .append(seconds)
                .toString();
    }

    /**
     * Check if the timer ended.
     * @return true if it's over, false otherwise.
     */
    public boolean isOver() {
        return timeLeft() <= 0;
    }

    /**
     * Milliseconds left until the time is over.
     * @return long number with milliseconds left.
     */
    public long timeLeft() {
        long difference = ((pauseTime != 0 ? pauseTime : now()) - startingTime) / 1000;
        return Config.Game.GAME_TIME - difference;
    }

    /**
     * Pause the timer.
     */
    public void pause() {
        pauseTime = now();
    }

    /**
     * Resume the timer from a pause.
     */
    public void resume() {
        if (pauseTime == 0) {
            return;
        }
        resumeTime = now();
        startingTime += resumeTime - pauseTime;
        pauseTime = 0;
    }

    /**
     * Set a new starting time for testing.
     * @param startingTime long with milliseconds start time.
     */
    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    /**
     * Return current millis.
     */
    private long now() {
        return System.currentTimeMillis();
    }
}
