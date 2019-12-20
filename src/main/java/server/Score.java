package server;

import java.util.Objects;

public class Score {
    private String username;
    private int scoreW;
    private int scoreA;

    public Score() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return scoreW == score.scoreW
                && scoreA == score.scoreA
                && username.equals(score.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, scoreW, scoreA);
    }
}
