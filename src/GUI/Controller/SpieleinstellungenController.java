package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SpieleinstellungenController {
    private int[] gameSizeValues = new int[26];

    @FXML
    private Button next;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> gameSize;

    @FXML
    void initialize() {
        for (int i = 0; i < gameSizeValues.length; i++) {
            int x = i + 5;
            gameSizeValues[i] = x;
            gameSize.getItems().add(x + " x " + x);
        }
        if (App.logicController.getGameSize() >= 5) {
            // Index Anpassen auf Array z.B. wenn Size = 5 Index = 0 .....
            gameSize.getSelectionModel().select(App.logicController.getGameSize() - 5);
        } else {
            gameSize.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        App.zeigeSpielStarten();
    }

    @FXML
    void handleInputName(ActionEvent event) throws IOException {

    }

    @FXML
    void handleNext(ActionEvent event) throws IOException {

        App.logicController.setName(name.getCharacters().toString());
        App.logicController.setGameSize(gameSizeValues[gameSize.getSelectionModel().getSelectedIndex()]);
        App.zeigePlatzierfeld();
    }

}
