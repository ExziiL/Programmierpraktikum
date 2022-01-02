package GUI;

import Logic.main.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {
    private Stage primaryStage;
    private static BorderPane mainLayout;
    public static Controller logicController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(GUIConstants.titel);
        showAppWindow();
        showStartGameWindow();
        logicController = new Controller();
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

    public static void main(String[] args) {
        launch(args);
    }

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

    private void showAppWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("Game.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 800, 600);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

}
