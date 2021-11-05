package GUI.Spieleinstellungen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class Spieleinstellungen extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("Spieleinstellungen.fxml"));
    primaryStage.setTitle("Spieleinstellungen");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();

    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll("Apples", "Bananas", "Oranges", "Melons");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
