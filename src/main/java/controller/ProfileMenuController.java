package controller;

import enums.Message;
import model.Player;

public class ProfileMenuController {

    public void logout() {
        Controller.setLoggedInPlayer(null);
    }

    public void deleteCurrentPlayerAccount() {
        Player.deleteAccountOfLoggedInPlayer();
    }

    public boolean isStrong(String password) {
        return password.length() >= 6 && password.matches(".*\\w+.*") && password.matches(".*\\d+.*");
    }

    public Message submitChanges(String username, String password) {
        if (username.length() != 0) {
            if (Player.getPlayerByUsername(username) != null) {
                return Message.USERNAME_EXISTS;
            }
            Controller.getLoggedInPlayer().setUsername(username);
        }
        if (password.length() != 0) {
            if (!isStrong(password)) {
                return Message.WEAK_PASSWORD;
            }
            Controller.getLoggedInPlayer().setPassword(password);
        }
        return Message.SUCCESS;
    }
}
