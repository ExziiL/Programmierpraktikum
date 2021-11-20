package GUI.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PlatzierfeldController {
  @FXML
  private Button zurueck; // braucht man das?
  @FXML
  private GridPane gridPane;
  private int groesse = App.logicController.getSpielfeldgroesse();

  @FXML
  void initialize() {
    for (int i = 1; i <= groesse ; i++) {
        gridPane.addRow(i);
        gridPane.addColumn(i);
    }
  }

  @FXML
  void handle_zurueck(ActionEvent event) throws IOException {
    App.zeigeSpieleinstellungen();
  }
}
