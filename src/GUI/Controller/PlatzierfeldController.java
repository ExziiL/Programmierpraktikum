package GUI.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.layout.*;

import java.io.IOException;

public class PlatzierfeldController {
  @FXML
  private Button zurueck; // braucht man das?
  @FXML
  public static GridPane changeGridpane(int groesse) {
    GridPane table = new GridPane();

    // Set GridPane Layout
    table.setGridLinesVisible(true);
    table.setAlignment(Pos.TOP_LEFT);
    table.prefHeight(300);
    table.prefWidth(300);
    table.setLayoutX(41);
    table.setLayoutY(30);



//    //Horizontal and Vertical Grow
//    GridPane.setHgrow(table, Priority.NEVER);
//    GridPane.setVgrow(table, Priority.NEVER);


    //Dynamic Change of Rows and Collumns
    for (int j = 0; j < groesse ; j++) {
      ColumnConstraints column = new ColumnConstraints();
      table.getColumnConstraints().add(column);
      column.setMinWidth(10.0);
      column.setPrefWidth(30.0);
      RowConstraints row = new RowConstraints();
      table.getRowConstraints().add(row);
      row.setMinHeight(10.0);
      row.setPrefHeight(30.0);
      table.addColumn(j);
      for (int i = 0; i < groesse ; i++) {
        Button anchor = new Button();
        table.addRow(i, anchor);
      }
    }
     return table;
  }

  @FXML
  void handleBack(ActionEvent event) throws IOException {
    App.zeigeSpieleinstellungen();
  }
}
