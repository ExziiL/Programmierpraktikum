package GUI.Controller;

import GUI.Game;
import Logic.main.LogicConstants;
import Network.Client;
import Network.Network;
import Network.Server;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class GameSettingsController {

    //region FXML-Variables
    @FXML
    private Slider slider;
    @FXML
    private Button connect;
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
    private Text IpAdresseText;
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
    @FXML
    private Pane boxGameSize;
    @FXML
    private VBox boxTwo;
    @FXML
    private VBox boxThree;
    @FXML
    private VBox boxFour;
    @FXML
    private VBox boxFive;
    //endregion

    //region Variables
    private int gameSize;
    private Thread networkThread = null;
    protected Network netplay;
    private boolean connected = false;
    private boolean serverCreated = false;
    private boolean clientCreated = false;
    //endregion

    /**
     * Initializes the Settings Window with all programmed Parts
     */
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
        Ip.setPromptText("IP-Adresse eingeben");

        IpAdresseText.opacityProperty().setValue(0.3);

        gameMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameMode.getValue().equals("Offline")) {
                    BoxOnline.setDisable(true);
                    disableGameSizeControls(false);
                    IpAdresseText.opacityProperty().setValue(0.3);
                    slider.setDisable(false);
                } else if (gameMode.getValue().equals("Online")) {
                    BoxOnline.setDisable(false);
                    IpAdresseText.opacityProperty().setValue(1);
                    if (Client.isSelected()) {
                        selectClient();

                        networkThread = new Thread(() -> {
                            netplay = Network.chooseNetworkTyp(false);
                        });
                        networkThread.start();
                    } else {
                        selectServer();
                        networkThread = new Thread(() -> {
                            netplay = Network.chooseNetworkTyp(true);
                            if (!(netplay instanceof Server))
                                selectServer();
                            Platform.runLater(() -> {
                                Ip.setText(((Server) netplay).getIp());
                            });
                        });
                        networkThread.start();
                    }
                }

            }
        });

        gameMode.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
            }
        });

        gameMode.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
            }
        });

        Client.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectClient();
                Network.closeNetwork(Network.getNetplay());
                if (networkThread.isAlive()) {
                    networkThread.stop();
                }
                networkThread = new Thread(() -> {
                    netplay = Network.chooseNetworkTyp(false);
                    if (!(netplay instanceof Client))
                        selectClient();
                });
                networkThread.start();
            }
        });

        Client.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
            }
        });

        Client.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
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
                    netplay = Network.chooseNetworkTyp(true);
                    if (!(netplay instanceof Server))
                        selectServer();
                    Platform.runLater(() -> {
                        Ip.setText(((Server) netplay).getIp());
                    });
                });
                networkThread.start();
            }
        });
        // ------------------------------- Slider ---------------------------------
        Server.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
            }
        });

        Server.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSize = newValue.intValue();
            setGameSize();
        });

        slider.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
            }
        });

        slider.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
            }
        });

        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ErrorMessage.setText("Warte auf Verbindung...");
                ErrorMessage.setStyle("-fx-text-fill: grey");

                networkThread = new Thread(() -> {
                    if (netplay instanceof Server) {
                        if (!serverCreated) {
                            serverCreated = true;
                            connected = ((Server) netplay).createServer();
                        }

                    } else if (netplay instanceof Client) {
                        if (!clientCreated) {
                            clientCreated = true;
                            connected = ((Client) netplay).createClient(Ip.getText());
                        }
                    }
                    Platform.runLater(() -> {
                        if (connected) {
                            ErrorMessage.setText("Verbunden");
                            ErrorMessage.setStyle("-fx-text-fill: green");
                        } else if (clientCreated || serverCreated) {
                            ErrorMessage.setText("Warte auf Verbindung...");
                            ErrorMessage.setStyle("-fx-text-fill: grey");
                        } else {
                            ErrorMessage.setText("Verbindung fehlgeschlagen");
                            ErrorMessage.setStyle("-fx-text-fill: red");
                            Network.closeNetwork(netplay);

                        }
                    });
                });
                networkThread.start();
            }
        });

        connect.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
            }
        });

        connect.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
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


    // ------------------------------- Next-Button ---------------------------------

    /**
     * Sets the Variables, which are determined on the Settings Window and calls the Game-Class to show the Placing Window
     *
     * @param event is fired if the Mouse clicked the Arrow in the lower right Corner
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    void handleNext(MouseEvent event) throws IOException, InterruptedException {
        Game.logicController.setGameSize(gameSize);
        Game.logicController.createEnemyGame(gameSize);
        Game.logicController.setGameMode(determineGameMode());
        Game.logicController.setEnemyGameGameMode(determineGameMode());
        Game.logicController.createWriter();

        if (gameMode.getValue().equals("Online")) {
            ErrorMessage.setText("Warte auf Spieler...");
            ErrorMessage.setStyle("-fx-text-fill: green");
            // Controller for Network
            Network.setController(Game.logicController);

            networkThread = new Thread(() -> {
                // int[] i = {2, 2, 2, 3, 3, 4};
                if (netplay instanceof Server) {
                    connected = ((Server) netplay).sendInitialisation(Game.logicController.getGameSize(),
                            setNetworkShip());

                }
                if (netplay instanceof Client) {
                    connected = ((Client) netplay).receiveMessage();
                }

                Platform.runLater(() -> {
                    try {

                        if (!connected) {
                            ErrorMessage.setText("Nicht Verbunden");
                            ErrorMessage.setStyle("-fx-text-fill: red");

                        } else {
                            Game.showPlacingFieldWindow();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
            networkThread.start();

        } else {
            ErrorMessage.setText("");
            Game.logicController.determineNumberOfShips();
            Game.showPlacingFieldWindow();
        }

    }

    /**
     * Sets the Labels, that a showing the count of each Ship-variant
     */
    private void setLabelTexts() {
        labelTwo.setText(Game.logicController.getCountTwoShip() + "x");
        labelThree.setText(Game.logicController.getCountThreeShip() + "x");
        labelFour.setText(Game.logicController.getCountFourShip() + "x");
        labelFive.setText(Game.logicController.getCountFiveShip() + "x");
    }

    /**
     * Determines the Gamemode of the Game, on which is chosen in the ComboBox
     *
     * @return the Gamemode
     */
    private LogicConstants.GameMode determineGameMode() {

        if (gameMode.getValue().equals("Online")) {
            return LogicConstants.GameMode.ONLINE;
        }
        return LogicConstants.GameMode.OFFLINE;
    }

    /**
     * If Client is selected, sets the TextBox editable to enter an IP-Adresse-String
     */
    private void selectClient() {
        Ip.setEditable(true);
        Ip.setText("");
        disableGameSizeControls(true);

        ErrorMessage.setText("");

        if (serverCreated == true) {
            Network.closeNetwork(Network.getNetplay());
            serverCreated = false;
        }

    }

    /**
     * If Server is selected, sets the TextBox not editable and displays the IP-Adresse
     */
    private void selectServer() {
        try {
            InetAddress realIP = InetAddress.getLocalHost();
            Ip.setEditable(false);
            Ip.setText(realIP.getHostAddress());
            disableGameSizeControls(false);
            ErrorMessage.setText("");

            if (clientCreated == true) {
                Network.closeNetwork(Network.getNetplay());
                clientCreated = false;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the Size of the Game-fields for the following Windows
     */
    private void setGameSize() {
        Game.logicController.setGameSize(gameSize);
        Game.logicController.determineNumberOfShips();
        setLabelTexts();
        slider.setValue(gameSize);
        labelGameFieldSize.setText("" + gameSize);
    }

    /**
     * Generates the String of the Ships, that are used in a Network-Game
     *
     * @return a String of each Shipsize that is used
     */
    private String setNetworkShip() {

        // int two = Game.logicController.getAllTwoShips();
        int two = Game.logicController.getAllTwoShips();
        int three = Game.logicController.getAllThreeShips();
        int four = Game.logicController.getAllFourShips();
        int five = Game.logicController.getAllFiveShips();
        String s = "";

        for (int i = 0; i < two; i++) {
            s += " 2";
        }
        for (int i = 0; i < three; i++) {
            s += " 3";
        }
        for (int i = 0; i < four; i++) {
            s += " 4";
        }
        for (int i = 0; i < five; i++) {
            s += " 5";
        }

        return s;
    }

    /**
     * Disable/ Able all Controls for Changing the Game Size
     *
     * @param disable true if Controls should be disabled
     */
    private void disableGameSizeControls(boolean disable) {
        boxGameSize.setDisable(disable);
        boxTwo.setDisable(disable);
        boxThree.setDisable(disable);
        boxFour.setDisable(disable);
        boxFive.setDisable(disable);
        if (!disable) {
            boxGameSize.setStyle("-fx-opacity: 1");
            boxTwo.setStyle("-fx-opacity: 1;");
            boxThree.setStyle("-fx-opacity: 1;");
            boxFour.setStyle("-fx-opacity: 1;");
            boxFive.setStyle("-fx-opacity: 1;");
        } else {
            boxGameSize.setStyle("-fx-opacity: 0.3");
            boxTwo.setStyle("-fx-opacity: 0.3;");
            boxThree.setStyle("-fx-opacity: 0.3;");
            boxFour.setStyle("-fx-opacity: 0.3;");
            boxFive.setStyle("-fx-opacity: 0.3;");

        }
    }
}