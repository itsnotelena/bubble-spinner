package server;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

    /*@Test
    void testGetConnectionFails() throws SQLException {
        DbAdapter dbAdapter = new DbAdapter(new String[]{"test"});
        SQLiteDataSource dataSource = Mockito.mock(SQLiteDataSource.class);
        Connection conn = Mockito.mock(Connection.class);
        Mockito.when(dataSource.getConnection()).thenReturn(conn);
        Mockito.doThrow(new SQLException())
                .when(conn)
                .close();
        dbAdapter.setDataSource(dataSource);
        Assertions.assertThatThrownBy(() -> dbAdapter.closeData())
                .isInstanceOf(SQLException.class);
    }*/

    /*@Test
    void errorGettingTop5() {
        Mockito.when(getDbAdapter().getConn()).thenThrow(new SQLException());

        List<User> list = dbImplement.getTop5Score();
        Assertions.assertThat(list.isEmpty()).isTrue();
    }*/
}
