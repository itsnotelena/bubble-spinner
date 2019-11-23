package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query = "SELECT * FROM users WHERE username = ?  AND password = ?";
        PreparedStatement stat = dbAdapter.getConn().prepareStatement(query);
        stat.setString(1, details.getUsername());
        stat.setString(2, details.getPassword());

        return stat.executeQuery().next();
    }


    /**
     * Insert User Details to Database.
     * @param details User Class Object
     * @return the Result Set if the User in inserted
     * @throws SQLException for the PreparedStatement if an error occurs
     */
    public ResultSet insertUser(User details) throws SQLException {
        assert details != null;

        String query = "INSERT INTO users VALUES (?,?,?);";
        PreparedStatement statement;
        statement = dbAdapter.getConn().prepareStatement(query);
        statement.setString(1, details.getUsername());
        statement.setString(2, details.getEmail());
        statement.setString(3, details.getPassword());

        return statement.executeQuery();
    }
}

