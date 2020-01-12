package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.sqlite.SQLiteDataSource;

/**
 * Adapter class for the Database.
 * It handles the Connection and the creation for
 * the SQLite database.
 */
public class DbAdapter {

    /**
     * Using SQLiteDataSource to improve test coverage instead
     * of the static methods from DriverManager that are not
     * easily testable.
     */
    private transient SQLiteDataSource dataSource;

    /**
     * Class Constructor.
     * @param name The name of the database.
     */
    public DbAdapter(String[] name) {
        //try {
        //    Class.forName("org.sqlite.jdbc");
        //} catch (ClassNotFoundException ignored) {
        //    ignored.printStackTrace();
        //}
        String jdbUrl = "jdbc:sqlite:assets/db/" + name[0] + ".db";
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(jdbUrl);
        //();

        System.out.println("database connection established for db : " + name[0]);
    }


    /**
     * Get the Connection object to the database.
     * @return a Connection object if the database is online, null otherwise.
     */
    public Connection getConn() throws SQLException {
        return dataSource.getConnection();

    }

    /**
     * Import all the tables into the database
     * if they weren't imported yet.
     */
    public void importTables() {
        try (Scanner scanner = new Scanner(new File("assets/db/schema.sql"))
                .useDelimiter(";")) {
            try (Statement stmt = getConn().createStatement()) {

                while (scanner.hasNext()) {
                    try {
                        stmt.execute(scanner.next());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Closing and clean up the connection with Database.
     */
    public final void closeData() throws SQLException {
        if (getConn() != null) {
            try {
                getConn().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clear the Database.
     */
    public void clearData() {
        String[] tables  = new String[]{
            "users",
            "score",
            "games",
            "badges"
        };
        for (int i = 0; i < tables.length; i++) {

            try (PreparedStatement statement = getConn().prepareStatement(
                    "DELETE FROM " + tables[i] + ";") ) {
                statement.executeUpdate();
                statement.close();
                System.out.println("here everything is deleted from " + tables[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

