package GUI.Controller;

import GUI.Game;
import Utilities.WindowsPlatformAppPDF;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

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
    void handleShowManual(MouseEvent event) throws IOException {
        WindowsPlatformAppPDF.showManual();
    }

    @FXML
    void handleLoadGame(MouseEvent event) throws IOException {
        Game.showLoadGameWindow();
    }

}
