package client;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;
import server.User;


public class ClientNormalTest {

    private transient Server server = new Server();

    @BeforeEach
    void start() throws FileNotFoundException, SQLException {
        server.main(new String[]{"database"});
        try {
            server.schemaCreate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testNormally() {
        Assertions.assertThat(new Client().authenticate(new User(null,null,null))).isFalse();
    }
}
