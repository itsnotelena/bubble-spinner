package server;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

public class DbImplement {

    private transient DbAdapter dbAdapter;

    /**
     * Constructor.
     *
     * @param dbAdapter Object dbAdapter
     */
    public DbImplement(DbAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    public DbAdapter getDbAdapter() {
        return dbAdapter;
    }

    /**
     * User verification.
     *
     * @param details user verification details.
     * @return returns whether the user is in the database.
     */
    public boolean checkLogin(User details) throws SQLException {
        assert details != null;

        System.out.println("verifying User");

        String query = "SELECT password FROM users WHERE username = ?";
        PreparedStatement stat = dbAdapter.getConn().prepareStatement(query);
        stat.setString(1, details.getUsername());
        boolean result = BCrypt.checkpw(details.getPassword(),
                stat.executeQuery().getString(1));
        stat.close();

        return result;
    }


    /**
     * Insert User Details to Database.
     * @param details User Class Object
     * @return the Result Set if the User in inserted
     * @throws SQLException in case of connection failure
     */
    public boolean insertUser(User details) throws SQLException {
        assert details != null;
        if (searchInUsers(details.getUsername())) {
            return false;
        }

        String query = "INSERT INTO users VALUES (?,?,?);";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1, details.getUsername());
        statement.setString(2, details.getEmail());
        statement.setString(3, BCrypt.hashpw(details.getPassword(), BCrypt.gensalt()));
        statement.execute();
        statement.close();

        return searchInUsers(details.getUsername());
    }

    /**
     * Insert the Score class object to the database.
     *
     * @param score using the Score class Object
     * @return true if the object is inserted
     * @throws SQLException in case of connection failure
     */
    public boolean insertScore(Score score) throws SQLException {
        assert score != null;
        String query = "INSERT INTO score VALUES(?,?,?)";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1, score.getUsername());
        statement.setInt(2, score.getScoreW());
        statement.setInt(3, score.getScoreA());
        statement.execute();
        statement.close();

        return searchInScore(score.getUsername());

    }

    /**
     * Insert the badge class object to the database.
     *
     * @param badge using the Badge class Object.
     * @return true if the object is inserted.
     * @throws SQLException in case of connection failure.
     */
    public boolean insertBadge(Badge badge) throws SQLException {
        assert badge != null;

        PreparedStatement statement = dbAdapter
                .getConn()
                .prepareStatement("INSERT INTO badges VALUES(?,?)");
        statement.setString(1, badge.getUsername());
        statement.setString(2, badge.getAward());
        statement.execute();
        statement.close();

        return searchInBadges(badge.getUsername());
    }

    /**
     * Searches for the user in the specified table ("users" OR "score" OR "games") as a string
     * and return true if found or otherwise.
     *
     * @param name set users name needs to be searched using String
     * @param table either users / score / games as string
     * @return boolean if user exists in the specified table
     * @throws SQLException in case of connection failure
     */
    private boolean searchUser(String name, String table) throws SQLException {
        assert name != null;
        String query = "SELECT username FROM " + table + "  WHERE username = ?";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1,name);
        boolean result = statement.executeQuery().next();
        statement.close();
        return result;
    }

    /**
     * search inside the users table.
     * @param name String to search for
     * @return if it exists or not
     */
    public boolean searchInUsers(String name) {
        try {
            return searchUser(name, "users");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * search inside the score table.
     * @param name String to search for
     * @return if it exists or not
     */
    public boolean searchInScore(String name) {
        try {
            return searchUser(name, "score");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * search inside the game table.
     * @param name String to search for
     * @return if it exists or not
     */
    public boolean searchInGame(String name) {
        try {
            return searchUser(name, "games");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * search inside the badge table.
     * @param name String to search for.
     * @return if it exists or not.
     */
    public boolean searchInBadges(String name) {
        try {
            return searchUser(name, "badges");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Insert Game Object to the database.
     *
     * @param game using Game class object
     * @return true if it is added to the table or otherwise
     * @throws SQLException in case of connection failure
     */
    public boolean insertGame(Game game) throws SQLException {
        assert game != null;

        String query = "INSERT INTO games VALUES(?,?,?,?)";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1,game.getUsername());
        statement.setInt(2,game.getGamesPlayed());
        statement.setInt(3,game.getHighestLevel());
        statement.setString(4,game.getAward());
        statement.execute();
        statement.close();

        return searchInGame(game.getUsername());
    }

    /**
     * Deletes the user from the tables specified
     * either (score or users) as a string.
     *
     * @param username username which needs to be deleted
     * @param table the table from which the username needs to be deleted
     * @return true if it is removed or otherwise
     * @throws SQLException in case of connection failure
     */
    private boolean removeUser(String username, String table) throws SQLException {
        assert username != null;

        String query = "DELETE FROM " + table + " WHERE username = ? ";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1,username);
        statement.execute();
        statement.close();

        return !searchUser(username,table);

    }

    /**
     * deletes username from Score.
     *
     * @param username String to remove from db
     * @return true if it is removed or otherwise
     */
    public boolean removeFromScore(String username) {
        try {
            return removeUser(username, "score");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * deletes usernname from users.
     *
     * @param username String to remove from db
     * @return true if it is removed or otherwise
     */
    public boolean removeFromUser(String username) {
        try {
            return removeUser(username, "users");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * deletes usernname from games.
     *
     * @param username String to remove from db
     * @return true if it is removed or otherwise
     */
    public boolean removeFromGame(String username) {
        try {
            return removeUser(username, "games");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the Top 5 User's Scores.
     * @return usernames.
     */
    public List<User> getTop5Score() {
        try {
            return getTopXScores(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.DataflowAnomalyAnalysis"})
    // The ResultSet causes pmd violation even though it's safely closed we initialize it as null.
    private List<User> getTopXScores(int amount) throws SQLException {
        ResultSet result = null;
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = dbAdapter.getConn().prepareStatement(
                    "SELECT * FROM score ORDER BY - scoreA LIMIT ?");
            statement.setInt(1,amount);
            result = statement.executeQuery();
            while (result.next()) {
                users.add(getUserByUsername(result.getString(1)).get());
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            dbAdapter.closeData();
            return users;
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    public Optional<Score> getScoreByUser(String username) throws SQLException {
        ResultSet result = null;
        try {
            PreparedStatement statement = dbAdapter
                    .getConn()
                    .prepareStatement("SELECT * FROM score where username = ? ");
            statement.setString(1, username);
            result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new Score(result.getString(1),
                        result.getInt(2),
                        result.getInt(3)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            dbAdapter.closeData();
            return Optional.empty();
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    public Optional<Badge> getBadgeByUser(String username) throws SQLException {
        ResultSet result = null;
        try {
            PreparedStatement statement = dbAdapter
                    .getConn()
                    .prepareStatement("SELECT * FROM badge where username = ? ");
            statement.setString(1, username);
            result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new Badge(result.getString(1),
                        result.getString(2)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            dbAdapter.closeData();
            return Optional.empty();
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * initialize the tables for this db.
     */
    public void initialize() {
        try {
            dbAdapter.importTables();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

