package game;

import client.Client;
import server.Badge;
import server.BadgesEnum;
import server.Score;
import server.User;

public class Achievement {

    private transient User user;

    public Achievement(User user) {
        this.user = user;
    }

    public void updateAchievements() {
        Score score = new Client().getSCore(user);

        // Award based on total score
        if (score.getScoreA() > 1024) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.Badge_Veteran));
        } else if (score.getScoreA() > 512) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.Badge_Legend));
        } else if (score.getScoreA() > 128) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.Badge_Gamer));
        } else if (score.getScoreA() > 0) {
            new Client().addBadge(new Badge(score.getUsername(),
                            BadgesEnum.First_Victory));
        }

        // If Week score is high award badge
        if (score.getHighestWeekScore() > 256) {
            new Client().addBadge(new Badge(score.getUsername(),
                        BadgesEnum.Top_Of_The_Week));
        }

    }
}
