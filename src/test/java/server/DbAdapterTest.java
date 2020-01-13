package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.sqlite.SQLiteDataSource;

public class DbAdapterTest {

    /**
     * Simple test to check the database creation happens successfully.
     */
    @Test
    public void testDatabaseConnection() throws SQLException {
        DbAdapter dba = new DbAdapter(new String[]{"test"});
        Assertions.assertThat(dba.getConn()).isNotNull();
    }

    @Test
    void testConnFails() throws SQLException {
        DbAdapter dbAdapter = new DbAdapter(new String[]{"test"});

        SQLiteDataSource dataSource = Mockito.mock(SQLiteDataSource.class);
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        dbAdapter.setDataSource(dataSource);
        Assertions.assertThatThrownBy(() -> dbAdapter.getConn())
                .isInstanceOf(SQLException.class);
    }

    @Test
    void nullInClosingData() throws SQLException {
        DbAdapter dba = Mockito.mock(DbAdapter.class);
        Mockito.when(dba.getConn()).thenReturn(null);
        dba.closeData();
    }

    @Test
    void noNullInClosingData() throws SQLException {
        DbAdapter dba = new DbAdapter(new String[]{"test"});
        dba.closeData();
    }
}
