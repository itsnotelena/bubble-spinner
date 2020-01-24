package server;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void gettersAndSetters() {
        User a = new User("hh", "hoo", "ohh");
        a.setUsername("aa");
        a.setEmail("bla");
        a.setPassword("ola");

        Assertions.assertEquals("aa", a.getUsername());
        Assertions.assertEquals("bla", a.getEmail());
        Assertions.assertEquals("ola", a.getPassword());
    }

    @Test
    public void equalsFalseTest1() {
        User one = new User("Cardi", "cardi@me", "abc");
        Object obj = new Object();
        Assert.assertNotEquals(one, obj);
    }

    @Test
    public void equalsFalseTest21() {
        User one = new User("Cardi", "cardi@me", "abc");
        User two = new User("Carrdi", "cardi@me", "abc");

        Assert.assertNotEquals(one, two);
    }

    @Test
    public void equalsFalseTest2() {
        User one = new User("Beyonce", "Beyonce@me", "jesus");
        User two = new User("Anitta", "anitta@me", "anitta");
        Assert.assertNotEquals(one,two);
    }

    @Test
    public void equalsSameObject() {
        User one = new User("Beyonce", "Beyonce@me", "jesus");
        Assert.assertEquals(one, one);
    }

    @Test
    public void equalsTrueTest() {
        User one = new User("Taylor", "taylor@me", "123");
        User two = new User("Taylor", "taylor@me", "123");
        Assert.assertEquals(one, two);
    }

    @Test
    public void equalsNullTest() {
        User one = new User("Jesus", "jesus@me", "666");
        Assert.assertNotEquals(one, null);
    }

    @Test
    public void equalsUsernameFalse() {
        User one = new User("Selena", "selena@me", "pii");
        User two = new User("Lady Gaga", "taylor@me", "bcc");

        Assert.assertNotEquals(one, two);
    }

    @Test
    public void testHashCode() {
        User one = new User("WAAAAA", "waaa@me", "waa");
        User two = new User("WAAAAA", "waaa@me", "waa");

        Assertions.assertTrue(one.equals(two) && two.equals(one));
        Assertions.assertTrue(one.hashCode() == two.hashCode());
    }

    @Test
    public void testToString() {
        User one = new User("user", "user@user", "pass");
        User two = new User("user", "user@user", "pass");

        Assertions.assertEquals(one.toString(), two.toString());
    }
}
