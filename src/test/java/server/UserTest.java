package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void gettersAndSetters() {
        User a = new User("hh", "hoo", "ohh");
        a.setUsername("aa");
        a.setEmail("bla");
        a.setPassword("ola");

        Assertions.assertEquals("aa", a.getUsername());
        Assertions.assertEquals("bla", a.getEmail());
        Assertions.assertEquals("ola", a.getPassword());
    }


}
