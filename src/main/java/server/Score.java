package server;

public class Score {
    private String username;
    private int scoreW;
    private int scoreA;

    /**
     * Constructor.
     * @param username set username using String
     * @param scoreW set score for this week
     * @param scoreA set score for All time
     */
    public Score(String username, int scoreW, int scoreA) {
        this.username = username;
        this.scoreW = scoreW;
        this.scoreA = scoreA;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScoreW() {
        return scoreW;
    }

    public void setScoreW(int scoreW) {
        this.scoreW = scoreW;
    }

    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }
}
