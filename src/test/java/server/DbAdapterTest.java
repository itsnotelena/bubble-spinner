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
    public void testDatabaseConnection() throws SQLException, FileNotFoundException {
        DbAdapter dba = new DbAdapter("database");
        Assertions.assertThat(dba.getConn()).isNotNull();
    }

    /**
     * Testing the table import method.
     */
    @Test
    public void testImportTables() throws FileNotFoundException, SQLException {
        DbAdapter db = new DbAdapter("database");
        Assertions.assertThat(db.importTables()).isTrue();
    }

}
