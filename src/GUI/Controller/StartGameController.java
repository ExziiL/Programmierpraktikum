package GUI.Controller;

import GUI.Game;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartGameController {
    @FXML
    void handleStartGame(MouseEvent event) throws IOException {
        Game.showGameSettingsWindow();
    }

    @FXML
    void handleClose(MouseEvent event) throws IOException {
        Game.logicController.exitGame();
    }

    @FXML
    void handleOpenSettings(MouseEvent event) throws IOException {
        Game.showGameSettingsWindow();
    }

}
