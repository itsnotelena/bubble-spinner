package server;


public class User {

    private String email;
    private String username;
    private String password;

    /**
     * Constructor.
     *
     * @param username set username using string
     * @param email set email using string
     * @param password set password using string
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * getter for the user name.
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for the user password.
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for the user email.
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
