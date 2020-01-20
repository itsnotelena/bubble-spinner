package client;

import java.io.FileNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Server;
import server.User;


public class ClientNormalTest {

    private transient Server server = new Server();

    /**
     * Sets up the environment for each test.
     */
    @Before
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
    @After
    public void end() {
        server.stop();
    }

    @Test
    public void testNormally() {
        Assertions.assertThat(new Client().authenticate(new User(null,null,null))).isFalse();
    }
}
