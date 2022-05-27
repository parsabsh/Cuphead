package view;

import controller.Controller;
import controller.ProfileMenuController;
import enums.Avatar;
import enums.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenu extends Menu implements Initializable {
    private final ProfileMenuController controller = new ProfileMenuController();

    @FXML
    private ListView<ImageView> listOfAvatars;
    @FXML
    private ImageView avatar;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorText;
    @FXML
    private Label password;
    @FXML
    private Label username;
    @FXML
    private ImageView deleteAccount;
    @FXML
    private ImageView logout;
    @FXML
    private Button saveChangesButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(deleteAccount, new Tooltip("Delete Account"));
        Tooltip.install(logout, new Tooltip("Logout"));
        for (Avatar avatar : Avatar.values()) {
            if (avatar != Avatar.GUEST_AVATAR) listOfAvatars.getItems().add(new ImageView(avatar.image));
        }
        avatar.imageProperty().bind(Controller.getLoggedInPlayer().avatarProperty());
        password.textProperty().bind(Controller.getLoggedInPlayer().passwordProperty());
        username.textProperty().bind(Controller.getLoggedInPlayer().usernameProperty());
        saveChangesButton.setDisable(true);

        passwordField.setOnKeyReleased(event -> {
            if (controller.isStrong(passwordField.getText())) {
                passwordField.setStyle("-fx-border-color: green");
            } else if (passwordField.getText().length() != 0) {
                passwordField.setStyle("-fx-border-color: red");
            } else {
                passwordField.setStyle("-fx-border-color: #c948fa;");
            }
            saveChangesButton.setDisable(passwordField.getText().isEmpty() && usernameField.getText().isEmpty());
        });

        usernameField.setOnKeyReleased(event -> {
            if (usernameField.getText().matches("^\\w+.*")) {
                usernameField.setStyle("-fx-border-color: green");
            } else if (usernameField.getText().length() != 0) {
                usernameField.setStyle("-fx-border-color: red");
            } else {
                usernameField.setStyle("-fx-border-color: #c948fa;");
            }
            saveChangesButton.setDisable(passwordField.getText().isEmpty() && usernameField.getText().isEmpty());
        });
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/profileMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void openMainMenu() {
        setup(avatar);
        window.setScene(Controller.getMainMenu().getScene());
    }

    public void deleteAccount() {
        controller.deleteCurrentPlayerAccount();
        logout();
    }

    public void logout() {
        controller.logout();
        setup(avatar);
        Controller.getLoginMenu().setScene(null);
        window.setScene(Controller.getLoginMenu().getScene());
        Controller.getMainMenu().setScene(null);
        Controller.getProfileMenu().setScene(null);
    }

    public void submitChanges() {
        Message changeResult = controller.submitChanges(usernameField.getText(), passwordField.getText());
        if (changeResult == Message.SUCCESS) {
            errorText.setOpacity(0);
            passwordField.clear();
            usernameField.clear();
            usernameField.setStyle("-fx-border-color: #c948fa;");
            passwordField.setStyle("-fx-border-color: #c948fa;");
        } else {
            errorText.setText(changeResult.toString());
            errorText.setOpacity(1);
        }
        saveChangesButton.setDisable(passwordField.getText().isEmpty() && usernameField.getText().isEmpty());
    }

    public void changeAvatar() {
        Controller.getLoggedInPlayer().setAvatar(Avatar.values()[listOfAvatars.getSelectionModel().getSelectedIndex()]);
        saveChangesButton.setDisable(passwordField.getText().isEmpty() && usernameField.getText().isEmpty());
    }
}
