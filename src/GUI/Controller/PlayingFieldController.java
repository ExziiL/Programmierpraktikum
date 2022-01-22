package GUI.Controller;

import GUI.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayingFieldController implements Initializable {

    //region FXML-Variables
    @FXML
    private GridPane tableEnemy;
    @FXML
    private GridPane tableGamer;
    @FXML
    private Text labelTwo;
    @FXML
    private Text labelThree;
    @FXML
    private Text labelFour;
    @FXML
    private Text labelFive;

    private int maxCountTwoShips = 0;
    private int maxCountThreeShips = 0;
    private int maxCountFourShips = 0;
    private int maxCountFiveShips = 0;

    private int size = Game.logicController.getGameSize();
    private GridPaneBuilder gridBuilder;
    private boolean cancel = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridBuilder = new GridPaneBuilder(size);

        maxCountTwoShips = Game.logicController.getAllTwoShips();
        maxCountThreeShips = Game.logicController.getAllThreeShips();
        maxCountFourShips = Game.logicController.getAllFourShips();
        maxCountFiveShips = Game.logicController.getAllFiveShips();

        tableEnemy = gridBuilder.createTableEnemy(tableEnemy, this);
        tableGamer = gridBuilder.createTableGamer(tableGamer);

        setLabelsShipDestroyed();

        gridBuilder.redrawGamerPanes();
        gridBuilder.redrawEnemyPanes();
    }


    @FXML
    public void handleBack(MouseEvent event) throws IOException {

        Game.showPopUpSaveGame();
    }

    public void handleSetOnMouseClicked(MouseEvent event, int index) {

        if (event.getButton() == MouseButton.PRIMARY) {
            yourTurn(index);
            setLabelsShipDestroyed();
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

    private void setLabelsShipDestroyed() {

        int currentTwoShip = Game.logicController.getDestroyedShips(2);
        int currentThreeShip = Game.logicController.getDestroyedShips(3);
        int currentFourShip = Game.logicController.getDestroyedShips(4);
        int currentFiveShip = Game.logicController.getDestroyedShips(5);

        labelTwo.setText(currentTwoShip + " / " + maxCountTwoShips);
        labelThree.setText(currentThreeShip + " / " + maxCountThreeShips);
        labelFour.setText(currentFourShip + " / " + maxCountFourShips);
        labelFive.setText(currentFiveShip + " / " + maxCountFiveShips);
    }
}
