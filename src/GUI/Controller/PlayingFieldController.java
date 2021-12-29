package GUI.Controller;

import GUI.Game;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;

public class PlayingFieldController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub

  }

  @FXML
  public void handleBack(ActionEvent event) throws IOException {
    Game.showPlacingFieldWindow();
  }

}
