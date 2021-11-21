package GUI.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PlatzierfeldController {
  @FXML
  private Button zurueck; // braucht man das?
  @FXML
  private AnchorPane anchorPane;


  @FXML
  public static GridPane changeGridpane(int groesse) {
    GridPane table = new GridPane();
    for (int i = 0; i <= groesse ; i++) {
      table.addRow(i);
      table.addColumn(i);
    }
    table.setGridLinesVisible(true);
    table.setAlignment(Pos.TOP_LEFT);
    return table;
  }

  @FXML
  void handle_zurueck(ActionEvent event) throws IOException {
    App.zeigeSpieleinstellungen();
  }
}
