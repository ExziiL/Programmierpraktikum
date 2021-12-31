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


    /**
     * putting the exit-Method, which is in Logic.main.Controller,
     * in own Thread to keep GUI and Logic seperated( just for Testing)
     * @param event
     * @throws IOException
     */
    @FXML
    void handleClose(ActionEvent event) throws IOException {
        System.out.println(Thread.currentThread().getName());
        Thread t = new Thread(()-> {
            Game.logicController.exitGame();
        });
        t.start();
    }

}
