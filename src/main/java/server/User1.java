package server;

import java.util.Objects;

public class User1 {
    private String username;
    private String email;
    private String password;
    private int score;
    private int bombs;
    private int level;

    /**
     * Contructor of User1 class.
     * @param username parameter.
     * @param email parameter.
     * @param password parameter.
     * @param score parameter.
     * @param bombs parameter.
     * @param level parameter.
     */
    public User1(String username, String email, String password, int score, int bombs, int level) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = score;
        this.bombs = bombs;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return username.equals(user.getUsername())
                || email.equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", score=" + score
                + ", bombs=" + bombs
                + ", level=" + level
                + '}';
    }
}
