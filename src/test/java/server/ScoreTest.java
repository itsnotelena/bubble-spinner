package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    @Test
    public void gettersAndSetters() {
        Score a = new Score("hh", 1, 1);
        Assertions.assertEquals(a,a);
        a.setUsername("aa");
        a.setScoreA(2);
        a.setHighestWeekScore(2);
        Assertions.assertEquals(a.getUsername(),"aa");
        Assertions.assertEquals(a.getScoreA(),2);
        Assertions.assertEquals(a.getHighestWeekScore(),2);
    }

    @Test
    public void equals1() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 1, 1);
        Assertions.assertEquals(a,b);
    }

    @Test
    public void equals2() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 0, 1);
        Assertions.assertNotEquals(a,b);
        Assertions.assertNotEquals(a,null);
        Assertions.assertNotEquals(a,"sl");
    }

    @Test
    public void equals3() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 1, 0);
        Assertions.assertNotEquals(a,b);
    }

    @Test
    public void equals4() {
        Score a = new Score("hhh", 1, 1);
        Score b = new Score("hh", 1, 1);
        Assertions.assertNotEquals(a,b);
    }

}
