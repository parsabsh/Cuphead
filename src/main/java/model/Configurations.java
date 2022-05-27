package model;

import enums.Difficulty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Configurations {
    public static Difficulty difficulty = Difficulty.MEDIUM;

    public static final int BOSS_INITIAL_LIVES = 100;

    public static final int SPEED_OF_CUP_HEAD = 15;

    public static final int SHOOTING_SPEED = 10;

    private static final BooleanProperty musicOn = new SimpleBooleanProperty(true);

    public static boolean isMusicOn() {
        return musicOn.get();
    }

    public static BooleanProperty musicOnProperty() {
        return musicOn;
    }

    public static void setMusicOn(boolean musicOn) {
        Configurations.musicOn.set(musicOn);
    }
}
