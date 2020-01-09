package server;

import java.util.Objects;

public class Badge {

    private String username;
    private String award;

    /**
     * Constructor.
     * @param username set username using string.
     * @param award set the badge.
     */
    public Badge(String username, String award) {
        this.username = username;
        this.award = award;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Badge badge = (Badge) o;
        return award == badge.award
                && username.equals(badge.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, award);
    }
}
