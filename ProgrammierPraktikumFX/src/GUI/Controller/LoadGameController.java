package GUI.Controller;

import GUI.Game;
import Logic.DocumentWriter.DocumentWriter;
import Network.Network;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadGameController implements Initializable {

    //region FXML-VAriables
    @FXML
    private ListView<HBox> saveGames;
    @FXML
    private Button refresh;
    @FXML
    private Button joinGame;
    @FXML
    private ImageView arrowIconLoadGame;
    //endregion

    //region Variables
    ArrayList<String> files;
    ArrayList<String> onlineFiles;
    Image delete = new Image("assets/Icons/trash.png");
    Image reload = new Image("assets/Icons/reload.png");
    //endregion

    /**
     * Initializes the Components in the LoadGame Window
     * @param location is not used
     * @param resources is not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.setController(Game.logicController);
        refresh();
        saveGames.requestFocus();

        joinGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Game.showPopUpReconnect("", false);

            }
        });

        refresh.setOnMouseClicked(event -> {
            refresh();
        });
    }

    @FXML
    public void handleBack() throws IOException {
        Game.showStartGameWindow();
    }

    /**
     * Selects between saving an Online or Offline Game and creating a Writer to
     * load a Game accordingly. Next it shows either to PlayingField Window (offline)
     * or the ReconnectPopUp.
     */
    private void loadGame() {
        try {
            InetAddress realIP = InetAddress.getLocalHost();
            String s = getSelectedText();

            if (s.contains("|Online")) {
                s = s.replace("|Online", "");

                Game.logicController.setWriter(new DocumentWriter(s, true));
                Platform.runLater(() -> {
                    Game.showPopUpReconnect(realIP.getHostAddress(), true);
                });

            } else {
                Game.logicController.setWriter(new DocumentWriter(s, false));
                Game.logicController.loadGame();
                Game.showPlayingFieldWindow();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes a selected File in the LoadGame and refreshes the Window
     */
    private void deleteGame() {
        String s = getSelectedText();
        s += ".txt";
        s = s.replace("|Online", "");
        if (files.contains(s)) {

            if (Game.logicController.deleteFile(s)) {
                ObservableList<HBox> selectedGame = saveGames.getSelectionModel().getSelectedItems();
                saveGames.getItems().remove(selectedGame.get(0));
            }

        } else if (onlineFiles.contains(s)) {

            if (Game.logicController.deleteOnlineFile(s)) {
                ObservableList<HBox> selectedGame = saveGames.getSelectionModel().getSelectedItems();
                saveGames.getItems().remove(selectedGame.get(0));
            }
        }

        saveGames.refresh();
    }

    /**
     * get Selected Line of The List View and gets the Text
     * @return the String of the Filename
     */
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

    /**
     * Method to refresh the LoadGame Window when a File is deleted
     * or other Attributs of a File has changes
     */
    private void refresh() {
        games = saveGames.getItems();
        games.clear();
        files = Game.logicController.getAllSaveFiles();
        onlineFiles = Game.logicController.getAllOnlineSaveFiles();

        for (String s : files) {
            s = s.replace(".txt", "");
            games.add(addLine(s));
        }

        for (String s : onlineFiles) {
            s = s.replace(".txt", "");
            s += "|Online";
            games.add(addLine(s));
        }

        saveGames.setItems(games);
        saveGames.requestFocus();

    }

    /**
     * Edits the Filename to displayed in the List of the LoadGame Window
     * and implements Methods to delete the File or load the saved Game.
     * @param s String of the File, how it is saved in the Directories
     * @return a HBox that is added to the List
     */
    private HBox addLine(String s) {
        HBox left = new HBox();
        HBox right = new HBox();

        HBox line = new HBox();
        // Set Label with Filename
        s.replace(".txt", "");
        Label text = new Label(s);
        left.getChildren().add(text);

        // Add Icons for Processing
        ImageView viewDelete = new ImageView(delete);

        viewDelete.setFitHeight(20);
        viewDelete.setFitWidth(20);

        right.getChildren().add(viewDelete);
        right.setMaxWidth(20);
        right.setMinWidth(20);
        right.setMinHeight(25);
        right.setAlignment(Pos.CENTER_LEFT);

        left.setMaxWidth(460);
        left.setMinWidth(460);
        left.setMinHeight(25);
        left.setAlignment(Pos.CENTER_LEFT);

        line.getChildren().addAll(left, right);

        right.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteGame();
            }
        });
        line.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                line.setStyle("-fx-cursor: hand;");
            }
        });
        line.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                line.setStyle("-fx-cursor: default;");
            }
        });

        left.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadGame();
            }
        });

        return line;
    }

}
