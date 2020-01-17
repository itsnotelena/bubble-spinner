package server;

import java.util.HashMap;
import java.util.Map;

public enum BadgesEnum {
    Top_Of_The_Week("Top_Of_The_Week"),
    Badge_Gamer("Badge_Gamer"),
    Badge_Legend("Badge_Legend"),
    Badge_Veteran("Badge_Veteran"),
    First_Victory("First_Victory");

    private final String text;

    BadgesEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private static final Map<String,BadgesEnum> lookup = new HashMap<>();

    static {
        for (BadgesEnum badge : BadgesEnum.values()) {
            lookup.put(badge.getText(), badge);
        }
    }

    public static BadgesEnum get(String award) {
        return lookup.get(award);
    }

}
