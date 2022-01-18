package GUI;

import Logic.main.Controller;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {
    public static Controller logicController;
    private static BorderPane mainLayout;
    private static Stage primaryStage;
    private static Popup PopupSaveGame;
    private static final Stage dialogSaveGame = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    //     this.primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
    //         @Override
    //         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    //             Thread t = new Thread(() -> {
    //                 ObservableList<Node> child = mainLayout.getChildren();
    //                 Platform.runLater(() -> {
    //                     //handleNodeSize(child, mainLayout, oldValue, newValue);
    //                 });
    //             });
    //             t.start();
    //         }
    //     });
    //     this.primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
    //         @Override
    //         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    //             Thread t = new Thread(() -> {
    //                 ObservableList<Node> child = mainLayout.getChildren();
    //                 Platform.runLater(() -> {
    //                     //handleNodeSize(child, mainLayout, oldValue, newValue);
    //                 });
    //             });
    //             t.start();
    //         }
    //     });
    // }

    public static void showStartGameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("StartGame/StartGame.fxml"));
        BorderPane startGame = loader.load();
        mainLayout.setCenter(startGame);
        mainLayout.setStyle("-fx-background-color: #ffffff");

    }

    public static void showGameSettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("GameSettings/GameSettings.fxml"));
        AnchorPane gameSettings = loader.load();
        mainLayout.setCenter(gameSettings);
    }

    public static void showPlacingFieldWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("PlacingField/PlacingField.fxml"));
        AnchorPane placingField = loader.load();
        mainLayout.setCenter(placingField);
    }

    public static void showPlayingFieldWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("PlayingField/PlayingField.fxml"));
        AnchorPane playingField = loader.load();
        mainLayout.setCenter(playingField);

    }

    public static void showEnd() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("End/End.fxml"));
        AnchorPane playingField = loader.load();
        mainLayout.setCenter(playingField);
    }


    public static void showLoadGameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("LoadGame/LoadGame.fxml"));
        AnchorPane placingField = loader.load();
        mainLayout.setCenter(placingField);
    }

    public static void showPopUpSaveGame() {

        dialogSaveGame.show();

    }



    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(GUIConstants.titel);
        showAppWindow();
        showStartGameWindow();
        logicController = new Controller();
        buildPopUpSaveGame();
    }

    private void showAppWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("Game.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

        mainLayout = loader.load();
        Scene scene = new Scene(new Group(mainLayout));
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        letterbox(scene, mainLayout);
    }


    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }


    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
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

            double scaleFactor =
                    newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }

    private void buildPopUpSaveGame() {

        dialogSaveGame.initOwner(primaryStage);
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
                    Game.showPlacingFieldWindow();
                    Game.logicController.save();
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
                    Game.showPlacingFieldWindow();
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
}



