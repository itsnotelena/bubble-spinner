package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DbAdapterTest {

    /**
     * Simple test to check the database creation happens successfully.
     */
    @Test
    public void testDatabaseConnection() throws SQLException {
        DbAdapter dba = new DbAdapter("test");
        Assertions.assertThat(dba.getConn()).isNotNull();
    }

    /**
     * Testing the table import method.
     */
    @Test
    public void testImportTables() throws FileNotFoundException {
        DbAdapter db = new DbAdapter("test");
        Assertions.assertThat(db.importTables()).isTrue();
    }

}
