package server;

import java.util.Objects;

public class Badge {

    private String username;
    private BadgesEnum award;

    public Badge() {

    }

    /**
     * Constructor.
     * @param username set username using string.
     * @param award set the badge.
     */
    public Badge(String username, BadgesEnum award) {
        this.username = username;
        this.award = award;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BadgesEnum getAward() {
        return award;
    }

    public void setAward(BadgesEnum award) {
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
        return award.equals(badge.award)
                && username.equals(badge.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, award);
    }
}
