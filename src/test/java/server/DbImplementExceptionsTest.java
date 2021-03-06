package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class DbImplementExceptionsTest {

    private transient DbImplement dbImplement;
    private transient String test = "test";

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
    public void catchSqlExceptionCheckLogin() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenThrow(Mockito.mock(SQLException.class));

        Assertions.assertThatThrownBy(() -> dbImplement.checkLogin(new User("LOL", "aaa", "aas")))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void catchSqlExceptionInsertUser() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenThrow(Mockito.mock(SQLException.class));

        Assertions.assertThatThrownBy(() -> dbImplement.insertUser(new User("LOL", "aaa", "aas")))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void errorGettingTop5() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        List<Score> list = dbImplement.getDbImplementGet().getTop5Score();
        Assertions.assertThat(list.isEmpty()).isTrue();
    }

    @Test
    public void errorGettingScoreByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThat(dbImplement.getDbImplementGet().getScoreByUser("lol"))
                .isEqualTo(new Score("",0,0));
    }

    @Test
    public void errorRemovingUser() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThat(dbImplement.removeFromBadge("a")).isFalse();
    }

    @Test
    public void errorGettingUserByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getDbImplementGet()
                .getUserByUsername("baba"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void exceptionByGettingTopXScores() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        List<Score> list = dbImplement.getDbImplementGet().getTop5Score();
        Assertions.assertThatThrownBy(() -> dbImplement.getDbAdapter().getConn().close())
                .isInstanceOf(SQLException.class);
        Assertions.assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void notGettingUserByUsername() throws SQLException {
        dbImplement = new DbImplement(new DbAdapter(test));

        dbImplement.removeFromUser("baka");
        Optional<User> optional = dbImplement.getDbImplementGet().getUserByUsername("baka");
        Assertions.assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    void notGettingGameByUsername() throws SQLException {
        dbImplement = new DbImplement(new DbAdapter(test));

        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
    }

    @Test
    void emptyScoreByGettingScoreByUser() throws SQLException {
        dbImplement = new DbImplement(new DbAdapter(test));

        Score optional = dbImplement.getDbImplementGet().getScoreByUser("naruto");
        Assert.assertEquals(optional,new Score("",0,0));
    }

    @Test
    void emptyBadgesByGettingBadgesByUser() throws SQLException {
        dbImplement = new DbImplement(new DbAdapter(test));

        dbImplement.removeFromUser("elena");
        List optional = dbImplement.getDbImplementGet().getBadgeByUser("elena");
        Assertions.assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    public void errorGettingBadgeByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getDbImplementGet().getBadgeByUser("lol"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void errorGettingGameByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());
        Assertions.assertThat(dbImplement.getDbImplementGet()
                .getGameByUser("asd"))
                .isEqualTo(new Game("",0,0));
    }
}
