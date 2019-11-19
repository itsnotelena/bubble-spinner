package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAdapter {

    private static DbAdapter dbAdapter_instance;

    private transient Connection conn;


    /**
     * Class Constructor.
     */
    private DbAdapter() {

        //try {
        //    Class.forName("org.postgresql.jdbc4");
        //} catch (ClassNotFoundException ignored) {
        //    ignored.printStackTrace();
        //}

        try {
            String jdbUrl = "jdbc:postgresql://localhost:5432/BubbleSpinner";
            conn = DriverManager.getConnection(jdbUrl, "postgres", "1234");

            System.out.println("database connection established");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * Getter for the whole class.
     *
     * @return DbAdapter
     */
    public static DbAdapter getInstance() {
        if (dbAdapter_instance == null) {
            dbAdapter_instance = new DbAdapter();
        }
        return dbAdapter_instance;
    }

    public Connection getConn() {
        return conn;
    }
}
