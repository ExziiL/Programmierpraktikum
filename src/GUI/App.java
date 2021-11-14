package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import GUI.Spieleinstellungen.*;
import sun.security.provider.ConfigFile;

public class App extends Application {
    private Stage primarystage;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primarystage = primaryStage;
        this.primarystage.setTitle(GUIKonstanten.titel);
        zeigeApp();
        zeigeSpielStarten();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static void zeigeSpieleinstellungen() throws  IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("Spieleinstellungen/Spieleinstellungen.fxml"));
            BorderPane Spieleinstellungen = loader.load();
            mainLayout.setCenter(Spieleinstellungen);
    }

    public static void zeigeSpielStarten() throws  IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("SpielStarten/SpielStarten.fxml"));
        BorderPane SpielStarten = loader.load();
        mainLayout.setCenter(SpielStarten);
    }

    private void zeigeApp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("App.fxml"));
       // Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));

        this.mainLayout = loader.load();
        Scene scene = new Scene(this.mainLayout);
        this.primarystage.setScene(scene);
        this.primarystage.show();
    }


    //  private void SpielStarten(ActionEvent event) {
    //      System.out.println("Spiel gestartet");
    //  }


}