package GUI.Controller;

import Logic.main.LogicConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import GUI.Game;

import java.io.IOException;

public class GameSettingsController {
    @FXML
    private Slider slider;
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
    private ComboBox<String> gameMode;


    private int gameSize;

    @FXML
    void initialize() {
        if (Game.logicController.getGameSize() >= 5) {
            gameSize = Game.logicController.getGameSize();
        } else {
            gameSize = 5;
        }
        Game.logicController.setGameSize(gameSize);
        slider.setValue(gameSize);
        setLabelTexts();


        ObservableList<String> options = FXCollections.observableArrayList("Offline", "Online", "???");
        gameMode.setItems(options);
        gameMode.setValue("Offline");
    }

    // ------------------------------- Zurück-Button ------------------------------
    @FXML
    void handleBack(MouseEvent event) throws IOException {
        Game.showStartGameWindow();
    }

    // ------------------------------- Name-Input ---------------------------------
    @FXML
    void handleInputName(ActionEvent event) throws IOException {
    }

    // ------------------------------- Slider ---------------------------------
    @FXML
    void handleSliderChange() throws IOException {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSize = newValue.intValue();
            Game.logicController.setGameSize(gameSize);
            setLabelTexts();
        });
    }

    ;

    // ------------------------------- Next-Button ---------------------------------

    @FXML
    void handleNext(MouseEvent event) throws IOException {
        Game.logicController.setName(name.getCharacters().toString());
        Game.logicController.setGameSize(gameSize);
        Game.logicController.setGameMode(determineGameMode());
        Game.showPlacingFieldWindow();
    }

    private void setLabelTexts() {
        labelTwo.setText(Game.logicController.getCountTwoShip() + "x");
        labelThree.setText(Game.logicController.getCountThreeShip() + "x");
        labelFour.setText(Game.logicController.getCountFourShip() + "x");
        labelFive.setText(Game.logicController.getCountFiveShip() + "x");
    }

    private LogicConstants.GameMode determineGameMode() {

        if (gameMode.getSelectionModel().equals("Online")) {
            return LogicConstants.GameMode.ONLINE;
        }
        return LogicConstants.GameMode.OFFLINE;
    }
}
