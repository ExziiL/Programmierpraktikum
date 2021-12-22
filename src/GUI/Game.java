package GUI;

import GUI.Controller.PlacingFieldController;
import Logic.main.Controller;
import javafx.application.Application;
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

        this.primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ObservableList scenes = mainLayout.getChildren();
                for (Object obj : scenes) {
                    if (obj instanceof Node) {
                        Node object = (Node) obj;
                        object.prefWidth(newValue.doubleValue());
                    }
                }
            }
        });
        this.primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ObservableList scenes = mainLayout.getChildren();
                for (Object obj : scenes) {
                    if (obj instanceof Node) {
                        Node object = (Node) obj;
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showGameSettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("GameSettings/GameSettings.fxml"));
        AnchorPane gameSettings = loader.load();
        mainLayout.setCenter(gameSettings);
    }

    public static void showStartGameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("StartGame/StartGame.fxml"));
        BorderPane startGame = loader.load();
        mainLayout.setCenter(startGame);
    }

    public static void showPlacingFieldWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("PlacingField/PlacingField.fxml"));
        AnchorPane placingField = loader.load();
        mainLayout.setCenter(placingField);
    }

    private void showAppWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("Game.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
}