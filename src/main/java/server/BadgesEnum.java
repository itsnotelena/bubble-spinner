package server;

import java.util.HashMap;
import java.util.Map;

public enum BadgesEnum {
    Badge_Flawless_Victory("Badge_Flawless_Victory"),
    Badge_Gamer("Badge_Gamer"),
    Badge_LEGEND("Badge_LEGEND"),
    Badge_Live_Die("Badge_Live_Die"),
    starterBadge("starterBadge");

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
