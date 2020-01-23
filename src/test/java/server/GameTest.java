package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    public void getterAndSetter() {
        Game game = new Game("sd",1,2);
        game.setGamesPlayed(100);
        game.setHighestLevel(1999);
        game.setUsername("asadd");
        Assertions.assertEquals(game.getGamesPlayed(), 100);
        Assertions.assertEquals(game.getHighestLevel(), 1999);
        Assertions.assertEquals(game.getUsername(), "asadd");
    }

    @Test
    public void equal1() {
        Game game = new Game("sd",1,2);
        Assertions.assertEquals(game,game);
    }

    @Test
    public void equal2() {
        Game game = new Game("sd",0,2);
        Game game2 = new Game("sd",1,2);
        Assertions.assertNotEquals(game,game2);
    }

    @Test
    public void equal3() {
        Game game = new Game("sda",1,2);
        Game game2 = new Game("sd",1,2);
        Assertions.assertNotEquals(game,game2);
    }

    @Test
    public void equal4() {
        Game game = new Game("sd",1,2);
        Game game2 = new Game("sd",1,1);
        Assertions.assertNotEquals(game,game2);
    }

    @Test
    public void equal5() {
        Game game = new Game("sda",1,2);
        Assertions.assertNotEquals(game,"sad");
        Assertions.assertNotEquals(game,null);
    }




}
