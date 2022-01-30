package GUI;

import Logic.main.Controller;
import Logic.main.LogicConstants;
import Network.Client;
import Network.Network;
import Network.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {

    //region Variables
    public static Controller logicController;
    private static BorderPane mainLayout;
    private static Stage primaryStage;
    private static Scene scene;

    public static Label endGameText = new Label();
    public static Label connection = new Label();

    private static final Stage dialogSaveGame = new Stage();
    private static Stage dialogStartNewGame = new Stage();
    private static final Stage dialogReconnect = new Stage();
    private static final Stage dialogConnectionClosed = new Stage();
    private static final Stage dialogCannotSave = new Stage();

    private static boolean connectionStable;
    //endregion

    public static void main(String[] args) {
        launch(args);
    }

    public static void toggleCursorHand(boolean hide) {
        if (hide) {
            scene.setCursor(Cursor.HAND);
        } else {
            scene.setCursor(Cursor.DEFAULT);
        }
    }

    public static void toggleCursorGrabHand(boolean hide) {
        if (hide) {
            scene.setCursor(Cursor.CLOSED_HAND);
        } else {
            scene.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Shows the Start Window of the Application
     * @throws IOException
     */
    public static void showStartGameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("StartGame/StartGame.fxml"));
        BorderPane startGame = loader.load();
        mainLayout.setCenter(startGame);
        mainLayout.setStyle("-fx-background-color: #ffffff");
    }

    /**
     * Shows the Settings Window of the Application
     * @throws IOException
     */
    public static void showGameSettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("GameSettings/GameSettings.fxml"));
        AnchorPane gameSettings = loader.load();
        mainLayout.setCenter(gameSettings);
    }

    /**
     * Shows the Placing Field of the Game
     * @throws IOException
     */
    public static void showPlacingFieldWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("PlacingField/PlacingField.fxml"));
        AnchorPane placingField = loader.load();
        mainLayout.setCenter(placingField);
    }

    /**
     * Shows the Playing Field of the Game
     * @throws IOException
     */
    public static void showPlayingFieldWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("PlayingField/PlayingField.fxml"));
        AnchorPane playingField = loader.load();
        mainLayout.setCenter(playingField);
    }

    /**
     * Shows the SaveGame Window, where all saved Files are listed
     * @throws IOException
     */
    public static void showLoadGameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("LoadGame/LoadGame.fxml"));
        AnchorPane placingField = loader.load();
        mainLayout.setCenter(placingField);
    }

    /**
     * Shows a Pop-Up Window, where the User decides if a Game should be saved
     */
    public static void showPopUpSaveGame() {
        dialogSaveGame.show();
    }

    /**
     * Shows a Pop-Up Window in which the User cloud reconnect to the Server or host a Saved-Game-File
     * @param ip Ip-Adresse of the Host
     * @param isServer decides if the Pop-Up is shown for Servers or Clients
     */
    public static void showPopUpReconnect(String ip, boolean isServer) {
        buildPopUpReconnect(ip, isServer);
        dialogReconnect.show();
    }

    /**
     * Shows a Pop-Up Window if a Connection is closed by a User through saving or quitting
     * @param save is true if the User wants to save the Game
     * @param id is the Filename by which the Game is saved
     */
    public static void showPopUpConnectionClosed(boolean save, String id) {
        if (save) {
            connection.setText("Das Spiel wurde vom Gegner gespeichert");
        } else {
            connection.setText("Das Spiel wurde vom Gegner beendet");
        }

        buildPopUpConnectionClosed(save, id);
        dialogConnectionClosed.show();
    }

    /**
     * Shows a Pop-Up Window if the Game could not be saved
     */
    public static void showPopUpCannotSave() {
        dialogCannotSave.show();
    }

    /**
     * Shows a Pop-Up Window with Congratulations and asks the User to start a new Game or leave //TODO
     */
    public static void showStartNewGame() {
        // endGameText = "Sie haben verloren :(";
        if (Game.logicController.isConcratulation()) {
            endGameText.setText("Du hast gewonnen!:)");
        } else {
            endGameText.setText("Du hast verloren :(");
        }

        dialogStartNewGame = new Stage();
        buildPopUpStartNewGame();

        // dialogStartNewGame.
        dialogStartNewGame.show();
    }

    /**
     * Starts the Application
     * @param primaryStage BorderPane on which every Scene is shown
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Game.primaryStage = primaryStage;
        Game.primaryStage.setTitle(GUIConstants.titel);
        showAppWindow();
        showStartGameWindow();
        logicController = new Controller();
        buildPopUpSaveGame();
        buildPopUpCannotSave();

    }

    /**
     * Shows the App Window
     * @throws IOException
     */
    private void showAppWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("Game.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

        mainLayout = loader.load();
        scene = new Scene(new Group(mainLayout));
        Game.primaryStage.setScene(scene);
        Game.primaryStage.show();

        letterbox(scene, mainLayout);
    }

    /**
     * Use for Resizing the Window
     * @param scene is the current scene shown
     * @param contentPane is the Stage on which every Window is shown on
     */
    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth,
                contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    /**
     * Changes the Size of the shown Window from a minimum Size(600x400) to FullSize
     */
    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth,
                                       Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();
            if (newHeight >= 400 && newWidth >= 600) {
                double scaleFactor = newWidth / newHeight > ratio
                        ? newHeight / initHeight
                        : newWidth / initWidth;

                // if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
                // } else {
                // contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                // contentPane.setPrefHeight(Math.max(initHeight, newHeight));
                // }
            } else {
                primaryStage.sizeToScene();
            }
        }
    }

    /**
     * Builds the Pop-Up for saving a Game
     */
    private void buildPopUpSaveGame() {

        VBox dialogVbox = new VBox(10);
        Label text = new Label("Möchten Sie Speichern?");
        text.setMinHeight(50);
        text.setMinWidth(50);
        HBox dialogHbox = new HBox(20);
        Button yes = new Button("Ja");
        Button no = new Button("Nein");
        Button cancel = new Button("Abbrechen");
        dialogVbox.getChildren().addAll(text, dialogHbox);
        dialogHbox.getChildren().addAll(yes, no, cancel);
        dialogHbox.setAlignment(Pos.CENTER);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 100);

        dialogSaveGame.initModality(Modality.APPLICATION_MODAL);
        dialogSaveGame.setScene(dialogScene);

        yes.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                // TODO Speichern
                try {
                    dialogSaveGame.hide();
                    if (Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE) {
                        Network.getNetplay().save();
                    } else {
                        Game.logicController.save();
                    }
                    Game.logicController.initializeGameField();
                    Game.showGameSettingsWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dialogSaveGame.hide();
                    if (Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE) {
                        Network.closeNetwork(Network.getNetplay());
                    }
                    Game.logicController.initializeGameField();
                    Game.showGameSettingsWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogSaveGame.hide();
            }
        });
    }

    /**
     * Builds the Pop-Up for starting a new Game
     */
    private static void buildPopUpStartNewGame() {
        try {
            dialogStartNewGame.initOwner(primaryStage);
        } catch (IllegalStateException e) {

        }

        VBox dialogVbox = new VBox(10);
        Label text = new Label("Möchten Sie ein neues Spiel starten?");
        text.setMinHeight(50);
        text.setMinWidth(100);
        HBox dialogHbox = new HBox(20);
        Button yes = new Button("Ja");
        Button endGame = new Button("Nein");

        dialogVbox.getChildren().addAll(endGameText, text, dialogHbox);
        dialogHbox.getChildren().addAll(yes, endGame);
        dialogHbox.setAlignment(Pos.CENTER);

        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 150);

        dialogStartNewGame.initModality(Modality.APPLICATION_MODAL);
        dialogStartNewGame.setScene(dialogScene);
        dialogStartNewGame.setTitle("Neues Spiel");

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dialogStartNewGame.hide();
                    // Game.logicController.initializeGameField();
                    Network.closeNetwork(Network.getNetplay());
                    Game.showGameSettingsWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Network.closeNetwork(Network.getNetplay());
                Game.logicController.exitGame();
            }
        });
    }

    /**
     * Builds the Pop-Up to Reconnect with a Server or Client
     * @param inputIP is the String, that is put in the TextField of the Pop-Up Reconnect Window
     * @param isServer decides if the Pop-Up is shown for Servers or Clients
     */
    private static void buildPopUpReconnect(String inputIP, boolean isServer) {
        try {
            dialogReconnect.initOwner(primaryStage);
        } catch (IllegalStateException e) {
            //Owner already init
        }
        dialogReconnect.setTitle("Neu Verbinden");
        VBox dialogVbox = new VBox(5);
        Label text = new Label();
        text.setMinHeight(50);
        text.setMinWidth(50);
        HBox dialogHbox = new HBox(20);
        TextField ip = new TextField();
        ip.setMinWidth(148);
        ip.setMinHeight(25);

        if (isServer) {
            ip.setEditable(false);
            ip.setText(inputIP);
        } else {
            ip.setPromptText("IP-Adresse");
        }
        Button verbinden = new Button("Verbinden");

        dialogVbox.getChildren().addAll(dialogHbox, text);
        dialogHbox.getChildren().addAll(ip, verbinden);
        dialogHbox.setAlignment(Pos.CENTER);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 100);

        dialogReconnect.initModality(Modality.APPLICATION_MODAL);
        dialogReconnect.setScene(dialogScene);
        dialogReconnect.setTitle("Erneut Verbinden");

        verbinden.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {


                text.setText("Warte auf Verbindung");
                text.setStyle("-fx-text-fill: grey");
                Thread connect = new Thread(() -> {
                    Server server;
                    Client client;
                    if (isServer) {
                        server = (Server) Network.chooseNetworkTyp(true);
                        connectionStable = server.createServer();
                        if (connectionStable) {
                            connectionStable = server.load();
                        }

                    } else {
                        client = (Client) Network.chooseNetworkTyp(false);
                        connectionStable = client.createClient(ip.getText());
                        if (connectionStable) {
                            connectionStable = client.receiveLoad();
                        }
                    }

                    Platform.runLater(() -> {
                        try {
                            if (!connectionStable) {
                                text.setText("Verbindung fehlgeschlagen");
                                text.setStyle("-fx-text-fill: red");
                            } else {
                                text.setText("Verbunden");
                                text.setStyle("-fx-text-fill: green");
                                dialogReconnect.hide();
                                Game.showPlayingFieldWindow();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
                connect.start();

            }
        });
    }

    /**
     * Builds the Pop-Up if the Connection is closed
     * @param save save is true if the User wants to save the Game
     * @param id is the Filename by which the Game is saved
     */
    private static void buildPopUpConnectionClosed(boolean save, String id) {
        try {
            dialogConnectionClosed.initOwner(primaryStage);
        } catch (IllegalStateException e) {

        }
        VBox dialogVbox = new VBox(10);

        connection.setMinHeight(50);
        connection.setMinWidth(100);
        HBox dialogHbox = new HBox(20);
        Button ok = new Button("Ok");
        dialogHbox.getChildren().addAll(ok);

        if (save) {
            Label idText = new Label("ID: " + id);
            dialogVbox.getChildren().addAll(connection, idText, dialogHbox);
        } else {
            dialogVbox.getChildren().addAll(connection, dialogHbox);
        }

        dialogHbox.setAlignment(Pos.CENTER);

        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 150);

        dialogConnectionClosed.initModality(Modality.APPLICATION_MODAL);
        dialogConnectionClosed.setScene(dialogScene);
        dialogConnectionClosed.setTitle("Verbindung getrennt");

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dialogConnectionClosed.hide();
                    Game.showGameSettingsWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Builds the CannotSave Pop-Up
     */
    private void buildPopUpCannotSave() {
        try {
            dialogStartNewGame.initOwner(primaryStage);
        } catch (IllegalStateException e) {

        }

        VBox dialogVbox = new VBox(50);
        VBox textVbox = new VBox(10);
        Label text = new Label("Sie sind nicht an der Reihe ");
        Label text2 = new Label("und können deshalb nicht Speichern");
        HBox dialogHbox = new HBox(20);
        Button back = new Button("Zurück");
        Button endGame = new Button("Trotzdem Beenden");

        textVbox.getChildren().addAll(text, text2);
        dialogVbox.getChildren().addAll(textVbox, dialogHbox);
        dialogHbox.getChildren().addAll(endGame, back);
        dialogHbox.setAlignment(Pos.CENTER);
        textVbox.setAlignment(Pos.CENTER);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 150);

        dialogCannotSave.initModality(Modality.APPLICATION_MODAL);
        dialogCannotSave.setScene(dialogScene);
        dialogCannotSave.setTitle("Speichern fehlgeschlagen");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialogCannotSave.hide();
            }
        });

        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showGameSettingsWindow();
                    dialogCannotSave.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}