package model;

import controller.Controller;
import enums.Avatar;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player implements Comparable<Player> {
    private static final ArrayList<Player> players = new ArrayList<>();
    private Avatar avatar;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final ObjectProperty<Image> avatarImage = new SimpleObjectProperty<>();

    public Player(String username, String password, int score) {
        setAvatar(Avatar.getRandomAvatar());
        this.username.setValue(username);
        this.password.setValue(password);
        this.score.setValue(score);
    }

    public static void addPlayer(String username, String password) {
        players.add(new Player(username, password, 0));
    }

    public static Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public static void deleteAccountOfLoggedInPlayer() {
        players.remove(Controller.getLoggedInPlayer());
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.setValue(score);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public ObjectProperty<Image> avatarProperty() {
        return avatarImage;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        this.avatarImage.set(avatar.image);
    }

    @Override
    public int compareTo(Player o) {
        Integer myScore = this.getScore();
        Integer otherScore = o.getScore();
        return otherScore.compareTo(myScore);
    }
}
