package GUI.Controller;

import GUI.Game;
import Network.*;
import Logic.main.LogicConstants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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
    private Label ErrorMessage;
    @FXML
    private HBox BoxOnline;
    @FXML
    private TextField Ip;
    @FXML
    private RadioButton Server, Client;
    @FXML
    private ComboBox<String> gameMode;


    private int gameSize;
    private Thread networkThread = null;
    Network player;


    @FXML
    void initialize() {
        if (Game.logicController.getGameSize() >= 5) {
            gameSize = Game.logicController.getGameSize();
        } else {
            gameSize = 5;
        }
        Game.logicController.setGameSize(gameSize);
        slider.setValue(gameSize);
        setLabelTexts();


        ObservableList<String> options = FXCollections.observableArrayList("Offline", "Online", "???");
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
                        networkThread = new Thread(() -> {
                            player = Network.chooseNetworkTyp(false, Ip.getText());
                        });
                        networkThread.start();
                    } else {
                        networkThread = new Thread(() -> {
                            player = Network.chooseNetworkTyp(true, "");
                            Platform.runLater(() -> {
                                if (player instanceof Server) {
                                    Ip.setText(((Server) player).getIp());
                                }
                            });
                        });
                        networkThread.start();
                    }
                }

            }
        });

        Client.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectClient();
                if (networkThread.isAlive()) {
                    networkThread.stop();
                }
                networkThread = new Thread(() -> {
                    player = Network.chooseNetworkTyp(false, "");
                });
                networkThread.start();
            }
        });

        Server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectServer();
                if (networkThread.isAlive()) {
                    networkThread.stop();
                }
                networkThread = new Thread(() -> {
                    player = Network.chooseNetworkTyp(true, Ip.getText());
                });
                networkThread.start();
            }
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

    // ------------------------------- Slider ---------------------------------
    @FXML
    void handleSliderChange() throws IOException {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSize = newValue.intValue();
            Game.logicController.setGameSize(gameSize);
            setLabelTexts();
        });
    }

    ;

// ------------------------------- Next-Button ---------------------------------

    @FXML
    void handleNext(MouseEvent event) throws IOException {
        if (gameMode.getValue().equals("Online") && Client.isSelected() && Ip.getText().isEmpty()) {
            ErrorMessage.setText(errorMessageNoIP);
        } else {
            networkThread = new Thread(() -> {

            });
            networkThread.start();
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
}
