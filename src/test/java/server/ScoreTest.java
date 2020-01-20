package server;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class ScoreTest {

    @Test
    public void gettersAndSetters() {
        Score a = new Score("hh", 1, 1);
        Assert.assertEquals(a,a);
        a.setUsername("aa");
        a.setScoreA(2);
        a.setHighestWeekScore(2);
        Assert.assertEquals(a.getUsername(),"aa");
        Assert.assertEquals(a.getScoreA(),2);
        Assert.assertEquals(a.getHighestWeekScore(),2);
    }

    @Test
    public void equals1() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 1, 1);
        Assert.assertEquals(a,b);
    }

    @Test
    public void equals2() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 0, 1);
        Assert.assertNotEquals(a,b);
        Assert.assertNotEquals(a,null);
        Assert.assertNotEquals(a,"sl");
    }

    @Test
    public void equals3() {
        Score a = new Score("hh", 1, 1);
        Score b = new Score("hh", 1, 0);
        Assert.assertNotEquals(a,b);
    }

    @Test
    public void equals4() {
        Score a = new Score("hhh", 1, 1);
        Score b = new Score("hh", 1, 1);
        Assert.assertNotEquals(a,b);
    }

}
