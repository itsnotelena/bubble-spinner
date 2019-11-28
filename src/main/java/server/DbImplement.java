package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        String query = "SELECT username FROM users WHERE username = ?  AND password = ?";
        PreparedStatement stat = dbAdapter.getConn().prepareStatement(query);
        stat.setString(1, details.getUsername());
        stat.setString(2, details.getPassword());

        return stat.executeQuery().next();
    }

    /**
     * Insert User Details to Database.
     * @param details User Class Object
     * @return the Result Set if the User in inserted
     * @throws SQLException in case of connection failure
     */
    public boolean insertUser(User details) throws SQLException {
        assert details != null;
        assert !searchUser(details.getUsername(),"users");

        String query = "INSERT INTO users VALUES (?,?,?);";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1, details.getUsername());
        statement.setString(2, details.getEmail());
        statement.setString(3, details.getPassword());
        statement.execute();

        return searchUser(details.getUsername(), "users");

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

        return searchUser(score.getUsername(), "score");

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
    public boolean searchUser(String name, String table) throws SQLException {
        assert name != null;
        String query = "SELECT username FROM " + table + "  WHERE username = ?";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1,name);
        return statement.executeQuery().next();
    }

    /**
     * By passing the Specified column name either as String
     * ("scoreW" or "scoreA") this method will return the top 5
     * entries of type Score class in descending order.
     *
     * @param column either scoreW or scoreA
     * @return List of top 5 entries in descending order of type Score class
     * @throws SQLException in case of connection failure
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    //Warning suppressed because to fix it a more severe one is created.
    public final List<Score> getTopFiveScore(String column) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM score ORDER BY " + column + " DESC LIMIT 5";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        result = statement.executeQuery();
        List<Score> res = new ArrayList<>(5);

        while (result.next()) {
            res.add(new Score(result
                    .getString(1), result
                    .getInt(2), result
                    .getInt(3)));
        }
        result.close();
        return res;
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

        return searchUser(game.getUsername(), "games");
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
    public boolean removeUser(String username, String table) throws SQLException {
        assert username != null;

        String query = "DELETE FROM " + table + " WHERE username = ? ";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1,username);
        statement.execute();

        return !searchUser(username,table);

    }

}

