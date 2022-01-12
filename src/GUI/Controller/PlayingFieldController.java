package GUI.Controller;

import GUI.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayingFieldController implements Initializable {

    @FXML
    private GridPane tableEnemy;

    @FXML
    private GridPane tableGamer;

    private int size = Game.logicController.getGameSize();
    private GridPaneBuilder gridBuilder;
    private boolean cancel = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridBuilder = new GridPaneBuilder(size);

        Game.logicController.createEnemyGame();

        tableEnemy = gridBuilder.createTableEnemy(tableEnemy, this);
        tableGamer = gridBuilder.createTableGamer(tableGamer);

    }

    @FXML
    public void handleBack(MouseEvent event) throws IOException {

        Game.showPopUpSaveGame();
    }

    public void handleSetOnMouseClicked(MouseEvent event, int index) {

        if (event.getButton() == MouseButton.PRIMARY) {
            yourTurn(index);
        }
    }

    public void yourTurn(int index) {
        boolean isHit = Game.logicController.shoot(index);
        gridBuilder.redrawEnemyPanes();

        checkMyWin();

        if (isHit == false) {
            enemyTurn();
            checkEnemyWin();
        }
    }

    public void enemyTurn() {
        boolean isEnemyTurn = false;
        do {
            isEnemyTurn = Game.logicController.enemyTurn();
        } while (isEnemyTurn);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    gridBuilder = new GridPaneBuilder(size);
    tableEnemy = gridBuilder.createTableEnemy(tableEnemy);
    tableGamer = gridBuilder.createTableGamer(tableGamer);
  }

        gridBuilder.redrawGamerPanes();
    }

    public void checkMyWin() {

        if (Game.logicController.allEnemyShipsDestroyed()) {
            try {
                Game.logicController.setConcratulation(true);
                Game.showEnd();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void checkEnemyWin() {

        if (Game.logicController.allShipsDestroyed()) {
            try {
                Game.logicController.setConcratulation(false);
                Game.showEnd();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
