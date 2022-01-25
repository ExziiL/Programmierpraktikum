package GUI.Controller;

import GUI.Game;
import Logic.DocumentWriter.DocumentWriter;
import Logic.main.LogicConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class LoadGameController implements Initializable {

    @FXML
    ListView<HBox> saveGames;

    @FXML
    ImageView arrowIconLoadGame;

    Image delete = new Image("assets/Icons/delete.png");
    Image reload = new Image("assets/Icons/reload.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<HBox> games = FXCollections.observableArrayList();

        ArrayList<String> files = Game.logicController.getAllSaveFiles();


        for (String s : files) {
            HBox left = new HBox();
            HBox right = new HBox();
            HBox line = new HBox();
            // Set Label with Filename
            Label text = new Label(s);
            left.getChildren().add(text);

            //Add Icons for Processing
            ImageView viewDelete = new ImageView(delete);

            viewDelete.setFitHeight(20);
            viewDelete.setFitWidth(20);

            right.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    deleteGame();
                }
            });

            left.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    loadGame();
                }
            });

            right.getChildren().add(viewDelete);
            right.setMaxWidth(20);
            right.setMinWidth(20);
            right.setSpacing(20);
            right.setAlignment(Pos.TOP_RIGHT);

            left.setMaxWidth(560);
            left.setMinWidth(560);
            line.getChildren().addAll(left, right);
            games.add(line);
        }

        saveGames.setItems(games);
    }


    @FXML
    public void handleBack() throws IOException {
        Game.showStartGameWindow();
    }

    private void loadGame() {
        try {
            String s = getSelectedText();
            Game.logicController.setWriter(new DocumentWriter(s));
            Game.logicController.loadGame();

            if (Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE) {
                Game.showPopUpReconnect();
            } else {
                Game.showPlayingFieldWindow();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteGame() {
        String s = getSelectedText();

        if (Game.logicController.deleteFile(s)) {
            ObservableList<HBox> selectedGame = saveGames.getSelectionModel().getSelectedItems();
            saveGames.getItems().remove(selectedGame.get(0));
        }

        saveGames.refresh();
    }

    private String getSelectedText() {

        ObservableList<HBox> selectedGame = saveGames.getSelectionModel().getSelectedItems();
        ObservableList<Node> children = selectedGame.get(0).getChildren();
        Node left = children.get(0);
        if (left.getClass() == HBox.class) {
            HBox leftBox = (HBox) left;
            ObservableList<Node> leftBoxChildren = leftBox.getChildren();

            for (Node e : leftBoxChildren) {
                if (e.getClass() == Label.class) {
                    Label t = (Label) e;
                    return t.getText();
                }
            }
        }
        return null;
    }
}
