package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Database {
    private Connection connection = null;

    //Database Methods
    /**
     * Construtor for Database.
     * @param name as a parameter.
     */
    public Database(String name) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + name + ".db");
            createSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating the Schema of the database.
     */
    public void createSchema() {
        try {
            Statement schema = connection.createStatement();
            schema.executeUpdate("create table if not exists users"
                    + "(username String not null constraint USER_pk primary key, "
                    + "email String not null, password String not null,score int not null, "
                    + "bombs int not null, level int not null)");
            schema.executeUpdate(
                    "create unique index if not exists USER_email_uindex on users (email)");
            schema.executeUpdate(
                    "create unique index if not exists USER_username_uindex on users (username)");
            schema.close();
        } catch (SQLException e) {
            e.printStackTrace();
            closeData();
        }
    }

    /**
     * Closing and clean up the connection with Database.
     */
    public void closeData() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clear the Database.
     */
    public void clearData() throws SQLException {

        PreparedStatement statement = connection.prepareStatement("DELETE FROM users");
        statement.executeUpdate();
        statement.close();
        closeData();

    }

    //Query Methods
    /**
     Add User to Table.
     */
    public boolean addUser(User1 user) {
        Optional<User1> optionalUser = getUserByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            return false;
        }

        try {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT into users values(?,?,?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getPassword());
            statement.setInt(4,user.getScore());
            statement.setInt(5,user.getBombs());
            statement.setInt(6,user.getLevel());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeData();
            return false;
        }
    }

    /**
     * Returns all the usernames in the database.
     * @param username as a parameter.
     * @return
     */
    public Optional<User1> getUserByUsername(String username) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM users where username = ?");
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new User1(result.getString(1),
                                    result.getString(2),
                                    result.getString(3),
                                    result.getInt(4),
                                    result.getInt(5),
                                    result.getInt(6)));
            } else {
                result.close();
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            closeData();
            return Optional.empty();
        }
    }

    /**
     * Returns the connection.
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Set the connection.
     * @param connection as a parameter.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}