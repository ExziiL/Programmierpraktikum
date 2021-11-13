package GUI.Spieleinstellungen;

import GUI.App;
import GUI.GUIKonstanten;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Spieleinstellungen extends Application {

    private Stage primarystage;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primarystage = primaryStage;
        this.primarystage.setTitle(GUIKonstanten.titel);
        this.primarystage.setTitle("Spieleinstellungen");
        showApp();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Apples", "Bananas", "Oranges", "Melons");
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void showApp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Spieleinstellungen.class.getResource("Spieleinstellungen.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));

        this.mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 600, 400);
        this.primarystage.setScene(scene);
        this.primarystage.show();
    }

}
