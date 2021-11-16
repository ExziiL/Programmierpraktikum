package GUI.Controller;

import GUI.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SpielStartenController {
    @FXML
    private Button SpielStarten;

    @FXML
    void starten(ActionEvent event) throws IOException {
        App.zeigeSpieleinstellungen();
    }

    @FXML
    void beenden(ActionEvent event) throws IOException {
        App.logicController.beenden();
    }



}
