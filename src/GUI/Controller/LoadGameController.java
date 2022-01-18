package GUI.Controller;

import GUI.Game;
import Logic.DocumentWriter.DocumentWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadGameController implements Initializable {

    @FXML
    ListView<String> saveGames;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> games = FXCollections.observableArrayList();

        ArrayList<String> files = Game.logicController.getAllSaveFiles();

        for (String s : files) {
            games.add(s);
        }

        saveGames.setItems(games);



        saveGames.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY){
                    ObservableList<String> selectedGame = saveGames.getSelectionModel().getSelectedItems();
                    String s = selectedGame.get(0);

                    Game.logicController.setWriter(new DocumentWriter(s));
                    Game.logicController.loadGame();
                    try {
                        Game.showPlayingFieldWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
