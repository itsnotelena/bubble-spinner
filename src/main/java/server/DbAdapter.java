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
    private transient String[] tables  = new String[]{
        "users",
        "score",
        "games",
        "badges"
    };

    /**
     * Class Constructor.
     * @param name The name of the database.
     */
    public DbAdapter(String name) {
        //try {
        //    Class.forName("org.sqlite.jdbc");
        //} catch (ClassNotFoundException ignored) {
        //    ignored.printStackTrace();
        //}
        String jdbUrl = "jdbc:sqlite:assets/db/" + name + ".db";
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(jdbUrl);
        //();

        System.out.println("database connection established for db : " + name);
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
    public void importTables() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("assets/db/schema.sql"))
                .useDelimiter(";");
        try (Statement stmt = getConn().createStatement()) {
            while (scanner.hasNext()) {
                stmt.execute(scanner.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Closing and clean up the connection with Database.
     */
    public final void closeData() throws SQLException {
        if (getConn() != null) {
            getConn().close();
        }
    }

    /**
     * Clear the Database.
     */
    @SuppressWarnings("PMD")
    //Explanation : suppressed warning on the variable name in the loop (UR anomaly) !!
    public void clearData() throws SQLException {
        for (String name : tables) {
            PreparedStatement statement = getConn().prepareStatement(
                    "DELETE FROM " + name + ";");
            statement.executeUpdate();
            statement.close();
            System.out.println("here everything is deleted from " + name);
        }
    }


    /**
     * sets the datasource with SQLiteDataSource.
     *
     * @param dataSource SQLiteDataSource
     */
    public void setDataSource(SQLiteDataSource dataSource) {
        this.dataSource = dataSource;
    }
}

