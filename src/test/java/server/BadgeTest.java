package server;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BadgeTest {

    @Test
    void gettersAndSetters() {
        Badge a = new Badge("hh", BadgesEnum.Badge_Legend);
        a.setUsername("aa");
        a.setAward(BadgesEnum.Badge_Legend);

        Assertions.assertEquals("aa", a.getUsername());
        Assertions.assertEquals(BadgesEnum.Badge_Legend, a.getAward());
    }

    @Test
    void equalsFalseTest1() {
        Badge one = new Badge("Cardi", BadgesEnum.Badge_Gamer);
        Object obj = new Object();
        Assert.assertNotEquals(one, obj);
    }

    @Test
    void equalsFalseTest2() {
        Badge one = new Badge("Beyonce", BadgesEnum.Badge_Legend);
        Badge two = new Badge("Anitta", BadgesEnum.Top_Of_The_Week);
        Assert.assertNotEquals(one,two);
    }

    @Test
    void equalsSameObject() {
        Badge one = new Badge("Bey", BadgesEnum.Badge_Legend);
        Assert.assertEquals(one, one);
    }

    @Test
    void equalsTrueTest() {
        Badge one = new Badge("TS", BadgesEnum.Badge_Gamer);
        Badge two = new Badge("TS", BadgesEnum.Badge_Gamer);
        Assert.assertEquals(one, two);
    }

    @Test
    void equalsUsernameFalse() {
        Badge one = new Badge("Selena Gomez", BadgesEnum.Top_Of_The_Week);
        Badge two = new Badge("Hayley Kiyoko", BadgesEnum.Badge_Gamer);

        Assert.assertNotEquals(one, two);
    }

    @Test
    void equalsUsernameFalse2() {
        Badge one = new Badge("Selena Gomez", BadgesEnum.Badge_Legend);
        Badge two = new Badge("Hayley Kiyoko", BadgesEnum.Badge_Legend);

        Assert.assertNotEquals(one, two);
    }

    @Test
    void testHashCode() {
        Badge one = new Badge("Jennifer", BadgesEnum.Badge_Gamer);
        Badge two = new Badge("Jennifer", BadgesEnum.Badge_Gamer);

        Assertions.assertTrue(one.equals(two) && two.equals(one));
        Assertions.assertTrue(one.hashCode() == two.hashCode());
    }

    @Test
    void testToString() {
        Badge one = new Badge("Anna", BadgesEnum.Badge_Gamer);
        Badge two = new Badge("Anna", BadgesEnum.Badge_Gamer);

        Assertions.assertEquals(one.toString(), two.toString());
    }
}
