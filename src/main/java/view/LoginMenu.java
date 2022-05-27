package view;

import controller.Controller;
import controller.LoginMenuController;
import enums.Message;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenu extends Menu implements Initializable {

    private final LoginMenuController controller = new LoginMenuController();

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ImageView continueAsGuest;
    @FXML
    private ImageView forbiddenIcon;
    @FXML
    private ImageView checkIcon;
    @FXML
    private Text message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(continueAsGuest, new Tooltip("Continue as a guest!"));
        checkIcon.setVisible(false);
        forbiddenIcon.setVisible(false);
        message.setText("");

        continueAsGuest.setOnMouseEntered(event -> {
            continueAsGuest.setOpacity(0.5);
            continueAsGuest.setCursor(Cursor.HAND);
        });

        continueAsGuest.setOnMouseExited(event -> continueAsGuest.setOpacity(1));
        username.setOnKeyReleased(event -> {
            if (username.getText().matches("^\\w+.*")) {
                username.setStyle("-fx-border-color: green");
            } else if (username.getText().length() != 0) {
                username.setStyle("-fx-border-color: red");
            } else {
                username.setStyle("-fx-border-color: #c948fa;");
            }
        });

        password.setOnKeyReleased(event -> {
            if (controller.isStrong(password.getText())) {
                password.setStyle("-fx-border-color: green");
            } else if (password.getText().length() != 0) {
                password.setStyle("-fx-border-color: red");
            } else {
                password.setStyle("-fx-border-color: #c948fa;");
            }
        });
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/loginMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void login() {
        setup(username);
        Message loginResult = controller.login(username.getText(), password.getText());
        if (loginResult != Message.SUCCESS) {
            message.setFill(Paint.valueOf("red"));
            forbiddenIcon.setVisible(true);
            message.setText(loginResult.toString());
        } else {
            forbiddenIcon.setVisible(false);
            checkIcon.setVisible(true);
            message.setOpacity(1);
            checkIcon.setOpacity(1);
            message.setFill(Paint.valueOf("green"));
            message.setText("Login successful! Wait...");
            nextPage();
        }
    }

    public void signUp() {
        setup(username);
        Message signupResult = controller.signUp(username.getText(), password.getText());
        if (signupResult != Message.SUCCESS) {
            message.setFill(Paint.valueOf("red"));
            forbiddenIcon.setVisible(true);
            message.setText(signupResult.toString());
        } else {
            forbiddenIcon.setVisible(false);
            checkIcon.setVisible(true);
            message.setOpacity(1);
            checkIcon.setOpacity(1);
            message.setFill(Paint.valueOf("green"));
            message.setText("SignUp successful! Wait...");
            nextPage();
        }
    }

    public void continueAsGuest() {
        setup(username);
        controller.continueAsGuest();
        forbiddenIcon.setVisible(false);
        checkIcon.setVisible(true);
        message.setOpacity(1);
        checkIcon.setOpacity(1);
        message.setFill(Paint.valueOf("green"));
        message.setText("Going to Main Menu! Wait...");
        nextPage();
    }

    private void nextPage() {
        FadeTransition messageFade = new FadeTransition(Duration.millis(2000), message);
        messageFade.setByValue(1);
        messageFade.setToValue(0);
        FadeTransition checkIconFade = new FadeTransition(Duration.millis(2000), checkIcon);
        checkIconFade.setByValue(1);
        checkIconFade.setToValue(0);
        messageFade.setOnFinished(e -> {
            window.setScene(Controller.getMainMenu().getScene());
        });
        checkIconFade.play();
        messageFade.play();
    }
}
