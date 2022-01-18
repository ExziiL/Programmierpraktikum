package GUI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadGameController implements Initializable {

    @FXML
    ListView<String> saveGames;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> games =  FXCollections.observableArrayList();
        games.add("Game 1");
        games.add("Game 2");


        saveGames.setItems(games);

    }
}
