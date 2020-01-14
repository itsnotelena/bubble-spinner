package server;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BadgeTest {

    @Test
    void gettersAndSetters() {
        Badge a = new Badge("hh", "hoo");
        a.setUsername("aa");
        a.setAward("bla");

        Assertions.assertEquals("aa", a.getUsername());
        Assertions.assertEquals("bla", a.getAward());
    }

    @Test
    void equalsFalseTest1() {
        Badge one = new Badge("Cardi", "LEVEL1");
        Object obj = new Object();
        Assert.assertNotEquals(one, obj);
    }

    @Test
    void equalsFalseTest2() {
        Badge one = new Badge("Beyonce", "LEVEL9");
        Badge two = new Badge("Anitta", "LEVEL3");
        Assert.assertNotEquals(one,two);
    }

    @Test
    void equalsSameObject() {
        Badge one = new Badge("Bey", "LEVEL2");
        Assert.assertEquals(one, one);
    }

    @Test
    void equalsTrueTest() {
        Badge one = new Badge("TS", "LEVEL0");
        Badge two = new Badge("TS", "LEVEL0");
        Assert.assertEquals(one, two);
    }

    @Test
    void equalsNullTest() {
        Badge one = new Badge("NULL", "NULL");
        Assert.assertNotEquals(one, null);
    }

    @Test
    void equalsUsernameFalse() {
        Badge one = new Badge("Selena Gomez", "LEVEL7");
        Badge two = new Badge("Hayley Kiyoko", "LEVEL3");

        Assert.assertNotEquals(one, two);
    }

    @Test
    void equalsUsernameFalse2() {
        Badge one = new Badge("Selena Gomez", "LEVEL7");
        Badge two = new Badge("Hayley Kiyoko", "LEVEL7");

        Assert.assertNotEquals(one, two);
    }

    @Test
    void testHashCode() {
        Badge one = new Badge("Jennifer", "LEVEL4");
        Badge two = new Badge("Jennifer", "LEVEL4");

        Assertions.assertTrue(one.equals(two) && two.equals(one));
        Assertions.assertTrue(one.hashCode() == two.hashCode());
    }

    @Test
    void testToString() {
        Badge one = new Badge("Anna", "LEVEL2");
        Badge two = new Badge("Anna", "LEVEL2");

        Assertions.assertEquals(one.toString(), two.toString());
    }
}
