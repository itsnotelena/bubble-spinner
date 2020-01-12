package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class DbImplementExceptionsTest {

    private transient DbImplement dbImplement;

    /**
     * SetUp proper environment before each test.
     *
     * @throws SQLException throws SQL Exception in case
     *                      something goes from the connection class
     */
    @BeforeEach
    public void setUp() throws SQLException {
        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
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


    @Test
    void searchNull() {
        Assertions.assertThatThrownBy(() -> dbImplement.removeFromUser(null))
                .isInstanceOf(java.lang.AssertionError.class);
    }

    @Test
    void removeNull() {
        Assertions.assertThatThrownBy(() -> dbImplement.removeFromUser(null))
                .isInstanceOf(java.lang.AssertionError.class);
    }

    @Test
    void insertUserNull() {
        Assertions.assertThatThrownBy(() -> dbImplement.insertUser(null))
                .isInstanceOf(java.lang.AssertionError.class);
    }

    @Test
    void insertScoreNull() {
        Assertions.assertThatThrownBy(() -> dbImplement.insertScore(null))
                .isInstanceOf(java.lang.AssertionError.class);
    }

    @Test
    void checkNull() {
        Assertions.assertThatThrownBy(() -> dbImplement.checkLogin(null))
                .isInstanceOf(java.lang.AssertionError.class);
    }

    @Test
    void errorGettingTop5() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        List<User> list = dbImplement.getTop5Score();
        Assertions.assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void errorGettingScoreByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getScoreByUser("lol"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void searchUserFails() {
        Assertions.assertThatThrownBy(() -> dbImplement.searchInScore(null))
                .isInstanceOf(AssertionError.class);
        Assertions.assertThatThrownBy(() -> dbImplement.searchInUsers(null))
                .isInstanceOf(AssertionError.class);
        Assertions.assertThatThrownBy(() -> dbImplement.searchInGame(null))
                .isInstanceOf(AssertionError.class);
    }


    @Test
    void errorGettingUserByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getUserByUsername("baba"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void exceptionByGettingTopXScores() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        List<User> list = dbImplement.getTop5Score();
        Assertions.assertThatThrownBy(() -> dbImplement.getDbAdapter().getConn().close())
                .isInstanceOf(SQLException.class);
        Assertions.assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void notGettingUserByUsername() throws SQLException {
        dbImplement = new DbImplement(new DbAdapter("Test"));

        dbImplement.removeFromUser("baka");
        Optional<User> optional = dbImplement.getUserByUsername("baka");
        Assertions.assertThat(optional.isEmpty()).isTrue();

        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
    }

    @Test
    void emptyScoreByGettingScoreByUser() throws SQLException {
        dbImplement = new DbImplement(new DbAdapter("Test"));

        dbImplement.removeFromUser("naruto");
        Optional<Score> optional = dbImplement.getScoreByUser("naruto");
        Assertions.assertThat(optional.isEmpty()).isTrue();

        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
    }

}
