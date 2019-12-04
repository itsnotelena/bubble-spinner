package client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Server;
import server.User;

public class ClientTest {

    @BeforeAll
    public static void before() {
        String[] args = {"test"};
        Server.main(args);
    }

    @Test
    void login() {
        User a = new User("bla","uk","uk");
        Assertions.assertThat(new Client().register(a)).isTrue();
        Assertions.assertThat(new Client().authenticate(a)).isTrue();
        new Client().removeUser("bla");
    }

    @Test
    void loginFail() {
        Assertions.assertThat(new Client().authenticate(new User("doesnt",
                "exit","ever"))).isFalse();
    }


}
