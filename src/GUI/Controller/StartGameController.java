package GUI.Controller;

import GUI.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartGameController {
    @FXML
    void handleStartGame(ActionEvent event) throws IOException {
        Game.showGameSettingsWindow();
    }

    @FXML
    void handleClose(ActionEvent event) throws IOException {
        Game.logicController.exitGame();
    }

}
