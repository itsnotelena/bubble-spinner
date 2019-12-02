package client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Server;

public class ClientTest {

    @BeforeAll
    public static void before() {
        String[] args = {"test"};
        Server.main(args);
    }

    @Test
    public void testAuthFail() {
        Assertions.assertThat(new Client().authenticate("test", "test"))
                .isFalse();
    }
}
