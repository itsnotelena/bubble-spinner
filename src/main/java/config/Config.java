package config;

public class Config {

    public static class Game {
        public static final String TITLE = "Bubble Spinner";
        public static final int WIDTH = 1280;
        public static final int HEIGHT = 720;
        public static final String ICON = "assets/icon.png";
        public static int GAME_TIME = 600;
        public static int BUBBLE_SIZE = 64;
    }

    public static class Time {
        public static final int DEFAULT = 600;
        public static final int MINUTE = 60;
        public static final int HOUR = 3600;
        public static boolean NeedToBeRestarted = false;
    }

    public static class HelpBox {
        public static final String[] text = {
            "Press right or left arrow to navigate this menu or C to close it.",
            "At the top of the screen you can see there are 5 balls of which the first one is"
                    + " the one you are allowed to use. You can shoot it in any direction as long"
                    + " as it points towards the bottom of the screen.",
            "The central structure is the hexagon containing the bubbles you need to shoot and"
                    + " in order to win the game. You can use the aiming arrow line to adjust your"
                    + " shots to be perfectly aligned with your target.",
            "If the bubble shot hits the hexagon and 3 or more bubbles of the same color are"
                    + " connected together they will pop and you will get points for it.",
            "If you miss the hexagon structure you will lose points but you will not lose points"
                    + " if you don't hit a bubble of he same color.",
            "In order to win the game you need to pop all the bubbles from the hexagon structure.",
            "Have fun!"
        };
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
