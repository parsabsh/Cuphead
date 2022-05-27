package controller;

import javafx.collections.ObservableList;
import model.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardController {

    public void loadSortedPlayers(ObservableList<Player> rawList) {
        ArrayList<Player> players = Player.getPlayers();
        Collections.sort(players);
        rawList.addAll(players);
    }

}
