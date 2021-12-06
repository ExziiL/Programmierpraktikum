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

  public static GridPane changeGridpane(int groesse) {
    GridPane table = new GridPane();

    //Size of Fields in Gridpane
    double hight;
    double width;
    if (groesse < 9) {
      hight = 175;
      width = 175;
    } else if (groesse < 15){
      hight = 140;
      width = 140;
    } else if (groesse < 20){
      hight = 115;
      width = 115;
    } else if (groesse < 25){
      hight = 100;
      width = 100;
    } else {
      hight = 80;
      width = 80;
    }

    // Set GridPane Layout
    table.setGridLinesVisible(true);
    table.setAlignment(Pos.TOP_LEFT);
    table.prefHeight(300);
    table.prefWidth(300);
    table.setLayoutX(41);
    table.setLayoutY(305);


    //Horizontal and Vertical Grow
    GridPane.setHgrow(table, Priority.ALWAYS);
    GridPane.setVgrow(table, Priority.ALWAYS);

    //Dynamic Change of Rows and Collumns
    for (int j = 0; j < groesse ; j++) {
      ColumnConstraints column = new ColumnConstraints();
      table.getColumnConstraints().add(column);
      column.setPrefWidth(width);
      RowConstraints row = new RowConstraints();
      table.getRowConstraints().add(row);
      row.setPrefHeight(hight);
      table.addColumn(j);
      for (int i = 0; i < groesse ; i++) {
        Pane anchor = new Pane();
        anchor.setPrefWidth(width);
        anchor.setPrefHeight(hight);
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
