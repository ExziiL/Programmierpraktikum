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
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {
    private Stage primaryStage;
    private static BorderPane mainLayout;
    public static Controller logicController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(GUIConstants.titel);
        logicController = new Controller();

        showAppWindow();
        showStartGameWindow();

        //System.out.println(Thread.currentThread().getName());
        mainLayout.widthProperty().toString();

        this.primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Thread t = new Thread(() -> {
                    ObservableList<Node> child = mainLayout.getChildren();
                    Platform.runLater(() -> {
                        handleNodeSize(child, mainLayout, oldValue, newValue);
                    });
                });
                t.start();
            }
        });
    }

    /**
     * Sets the Borderpane of Game.fxml as a new Scene, which is displayed.
     *
     * @throws IOException can potentially throw this Exception
     */
    private void showAppWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("Game.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    /**
     * Loads the StartGame.fxml and set in the center of the mainLayout-Borderpane
     *
     * @throws IOException
     */
    public static void showStartGameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("StartGame/StartGame.fxml"));
        BorderPane startGame = loader.load();
        mainLayout.setCenter(startGame);
    }

    /**
     * Loads the GameSettings.fxml and replaces the center of the mainLayout-Borderpane,
     * the StartGame.fxml
     *
     * @throws IOException
     */
    public static void showGameSettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("GameSettings/GameSettings.fxml"));
        AnchorPane gameSettings = loader.load();
        mainLayout.setCenter(gameSettings);
    }


    private void handleNodeSize(ObservableList<Node> nodes, Pane parentnode, Number oldV, Number newV) {
        for (Node x : nodes) {
            if (x instanceof Pane) {
                Pane node = (Pane) x;
                ObservableList<Node> children = node.getChildren();
                if (node instanceof GridPane) {
                    node.setPrefWidth(node.getWidth() + newV.doubleValue() - oldV.doubleValue());
                    node.setPrefHeight(node.getHeight() + newV.doubleValue() - oldV.doubleValue());
                    handleNodeSize(children, parentnode, oldV, newV);
                } else if (node instanceof HBox) {

                } else if (node instanceof VBox) {

                } else {
                    handleNodeSize(children, node, oldV, newV);
                }
            }
            else if (x instanceof Button) {
                Button button = (Button) x;


                button.setLayoutX(button.getLayoutX() + (newV.doubleValue() - oldV.doubleValue())/4);
                button.setLayoutY(button.getLayoutY() + (newV.doubleValue() - oldV.doubleValue())/4);

            }
            else if (x instanceof Text){

            }
            else if (x instanceof ImageView){
                ImageView image = (ImageView) x;

                image.setLayoutX(image.getLayoutX() + (newV.doubleValue() - oldV.doubleValue())/4);
                image.setLayoutY(image.getLayoutY() + (newV.doubleValue() - oldV.doubleValue())/4);
            }


        }
    }
}
/*public class Game extends Application {
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
}*/