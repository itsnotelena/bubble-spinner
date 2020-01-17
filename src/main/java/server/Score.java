package server;

public class Score {
    private String username;
    private int highestWeekScore;
    private int scoreA;

    public Score() {

    }

    /**
     * Constructor.
     * @param username set username using String
     * @param highestWeekScore set score for this week
     * @param scoreA set score for All time
     */
    public Score(String username, int highestWeekScore, int scoreA) {
        this.username = username;
        this.highestWeekScore = highestWeekScore;
        this.scoreA = scoreA;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighestWeekScore() {
        return highestWeekScore;
    }

    public void setHighestWeekScore(int highestWeekScore) {
        this.highestWeekScore = highestWeekScore;
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
        return highestWeekScore == score.highestWeekScore
                && scoreA == score.scoreA
                && username.equals(score.username);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
