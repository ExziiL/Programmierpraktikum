package GUI.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import GUI.App;

import java.io.IOException;

public class PlatzierfeldController {
  @FXML
  private Button zurück; // braucht man das?

  @FXML
  void handleBack(ActionEvent event) throws IOException {
    App.zeigeSpieleinstellungen();
  }
}
