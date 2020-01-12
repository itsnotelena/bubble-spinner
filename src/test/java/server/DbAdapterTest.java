package server;

import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DbAdapterTest {

    /**
     * Simple test to check the database creation happens successfully.
     */
    @Test
    public void testDatabaseConnection() throws SQLException {
        DbAdapter dba = new DbAdapter(new String[]{"test"});
        Assertions.assertThat(dba.getConn()).isNotNull();
    }

}
