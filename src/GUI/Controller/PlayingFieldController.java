package GUI.Controller;

import GUI.GUIConstants;
import GUI.Game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class PlayingFieldController implements Initializable {

  @FXML
  private GridPane tableEnemy;

  @FXML
  private GridPane tableGamer;

  private int size = Game.logicController.getGameSize();

  private GridPaneBuilder gridBuilder;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    gridBuilder = new GridPaneBuilder(size);

    tableEnemy = gridBuilder.createTableEnemy(tableEnemy);
    tableGamer = gridBuilder.createTableGamer(tableGamer);

  }

  @FXML
  public void handleBack(MouseEvent event) throws IOException {
    Game.showPlacingFieldWindow();
  }

}
