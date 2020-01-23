package game;

import game.ui.TutorialHelpBox;

public class GameSettings {

    private transient boolean computerPlayer;
    private transient int level;
    private transient boolean infinite;
    private transient int difficulty;
    private transient TutorialHelpBox helpBox;

    /**
     * Constructor for GameSettings which copies the builder instance.
     * @param gameSettingsBuilder builder instance.
     */
    private GameSettings(GameSettingsBuilder gameSettingsBuilder) {
        this.computerPlayer = gameSettingsBuilder.computerPlayer;
        this.level = gameSettingsBuilder.level;
        this.infinite = gameSettingsBuilder.infinite;
        this.difficulty = gameSettingsBuilder.difficulty;
        this.helpBox = gameSettingsBuilder.helpBox;
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

    public TutorialHelpBox getHelpBox() {
        return this.helpBox;
    }

    public void incrementLevel() {
        this.level++;
    }

    public void addHelpBox(TutorialHelpBox helpBox) {
        this.helpBox = helpBox;
    }

    public static class GameSettingsBuilder {

        private transient boolean computerPlayer;
        private transient int level;
        private transient boolean infinite;
        private transient int difficulty;
        private transient TutorialHelpBox helpBox;

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

        public GameSettingsBuilder withHelpBox(TutorialHelpBox helpBox) {
            this.helpBox = helpBox;
            return this;
        }

        public GameSettings build() {
            return new GameSettings(this);
        }
    }
}
