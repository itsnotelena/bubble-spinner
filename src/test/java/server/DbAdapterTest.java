package server;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DbAdapterTest {

    /**
     * Simple test to check the database creation happens successfully.
     */
    @Test
    public void testDatabaseConnection() {
        DbAdapter dba = new DbAdapter();
        Assertions.assertThat(dba.getConn()).isNotNull();
    }

    /**
     * Testing the table import method.
     */
    @Test
    public void testImportTables() {
        DbAdapter db = new DbAdapter();
        Assertions.assertThat(db.importTables()).isTrue();
    }

}
