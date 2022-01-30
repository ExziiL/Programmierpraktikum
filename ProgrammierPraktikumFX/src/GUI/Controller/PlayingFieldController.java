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
    private boolean noClick = false;
    // endregion

    /**
     * Initializes the programmed Parts of the PlayingField. The GridPanes are generated and the Labels, where
     * the Count of Ships from the Enemy is shown, get the number of each Kind of Ship.
     * Also, the Event for Hovering over the Panes in the EnemyGame are implemented.
     * @param location not used
     * @param resources not used
     */
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

    /**
     * Handels if the Backarrow is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleBack(MouseEvent event) throws IOException {
        if (yourTurn) {
            Game.showPopUpSaveGame();
        } else {
            Game.showPopUpCannotSave();
        }

    }

    /**
     *  //TODO
     * @param event fires when the left MouseButton is pressed
     * @param index identifying Number of a Pane in the GridPane
     */
    public void handleSetOnMouseClicked(MouseEvent event, int index) {
        if (event.getButton() == MouseButton.PRIMARY && yourTurn) {
            yourTurn(index);
        }
    }

    /**
     * Determines if it is the Users Turn or Computer's/Enemy's. Checks also for a Win or Lose of the Player,
     * depending on if it is the User's Turn or not and if in a Network GAme a Player disconnects.
     * @param index identifying Number of a Pane in the GridPane
     */
    public void yourTurn(int index) {

        Thread t = new Thread(() -> {
            int answer[] = new int[3];
            int result = 0;

            if (yourTurn) {
                if (Game.logicController.getEnemyElementStatus(index) == LogicConstants.GameElementStatus.HIT ||
                        Game.logicController.getEnemyElementStatus(index) == LogicConstants.GameElementStatus.MISS) {
                    yourTurn = true;
                } else {
                    result = Game.logicController.shoot(index) ;

                    if(result == -1){
                        Platform.runLater(() -> {
                            Game.showPopUpConnectionClosed(false, "");
                        });
                    }
                    yourTurn = result == 0;
                }

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

    /**
     * Handels the Turn of the Computer or another Player over the Network.
     * Shows the ConnectionClosed Pop-Up if the Enemy closes the Connection
     * Gives also a Signal if all Ships are destroyed.
     */
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

            if (isEnemyTurn == 99) {
                isEnemyTurn = 1;
                Platform.runLater(() -> {
                    Game.showPopUpConnectionClosed(false, "");
                });
            }

            Platform.runLater(() -> {
                gridBuilder.redrawGamerPanes();
            });
        } while (isEnemyTurn != 1);


    }

    public void checkMyWin() {
        if (Game.logicController.allEnemyShipsDestroyed()) {
            Game.logicController.setConcratulation(true);
            Game.showStartNewGame();
        }
    }

    public void checkEnemyWin() {
        if (Game.logicController.allShipsDestroyed()) {
            Game.logicController.setConcratulation(false);
            Game.showStartNewGame();
        }
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setNoClick(boolean noClick) {
        this.noClick = noClick;
    }

    /**
     * Sets the current Count of the Ship-kind and is called is something has updated,
     * to update the Labels
     */
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