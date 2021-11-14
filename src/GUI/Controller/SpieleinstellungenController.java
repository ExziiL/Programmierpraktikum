package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import GUI.App;

import java.io.IOException;

public class SpieleinstellungenController {
    private App app;

    @FXML
    private Button SpielStarten;

    @FXML
    void zurueck(ActionEvent event) throws IOException {
        app.zeigeSpielStarten();
    }
}
