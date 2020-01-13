package server;

public class Game {
    private String username;
    private int gamesPlayed;
    private int highestLevel;


    /**
     * Constructor.
     *  @param username set username using string
     * @param gamesPlayed set games Played by user using int
     * @param highestLevel set highest level reached by user using int
     */
    public Game(String username, int gamesPlayed, int highestLevel) {
        this.username = username;
        this.gamesPlayed = gamesPlayed;
        this.highestLevel = highestLevel;
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
}
