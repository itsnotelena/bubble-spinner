package server;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbImplement {

    private static DbImplement dbImplementInstance;
    private transient DbAdapter dbAdapter;

    /**
     * Constructor.
     *
     * @param dbAdapter Object dbAdapter
     */
    public DbImplement(DbAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    /**
     * getInstance of the current class.
     *
     * @return DBImplement Object
     */
    public static DbImplement getInstance() {
        if (dbImplementInstance == null) {
            dbImplementInstance = new DbImplement(DbAdapter.getInstance());
        }

        return dbImplementInstance;
    }


    /**
     * Insertion of a new Account to the db.
     *
     * @param details User which will  be the user details
     * @return boolean if it was added
     */
    public boolean insertUser(User details) {

        System.out.println("Adding user " + details.getUsername());

        String query = "INSERT INTO usario VALUES (?,?,?);";
        PreparedStatement statment;
        try {
            statment = dbAdapter.getConn().prepareStatement(query);
            statment.setString(1, details.getEmail());
            statment.setString(2, details.getUsername());
            statment.setString(3, details.getPassword());
            statment.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    /**
     * User verification.
     *
     * @param details user verification details.
     * @return returns whether the user is in the database.
     */
    public boolean checkLogin(User details) {

        System.out.println("Verfying User Details");
        String query = "SELECT * FROM usario WHERE username = ?  AND password = ? ";

        try {
            PreparedStatement stat = dbAdapter.getConn().prepareStatement(query);
            stat.setString(1, details.getUsername());
            stat.setString(2, details.getPassword());
            return stat.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

