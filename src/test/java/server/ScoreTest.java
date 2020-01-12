package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    @Test
    void gettersAndSetters() {
        Score a = new Score("hh", 1, 1);
        Assertions.assertEquals(a,a);
        a.setUsername("aa");
        a.setScoreA(2);
        a.setScoreW(2);
        Assertions.assertEquals(a.getUsername(),"aa");
        Assertions.assertEquals(a.getScoreA(),2);
        Assertions.assertEquals(a.getScoreW(),2);
    }

    @Test
    void equals1() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 1, 1);
        Assertions.assertEquals(a,b);
    }

    @Test
    void equals2() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 0, 1);
        Assertions.assertNotEquals(a,b);
        Assertions.assertNotEquals(a,null);
        Assertions.assertNotEquals(a,"sl");
    }

    @Test
    void equals3() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 1, 0);
        Assertions.assertNotEquals(a,b);
    }

    @Test
    void equals4() {
        Score a = new Score("hhh", 1, 1);
        Score b = new Score("hh", 1, 1);
        Assertions.assertNotEquals(a,b);
    }




}
