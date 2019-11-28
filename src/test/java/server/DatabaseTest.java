package server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    private transient Database db;
    private transient User1 user = new User1("mickey", "mickey@mouse", "abc", 2,3,1);

    @BeforeEach
    public void buildEnvironment() throws SQLException {
        db = new Database("Test");
        db.setConnection(db.getConnection());
    }

    @AfterEach
    public void close() {
        db.closeData();
    }

    @Test
    public void addUser() {
        boolean result = db.addUser(user);
        assertTrue(result);
    }

    @Test
    public void addExistUsername() {
        db.addUser(user);
        boolean result2 = db.addUser(new User1("mickey", "different@mouse", "abc", 2,3,1));
        assertFalse(result2);
    }

    @Test
    public void addExistingEmail() {
        db.addUser(user);

        User1 fakeUser = new User1("different", "mickey@mouse", "lol", 3, 3, 1);
        assertFalse(db.addUser(fakeUser));
    }

    @Test
    public void addNonExistingEmail() {
        db.addUser(user);

        User1 fakeUser = new User1("hola", "minnie@mouse", "lol", 3, 3, 1);
        assertTrue(db.addUser(fakeUser));
    }

    @Test
    public void getUserByUsernameTesting() {
        db.addUser(user);
        assertTrue(user.getUsername().equals("mickey"));
    }

}
