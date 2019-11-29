package server;

public class Game {
    private String username;
    private int gamesPlayed;
    private int highestLevel;
    private String award;


    /**
     * Constructor.
     *  @param username set username using string
     * @param gamesPlayed set games Played by user using int
     * @param highestLevel set highest level reached by user using int
     * @param award Set award as a String
     */
    public Game(String username, int gamesPlayed, int highestLevel, String award) {
        this.username = username;
        this.gamesPlayed = gamesPlayed;
        this.highestLevel = highestLevel;
        this.award = award;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }
}
