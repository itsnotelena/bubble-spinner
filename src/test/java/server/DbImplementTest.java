package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;




public class DbImplementTest {

    private transient DbImplement dbImplement;
    private transient ResultSet mockedSet;


    /**
     * SetUp proper environment before each test.
     *
     * @throws SQLException throws SQL Exception in case
     *                      something goes from the connection class
     */
    @BeforeEach
    public void setUp() throws SQLException {
        mockedSet = Mockito.mock(ResultSet.class);
        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenReturn(Mockito.mock(PreparedStatement.class));

    }

    @AfterEach
    void clean(){

    }


    @Test
    void insertUserAndCheckLogin() {
        User a = new User("LOL", "aaa", "aas");
        try {
            BDDMockito.given(mockedSet.next()).willReturn(true);
            Mockito.when(dbImplement.insertUser(a)).thenReturn(mockedSet);
            Assertions.assertThat(dbImplement.checkLogin(a)).isTrue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void insertNullUser() {
        Assertions.assertThatThrownBy(() -> dbImplement.insertUser(null))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void checkNullUser() {
        Assertions.assertThatThrownBy(() -> dbImplement.checkLogin(null))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void catchSqlExceptionCheckLogin() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenThrow(Mockito.mock(SQLException.class));

        Assertions.assertThatThrownBy(() -> dbImplement.checkLogin(new User("LOL", "aaa", "aas")))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void catchSqlExceptionInsertUser() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenThrow(Mockito.mock(SQLException.class));

        Assertions.assertThatThrownBy(() -> dbImplement.insertUser(new User("LOL", "aaa", "aas")))
                .isInstanceOf(SQLException.class);
    }


}
