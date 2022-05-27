package view;

import controller.Controller;
import enums.Difficulty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Configurations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSettingsMenu extends Menu implements Initializable {

    @FXML
    private ComboBox<Difficulty> difficultyComboBox;
    @FXML
    private ImageView musicOn;
    @FXML
    private ImageView musicOff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficultyComboBox.getItems().add(Difficulty.EASY);
        difficultyComboBox.getItems().add(Difficulty.MEDIUM);
        difficultyComboBox.getItems().add(Difficulty.HARD);
        difficultyComboBox.getSelectionModel().select(Configurations.difficulty);
        musicOff.visibleProperty().bind(Configurations.musicOnProperty().not());
        musicOff.disableProperty().bind(Configurations.musicOnProperty());
        musicOn.visibleProperty().bind(Configurations.musicOnProperty());
        musicOn.disableProperty().bind(Configurations.musicOnProperty().not());
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/gameSettingsMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void openMainMenu() {
        setup(difficultyComboBox);
        window.setScene(Controller.getMainMenu().getScene());
        Controller.getGameSettings().setScene(null);
    }

    public void changeDifficulty() {
        Configurations.difficulty = difficultyComboBox.getSelectionModel().getSelectedItem();
    }

    public void startGame() {
        setup(difficultyComboBox);
        window.setScene(Controller.getGameMenu().getScene());
        this.setScene(null);
    }

    public void mute() {
        Configurations.setMusicOn(false);
        Controller.musicClip.stop();
    }

    public void unmute() {
        Configurations.setMusicOn(true);
        Controller.musicClip.start();
    }
}
