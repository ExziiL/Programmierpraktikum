package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import GUI.Game;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GameSettingsController {
    private final int[] gameSizeValues = new int[26];

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
        if (Game.logicController.getGameSize() >= 5) {
            // Index Anpassen auf Array z.B. wenn Size = 5 Index = 0 .....
            gameSize.getSelectionModel().select(Game.logicController.getGameSize() - 5);
        } else {
            gameSize.getSelectionModel().selectFirst();
        }

        Game.logicController.setGameSize(gameSizeValues[gameSize.getSelectionModel().getSelectedIndex()]);
        setLabelTexts();
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        Game.showStartGameWindow();
    }

    @FXML
    void handleInputName(ActionEvent event) throws IOException {

    }

    @FXML
    void handleNext(ActionEvent event) throws IOException {

        Game.logicController.setName(name.getCharacters().toString());
        Game.logicController.setGameSize(gameSizeValues[gameSize.getSelectionModel().getSelectedIndex()]);
        Game.showPlacingFieldWindow();
    }

    @FXML
    void handleSizeChanged(ActionEvent event) throws IOException {
        Game.logicController.setGameSize(gameSizeValues[gameSize.getSelectionModel().getSelectedIndex()]);
        setLabelTexts();
    }

    private void setLabelTexts() {
        labelTwo.setText(Game.logicController.getCountTwoShip() + "x");
        labelThree.setText(Game.logicController.getCountThreeShip() + "x");
        labelFour.setText(Game.logicController.getCountFourShip() + "x");
        labelFive.setText(Game.logicController.getCountFiveShip() + "x");

    }

}
