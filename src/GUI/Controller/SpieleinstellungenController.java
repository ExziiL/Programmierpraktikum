package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SpieleinstellungenController {
    private int[] gameSizeValues = new int[26];

    @FXML
    private Button next;
    @FXML
    private TextField name;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;

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

        App.logicController.setGameSize(gameSizeValues[gameSize.getSelectionModel().getSelectedIndex()]);
        setLabelTexts();
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

    @FXML
    void handleSizeChangend(ActionEvent event) throws IOException {
        App.logicController.setGameSize(gameSizeValues[gameSize.getSelectionModel().getSelectedIndex()]);
        setLabelTexts();
    }

    private void setLabelTexts() {
        labelTwo.setText(App.logicController.getCountTwoShip() + "x 2er Schiffe");
        labelThree.setText(App.logicController.getCountThreeShip() + "x 3er Schiffe");
        labelFour.setText(App.logicController.getCountFourShip() + "x 4er Schiffe");
        labelFive.setText(App.logicController.getCountFiveShip() + "x 5er Schiffe");

    }

}
