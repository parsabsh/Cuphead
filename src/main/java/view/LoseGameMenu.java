package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoseGameMenu extends Menu implements Initializable {

    @FXML
    private Label message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Controller.getLoggedInPlayer() != null)
            message.setText("Now you got " + Controller.getLoggedInPlayer().getScore() + " scores :(");
    }

    @Override
    public Scene getScene() {
        Controller.newGameMenu();
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/loseGame.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void returnMainMenu() {
        setup(message);
        this.setScene(null);
        window.setScene(Controller.getMainMenu().getScene());
    }

    public void startNewGame() {
        setup(message);
        this.setScene(null);
        window.setScene(Controller.getGameSettings().getScene());
    }
}
