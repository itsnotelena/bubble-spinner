package config;

public class Config {

    public static class Game {
        public static final String TITLE = "Bubble Spinner";
        public static final int WIDTH = 1280;
        public static final int HEIGHT = 720;
        public static final String ICON = "assets/icon.png";
        public static int GAME_TIME = 600;
        public static final int BUBBLE_SIZE = 64;
    }

    public static class Time {
        public static final int DEFAULT = 600;
        public static final int MINUTE = 60;
        public static final int HOUR = 3600;
    }

    public static class Api {
        public static final String URL = "http://localhost:8080";
    }

    public static class Bubbles {
        public static final String[] textures = {
            "assets/Bubbles/Blue.png",
            "assets/Bubbles/Green.png",
            "assets/Bubbles/Pink.png",
            "assets/Bubbles/Red.png",
            "assets/Bubbles/Lavender.png",
            "assets/Bubbles/NavyBlue.png"
        };
    }


}
