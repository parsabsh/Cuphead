package controller;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Player;
import view.*;

import javax.sound.sampled.*;
import java.io.IOException;

public class Controller {
    private static Player loggedInPlayer = null;
    private static Stage window = null;
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ProfileMenu profileMenu = new ProfileMenu();
    private static GameMenu gameMenu = new GameMenu();
    private static final ScoreBoard scoreBoard = new ScoreBoard();
    private static final GameSettingsMenu gameSettings = new GameSettingsMenu();
    private static final WinGameMenu winMenu = new WinGameMenu();
    private static final LoseGameMenu loseMenu = new LoseGameMenu();
    public static Clip musicClip;

    public Controller(Stage stage) {
        window = stage;
        window.setResizable(false);
        try {
            AudioInputStream menusMusicStream = AudioSystem.getAudioInputStream(Controller.class.getResource("/musics/The_Dave_Brubeck_Quartet_Take_Five.wav"));
            musicClip = AudioSystem.getClip();
            musicClip.open(menusMusicStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        window.setScene(loginMenu.getScene());
        window.setTitle("Cup Head");
        window.getIcons().add(new Image(getClass().getResource("/frames/images/blue.png").toExternalForm()));
        musicClip.start();
        window.show();
    }

    public static GameMenu getGameMenu() {
        return gameMenu;
    }

    public static LoginMenu getLoginMenu() {
        return loginMenu;
    }

    public static MainMenu getMainMenu() {
        return mainMenu;
    }

    public static ProfileMenu getProfileMenu() {
        return profileMenu;
    }

    public static ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public static GameSettingsMenu getGameSettings() {
        return gameSettings;
    }

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        Controller.loggedInPlayer = loggedInPlayer;
    }

    public static WinGameMenu getWinMenu() {
        return winMenu;
    }

    public static LoseGameMenu getLoseMenu() {
        return loseMenu;
    }

    public static void newGameMenu() {
        gameMenu = new GameMenu();
    }

    public static Stage getWindow() {
        return window;
    }
}
