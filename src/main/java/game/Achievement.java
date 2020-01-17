package game;

import client.Client;
import server.Badge;
import server.BadgesEnum;
import server.Score;
import server.User;

public class Achievement {

    private transient User user;

    private static final int VETERAN_SCORE = 1024;
    private static final int LEGEND_SCORE = 512;
    private static final int GAMER_SCORE = 128;
    private static final int TOP_OF_WEEK_SCORE = 256;

    /**
     * Achivement constructor.
     * @param user User object.
     */
    public Achievement(User user) {
        this.user = user;
    }

    /**
     * Calculate and add achievements to the database.
     */
    public void updateAchievements() {
        Score score = new Client().getSCore(user);

        // Award based on total score
        if (score.getScoreA() > VETERAN_SCORE) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.Badge_Veteran));
        } else if (score.getScoreA() > LEGEND_SCORE) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.Badge_Legend));
        } else if (score.getScoreA() > GAMER_SCORE) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.Badge_Gamer));
        } else if (score.getScoreA() > 0) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.First_Victory));
        }

        // If Week score is high award badge
        if (score.getHighestWeekScore() > TOP_OF_WEEK_SCORE) {
            new Client().addBadge(new Badge(score.getUsername(),
                        BadgesEnum.Top_Of_The_Week));
        }

    }
}