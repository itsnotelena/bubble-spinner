package client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Server;

public class ClientTest {

    @BeforeAll
    public static void before() {
        Server.main(new String[0]);
    }

    @Test
    public void testAuthFail() {
        Assertions.assertThat(new Client().authenticate("test", "test"))
                .isFalse();
    }


    @Test
    public void testAuthPass() {
        Assertions.assertThat(new Client().authenticate("test", "pass"))
                .isTrue();
    }

}
