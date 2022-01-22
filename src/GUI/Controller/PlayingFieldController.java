package GUI.Controller;

import GUI.Game;
import Logic.main.LogicConstants;
import Network.*;
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
    @FXML
    private Text statusText;
    //endregion


    private int maxCountTwoShips = 0;
    private int maxCountThreeShips = 0;
    private int maxCountFourShips = 0;
    private int maxCountFiveShips = 0;

    private int size = Game.logicController.getGameSize();
    private GridPaneBuilder gridBuilder;
    private boolean cancel = false;

    private boolean yourTurn = false;

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

        if (Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE) {
            Network netplay = Network.getNetplay();
            if (netplay instanceof Server) {
                yourTurn = true;
            } else if (netplay instanceof Client) {
                yourTurn = false;
            }
        } else {
            yourTurn = true;
        }
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
        if (yourTurn) {
            boolean isHit = Game.logicController.shoot(index);
            gridBuilder.redrawEnemyPanes();

            checkMyWin();

            yourTurn = isHit;
        } else {


            enemyTurn();
            checkEnemyWin();
            yourTurn = true;
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

    private void setStatusText(){
        if(yourTurn){

        }else{

        }

    }


}
