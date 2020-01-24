package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is only responsible to get data from the db.
 */
public class DbImplementGet {

    private transient DbAdapter dbAdapter;

    public DbImplementGet(DbAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    /**
     * Get the Top 5 User's Scores.
     * @return usernames.
     */
    public List<Score> getTop5Score() {
        try {
            return getTopXScores(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.DataflowAnomalyAnalysis"})
    // The ResultSet causes pmd violation even though it's safely closed we initialize it as null.
    private List<Score> getTopXScores(int amount) throws SQLException {
        ResultSet result = null;
        List<Score> users = new ArrayList<>();
        try {
            PreparedStatement statement = dbAdapter.getConn().prepareStatement(
                    "SELECT * FROM score ORDER BY - scoreA LIMIT ?");
            statement.setInt(1,amount);
            result = statement.executeQuery();
            while (result.next()) {
                users.add(new Score(result.getString(1),
                        result.getInt(2),result.getInt(3)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            dbAdapter.closeData();
            return users;
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    /**
     * Returns all the usernames in the database.
     * @param username as a parameter.
     * @return Optional User
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    // The ResultSet causes pmd violation even though it's safely closed we initialize it as null.
    public Optional<User> getUserByUsername(String username) throws SQLException {
        ResultSet result = null;
        try {
            PreparedStatement statement =
                    dbAdapter.getConn().prepareStatement("SELECT * FROM users where username = ?");
            statement.setString(1,username);
            result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new User(result.getString(1),
                        result.getString(2),
                        result.getString(3)));
            } else {
                result.close();
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dbAdapter.closeData();
            return Optional.empty();
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    /**
     * Get the score by Username.
     * @param username as a parameter.
     * @return Score.
     * @throws SQLException if no score.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    // The ResultSet causes pmd violation even though it's safely closed we initialize it as null.
    public Score getScoreByUser(String username) throws SQLException {
        ResultSet result = null;
        try {
            PreparedStatement statement = dbAdapter
                    .getConn()
                    .prepareStatement("SELECT * FROM score where username = ?");
            statement.setString(1, username);
            result = statement.executeQuery();
            if (result.next()) {
                return new Score(result.getString(1),
                        result.getInt(2),
                        result.getInt(3));
            } else {
                return new Score("",0,0);
            }
        } catch (SQLException e) {
            return new Score("",0,0);
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }


    /**
     * Get the score by Username.
     * @param username as a parameter.
     * @return Score.
     * @throws SQLException if no score.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    // The ResultSet causes pmd violation even though it's safely closed we initialize it as null.
    public Game getGameByUser(String username) throws SQLException {
        ResultSet result = null;
        try {
            PreparedStatement statement = dbAdapter
                    .getConn()
                    .prepareStatement("SELECT * FROM games where username = ?");
            statement.setString(1, username);
            result = statement.executeQuery();
            if (result.next()) {
                return new Game(result.getString(1),
                        result.getInt(2),
                        result.getInt(3));
            } else {
                return new Game("",0,0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Game("",0,0);
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    /**
     * Get badges by username.
     * @param username as a parameter.
     * @return Badge.
     * @throws SQLException if no badge.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    // The ResultSet causes pmd violation even though it's safely closed we initialize it as null.
    public ArrayList<Badge> getBadgeByUser(String username) throws SQLException {
        ResultSet result = null;
        ArrayList<Badge> output = new ArrayList<>();
        try {
            PreparedStatement statement = dbAdapter
                    .getConn()
                    .prepareStatement("SELECT * FROM badges where username = ? ");
            statement.setString(1, username);
            result = statement.executeQuery();
            while (result.next()) {
                output.add(new Badge(result.getString("username"),
                        BadgesEnum.get(result.getString("award"))));
            }
            return output;
        } catch (SQLException e) {
            e.printStackTrace();
            dbAdapter.closeData();
            return output;
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }
}
