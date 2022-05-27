package controller;

import enums.Message;
import model.Player;

public class LoginMenuController {

    public Message login(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            return Message.EMPTY_FIELDS;
        }
        if (Player.getPlayerByUsername(username) == null || !Player.getPlayerByUsername(username).getPassword().equals(password)) {
            return Message.LOGIN_ERROR;
        }
        Controller.setLoggedInPlayer(Player.getPlayerByUsername(username));
        return Message.SUCCESS;
    }

    public Message signUp(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            return Message.EMPTY_FIELDS;
        }
        if (!isStrong(password)) {
            return Message.WEAK_PASSWORD;
        }
        if (Player.getPlayerByUsername(username) != null) {
            return Message.USERNAME_EXISTS;
        }
        Player.addPlayer(username, password);
        Controller.setLoggedInPlayer(Player.getPlayerByUsername(username));
        return Message.SUCCESS;
    }

    public void continueAsGuest() {
        Controller.setLoggedInPlayer(null);
    }

    public boolean isStrong(String password) {
        return password.length() >= 6 && password.matches(".*\\w+.*") && password.matches(".*\\d+.*");
    }
}
