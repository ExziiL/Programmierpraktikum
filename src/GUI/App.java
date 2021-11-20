package GUI;

import Logic.main.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    private Stage primarystage;
    private static BorderPane mainLayout;
    public static Controller logicController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primarystage = primaryStage;
        this.primarystage.setTitle(GUIKonstanten.titel);
        zeigeApp();
        zeigeSpielStarten();
        logicController = new Controller();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void zeigeSpieleinstellungen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("Spieleinstellungen/Spieleinstellungen.fxml"));
        AnchorPane Spieleinstellungen = loader.load();
        mainLayout.setCenter(Spieleinstellungen);
    }

    public static void zeigeSpielStarten() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("SpielStarten/SpielStarten.fxml"));
        BorderPane SpielStarten = loader.load();
        mainLayout.setCenter(SpielStarten);
    }

    public static void zeigePlatzierfeld() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("Platzierfeld/Platzierfeld.fxml"));
        AnchorPane Platzierfeld = loader.load();
        mainLayout.setCenter(Platzierfeld);
    }

    private void zeigeApp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("App.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));

        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        this.primarystage.setScene(scene);
        this.primarystage.show();
    }

}