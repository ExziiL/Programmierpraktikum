package GUI.Controller;

import GUI.Game;
import Logic.main.LogicConstants;
import Network.Client;
import Network.Network;
import Network.Server;
import javafx.application.Platform;
import javafx.event.EventHandler;
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

    // region FXML-Variables
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
    // endregion

    // region Variables
    private int maxCountTwoShips = 0;
    private int maxCountThreeShips = 0;
    private int maxCountFourShips = 0;
    private int maxCountFiveShips = 0;

    private int size = Game.logicController.getGameSize();
    private GridPaneBuilder gridBuilder;
    private boolean cancel = false;
    private boolean yourTurn = false;
    // endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridBuilder = new GridPaneBuilder(size, null, null, null, null);

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
                Thread t = new Thread(() -> {
                    enemyTurn();
                    yourTurn = true;
                    Platform.runLater(() -> {
                        setStatusText();
                    });
                });
                t.start();
            }
        } else {
            yourTurn = true;
        }
        setStatusText();

        tableEnemy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
            }
        });
        tableEnemy.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
            }
        });

    }

    @FXML
    public void handleBack(MouseEvent event) throws IOException {
        Game.showPopUpSaveGame();
    }

    public void handleSetOnMouseClicked(MouseEvent event, int index) { // TODO enemyTurn darf nicht erst wenn geclickt
        // wurde aufgerufen werden, muss automatisch
        // aufgerufen werden

        if (event.getButton() == MouseButton.PRIMARY) {
            yourTurn(index);

        }
    }

    public void yourTurn(int index) {


        Thread t = new Thread(() -> {
            int answer[] = new int[3];

            if (yourTurn) {
                    yourTurn = Game.logicController.shoot(index) > 0;
                Platform.runLater(() -> {
                    checkMyWin();
                    setStatusText();
                    setLabelsShipDestroyed();
                    gridBuilder.redrawEnemyPanes();
                });
            }
            if (!yourTurn) {
                enemyTurn();

                Platform.runLater(() -> {
                    yourTurn = true;
                    setStatusText();
                    checkEnemyWin();
                });
            }

        });

        t.start();

    }

    public void enemyTurn() {
        int isEnemyTurn = 0;
        do {
            isEnemyTurn = Game.logicController.enemyTurn();

            if (isEnemyTurn == 2) {
                Platform.runLater(() -> {
                    Game.showPopUpConnectionClosed(true, Game.logicController.getDocumentID());
                });
                isEnemyTurn = 1;
            }

            if (Game.logicController.allShipsDestroyed()) {
                isEnemyTurn = 1;
            }
            Platform.runLater(() -> {
                gridBuilder.redrawGamerPanes();
            });
        } while (isEnemyTurn != 1);
    }

    public void checkMyWin() {
        if (Game.logicController.allEnemyShipsDestroyed()) {
            Game.logicController.setConcratulation(true);
            // Game.showEnd();
            Game.showStartNewGame();
        }
    }

    public void checkEnemyWin() {
        if (Game.logicController.allShipsDestroyed()) {
            Game.logicController.setConcratulation(false);
            // Game.showEnd();
            Game.showStartNewGame();
        }
    }

    public boolean isYourTurn() {
        return yourTurn;
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

    private void setStatusText() {
        if (yourTurn) {
            statusText.setText("Du bist dran!");
        } else {
            statusText.setText("Warte auf Gegner");
        }
    }
}
