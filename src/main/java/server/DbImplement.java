package server;

import config.Config;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;


public class DbImplement {

    private transient DbAdapter dbAdapter;
    private transient CalendarWrapper calendar;
    private transient DbImplementGet dbImplementGet;

    /**
     * Constructor.
     *
     * @param dbAdapter Object dbAdapter
     */
    public DbImplement(DbAdapter dbAdapter) {
        this.calendar = new CalendarWrapper();
        this.dbAdapter = dbAdapter;
        this.dbImplementGet = new DbImplementGet(dbAdapter);
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
        if (searchInUsers(details.getUsername())) {
            return false;
        }

        String query = "INSERT INTO users VALUES (?,?,?);";
        PreparedStatement statement = dbAdapter.getConn().prepareStatement(query);
        PreparedStatement statement1 = dbAdapter.getConn()
                .prepareStatement("INSERT INTO score VALUES(?,0,0)");
        statement1.setString(1,details.getUsername());
        statement.setString(1, details.getUsername());
        statement.setString(2, details.getEmail());
        statement.setString(3, BCrypt.hashpw(details.getPassword(), BCrypt.gensalt()));
        statement.execute();
        statement.close();
        statement1.execute();
        statement1.close();

        insertGame(new Game(details.getUsername(),0,0));

        return searchInUsers(details.getUsername());
    }

    /**
     * Insert the badge class object to the database.
     *
     * @param badge using the Badge class Object.
     * @return true if the object is inserted.
     * @throws SQLException in case of connection failure.
     */
    public boolean insertBadge(Badge badge) throws SQLException {
        PreparedStatement statement = dbAdapter
                .getConn()
                .prepareStatement("INSERT INTO badges VALUES(?,?)");
        statement.setString(1, badge.getUsername());
        statement.setString(2, badge.getAward().getText());
        statement.execute();
        statement.close();

        return searchInBadges(badge.getUsername());
    }

    /**
     * Insert the Score class object to the database.
     *
     * @param score using the Score class Object
     * @return true if the object is inserted
     * @throws SQLException in case of connection failure
     */
    public boolean addScoreAndIncrementGames(Score score) throws SQLException {
        Score result = dbImplementGet.getScoreByUser(score.getUsername());
        int scoreWeek = result.getHighestWeekScore();
        int total = result.getScoreA() + score.getScoreA();
        if (score.getHighestWeekScore() > scoreWeek) {
            scoreWeek = score.getHighestWeekScore();
        }
        PreparedStatement statement = dbAdapter
                .getConn()
                .prepareStatement("UPDATE score "
                        + "SET scoreA = ? ,highestWeekScore = ?  "
                        + "WHERE username = ? ");
        statement.setInt(1,total);
        statement.setInt(2,scoreWeek);
        statement.setString(3,score.getUsername());
        statement.execute();
        statement.close();
        incrementGames(score.getUsername());
        return true;
    }

    private void incrementGames(String username) throws SQLException {

        int game  = dbImplementGet.getGameByUser(username).getGamesPlayed() + 1;
        try (PreparedStatement preparedStatement = dbAdapter.getConn()
                .prepareStatement("UPDATE games SET gamesP = ? WHERE username = ?")) {
            preparedStatement.setString(2,username);
            preparedStatement.setInt(1,game);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Searches for the user in the specified table ("users" OR "score" OR "games") as a string
     * and return true if found or otherwise.
     *
     * @param name set users name needs to be searched using String
     * @param table either users / score / games / badges as string
     * @return boolean if user exists in the specified table
     * @throws SQLException in case of connection failure
     */
    private boolean searchUser(String name, String table) {
        try (PreparedStatement statement = dbAdapter.getConn()
                .prepareStatement("SELECT username FROM " + table + "  WHERE username = ?")) {
            statement.setString(1,name);
            boolean result =  statement.executeQuery().next();
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * search inside the users table.
     * @param name String to search for
     * @return if it exists or not
     */
    public boolean searchInUsers(String name) {
        return searchUser(name, "users");
    }

    /**
     * search inside the score table.
     * @param name String to search for
     * @return if it exists or not
     */
    public boolean searchInScore(String name) {
        return searchUser(name, "score");
    }

    /**
     * search inside the game table.
     * @param name String to search for
     * @return if it exists or not
     */
    public boolean searchInGame(String name) {
        return searchUser(name, "games");
    }

    /**
     * search inside the badge table.
     * @param name String to search for.
     * @return if it exists or not.
     */
    public boolean searchInBadges(String name) {
        return searchUser(name, "badges");
    }

    /**
     * Insert Game Object to the database.
     *
     * @param game using Game class object
     * @return true if it is added to the table or otherwise
     * @throws SQLException in case of connection failure
     */
    public boolean insertGame(Game game) throws SQLException {
        PreparedStatement statement = dbAdapter.getConn()
                .prepareStatement("INSERT INTO games VALUES(?,?,?)");
        statement.setString(1,game.getUsername());
        statement.setInt(2,game.getGamesPlayed());
        statement.setInt(3,game.getHighestLevel());
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
    private boolean removeUser(String username, String table) {
        try (PreparedStatement statement = dbAdapter.getConn()
                .prepareStatement("DELETE FROM " + table + " WHERE username = ? ")) {
            statement.setString(1,username);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * deletes username from Score.
     *
     * @param username String to remove from db
     * @return true if it is removed or otherwise
     */
    public boolean removeFromScore(String username) {
        return removeUser(username, "score");
    }

    /**
     * deletes username from Badges.
     *
     * @param username String to remove from db.
     * @return true if it is removed or otherwise.
     */
    public boolean removeFromBadge(String username) {
        return removeUser(username, "badges");
    }

    /**
     * deletes usernname from users.
     *
     * @param username String to remove from db
     * @return true if it is removed or otherwise
     */
    public boolean removeFromUser(String username) {
        return removeUser(username, "users");
    }

    /**
     * initialize the tables for this db.
     */
    public void initialize() {
        try {
            dbAdapter.importTables();
            this.checkTimeToChange();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * check if the server needs to restart the weekly scores.
     */
    public boolean checkTimeToChange() {
        calendar.setTime(new Date());
        if (!Config.Time.NeedToBeRestarted) {
            if (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                itsMonday();
                Config.Time.NeedToBeRestarted = true;
                return true;
            } else {
                Config.Time.NeedToBeRestarted = false;
            }
        }
        return false;
    }

    /**
     * restarts the weekly scores.
     */
    private void itsMonday() {
        try (PreparedStatement statement = dbAdapter.getConn().prepareStatement("UPDATE score "
                + "SET scoreA = 0 ")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCalendar(CalendarWrapper calendar) {
        this.calendar = calendar;
    }

    public DbImplementGet getDbImplementGet() {
        return dbImplementGet;
    }
}

