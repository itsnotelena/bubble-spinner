package game;

import client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import server.Badge;
import server.BadgesEnum;
import server.Score;
import server.User;

public class AchievementTest {

    @Mock
    private transient Client client;

    private transient User user;
    private transient Achievement achievement;

    @BeforeEach
    public void before() {
        client = Mockito.mock(Client.class);
        user = new User("", "", "");
        achievement = new Achievement(user);
        achievement.setClient(client);
    }

    @Test
    public void testFirstVictory() {
        Score score = new Score("", 10, 10);
        Mockito.when(client.getSCore(user)).thenReturn(score);
        Mockito.when(client.addBadge(Mockito.any())).thenReturn(true);
        achievement.updateAchievements();
        Mockito.verify(client, Mockito.times(1))
                .addBadge(new Badge("", BadgesEnum.First_Victory));
    }

    @Test
    public void testBadgeGamer() {
        Score score = new Score("", 10, 150);
        Mockito.when(client.getSCore(user)).thenReturn(score);
        Mockito.when(client.addBadge(Mockito.any())).thenReturn(true);
        achievement.updateAchievements();
        Mockito.verify(client, Mockito.times(1))
                .addBadge(new Badge("", BadgesEnum.Badge_Gamer));
    }

    @Test
    public void testBadgeLegend() {
        Score score = new Score("", 10, 750);
        Mockito.when(client.getSCore(user)).thenReturn(score);
        Mockito.when(client.addBadge(Mockito.any())).thenReturn(true);
        achievement.updateAchievements();
        Mockito.verify(client, Mockito.times(1))
                .addBadge(new Badge("", BadgesEnum.Badge_Legend));
    }

    @Test
    public void testBadgeVeteran() {
        Score score = new Score("", 10, 1500);
        Mockito.when(client.getSCore(user)).thenReturn(score);
        Mockito.when(client.addBadge(Mockito.any())).thenReturn(true);
        achievement.updateAchievements();
        Mockito.verify(client, Mockito.times(1))
                .addBadge(new Badge("", BadgesEnum.Badge_Veteran));
    }

    @Test
    public void testTopOfTheWeek() {
        Score score = new Score("", 500, 0);
        Mockito.when(client.getSCore(user)).thenReturn(score);
        Mockito.when(client.addBadge(Mockito.any())).thenReturn(true);
        achievement.updateAchievements();
        Mockito.verify(client, Mockito.times(1))
                .addBadge(new Badge("", BadgesEnum.Top_Of_The_Week));
    }
}
