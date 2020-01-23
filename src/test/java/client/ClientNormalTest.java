package client;

import java.io.FileNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;
import server.User;


public class ClientNormalTest {

    private transient Server server = new Server();

    /**
     * Sets up the environment for each test.
     */
    @BeforeEach
    public void start() {
        server.main(new String[0]);
        try {
            server.schemaCreate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cleans up everything to start the new tests properly.
     */
    @AfterEach
    public void end() {
        server.stop();
    }

    @Test
    public void testNormally() {
        Assertions.assertThat(new Client().authenticate(new User(null,null,null))).isFalse();
    }
}
