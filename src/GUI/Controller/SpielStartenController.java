package GUI.Controller;

import GUI.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SpielStartenController {
    App app;

    @FXML
    private Button SpielStarten;
    
    @FXML
    void display(ActionEvent event) throws IOException {
        app.zeigeSpieleinstellungen();
    }
}
