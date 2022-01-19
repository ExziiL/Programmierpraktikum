package GUI.Controller;

import GUI.Game;
import Logic.main.LogicConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static GUI.GUIConstants.errorMessageNoIP;

public class GameSettingsController {
    @FXML
    private Slider slider;
    @FXML
    private Button next;
    @FXML
    private TextField name;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;
    @FXML
    private Text labelGameFieldSize;
    @FXML
    private Label ErrorMessage;
    @FXML
    private Pane BoxOnline;
    @FXML
    private TextField Ip;
    @FXML
    private RadioButton Server, Client;
    @FXML
    private ComboBox<String> gameMode;

    private int gameSize;

    @FXML
    void initialize() {
        if (Game.logicController.getGameSize() >= 5) {
            gameSize = Game.logicController.getGameSize();
        } else {
            gameSize = 5;
        }
        setGameSize();

        setLabelTexts();

        ObservableList<String> options = FXCollections.observableArrayList("Offline", "Online");
        gameMode.setItems(options);
        gameMode.setValue("Offline");

        BoxOnline.setDisable(true);
        Ip.setPromptText("IP-Adresse");

        gameMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameMode.getValue().equals("Offline")) {
                    BoxOnline.setDisable(true);
                } else if (gameMode.getValue().equals("Online")) {
                    BoxOnline.setDisable(false);
                    if (Client.isSelected()) {
                        selectClient();
                    } else {
                        selectServer();
                    }
                }
            }
        });

        Client.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectClient();
            }
        });

        Server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectServer();
            }
        });
        // ------------------------------- Slider ---------------------------------
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSize = newValue.intValue();
            setGameSize();
        });

    }

    // ------------------------------- Zurück-Button ------------------------------
    @FXML
    void handleBack(MouseEvent event) throws IOException {
        Game.showStartGameWindow();
    }

    // ------------------------------- Name-Input ---------------------------------
    @FXML
    void handleInputName(ActionEvent event) throws IOException {
    }

    // ------------------------------- Next-Button ---------------------------------

    @FXML
    void handleNext(MouseEvent event) throws IOException {
        if (gameMode.getValue().equals("Online") && Client.isSelected() && Ip.getText().isEmpty()) {
            ErrorMessage.setText(errorMessageNoIP);
        } else {
            ErrorMessage.setText("");
            Game.logicController.setName(name.getCharacters().toString());
            Game.logicController.setGameSize(gameSize);
            Game.logicController.setGameMode(determineGameMode());
            Game.showPlacingFieldWindow();
        }
    }

    private void setLabelTexts() {
        labelTwo.setText(Game.logicController.getCountTwoShip() + "x");
        labelThree.setText(Game.logicController.getCountThreeShip() + "x");
        labelFour.setText(Game.logicController.getCountFourShip() + "x");
        labelFive.setText(Game.logicController.getCountFiveShip() + "x");
    }

    private LogicConstants.GameMode determineGameMode() {

        if (gameMode.getSelectionModel().equals("Online")) {
            return LogicConstants.GameMode.ONLINE;
        }
        return LogicConstants.GameMode.OFFLINE;
    }

    private void selectClient() {
        Ip.setDisable(false);
        Ip.setText("");
    }

    private void selectServer() {
        try {
            InetAddress realIP = InetAddress.getLocalHost();
            Ip.setDisable(true);
            Ip.setText(realIP.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void setGameSize() {
        Game.logicController.setGameSize(gameSize);
        setLabelTexts();
        slider.setValue(gameSize);
        labelGameFieldSize.setText("" + gameSize);
    }
}
