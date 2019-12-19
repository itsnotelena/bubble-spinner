package game;

import config.Config;

public class Timer {

    private transient long startingTime;
    private transient long pauseTime;
    private transient long resumeTime;

    public Timer() {
        startingTime = System.currentTimeMillis();
    }

    /**
     * Calculate the remaining time until the
     * game finishes.
     * @return a String with format minutes:seconds.
     */
    public String calculateRemainingTime() {
        long difference = ((pauseTime != 0 ? pauseTime : System.currentTimeMillis()) - startingTime) / 1000;
        long remainingTime = Config.Game.GAME_TIME - difference;
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

    public boolean isOver() {
        return calculateRemainingTime().equals("00:00");
    }

    public void pause() {
        pauseTime = System.currentTimeMillis();
    }

    public void resume() {
        resumeTime = System.currentTimeMillis();
        startingTime += resumeTime - pauseTime;
        pauseTime = 0;
    }
}
