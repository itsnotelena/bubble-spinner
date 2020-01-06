package game;

public class GameSettings {

    private transient boolean computerPlayer;
    private transient int level;
    private transient boolean infinite;
    private transient int difficulty;


    public GameSettings(GameSettingsBuilder gameSettingsBuilder) {
        this.computerPlayer = gameSettingsBuilder.computerPlayer;
        this.level = gameSettingsBuilder.level;
        this.infinite = gameSettingsBuilder.infinite;
        this.difficulty = gameSettingsBuilder.difficulty;
    }

    public boolean isComputerPlayer() {
        return this.computerPlayer;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void incrementLevel() {
        this.level++;
    }

    public static class GameSettingsBuilder {

        private transient boolean computerPlayer;
        private transient int level;
        private transient boolean infinite;
        private transient int difficulty;

        public GameSettingsBuilder() {

        }

        public GameSettingsBuilder withComputerPlayer(boolean computerPlayer) {
            this.computerPlayer = computerPlayer;
            return this;
        }

        public GameSettingsBuilder withLevel(int level) {
            this.level = level;
            return this;
        }

        public GameSettingsBuilder withInfinite(boolean isInfinite) {
            this.infinite = isInfinite;
            return this;
        }

        public GameSettingsBuilder withDifficulty(int difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public GameSettings build() {
            return new GameSettings(this);
        }
    }
}
