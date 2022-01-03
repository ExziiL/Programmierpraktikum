package GUI.Controller;

import GUI.GUIConstants;
import GUI.Game;
import Utilities.HoverState;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlacingFieldController implements Initializable {
    @FXML
    private GridPane table;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;
    @FXML
    private HBox BoxTwo;
    @FXML
    private HBox BoxThree;
    @FXML
    private HBox BoxFour;
    @FXML
    private HBox BoxFive;
    @FXML
    private ToggleButton EditShips;
    @FXML
    private Button Random;
    @FXML
    private Button Clear;
    @FXML
    private Text textLeftClick;
    @FXML
    private Text textRightClick;

    private GridPaneBuilder gridBuilder;
    private int size = Game.logicController.getGameSize();
    private boolean isHorizontal = false;
    private boolean noPlacingAllowed = false;
    private boolean editMode = false;
    private boolean replaceShipMode = false;
    private int currentShip = 0;
    private Pane currentPane;
    private ObservableList shipPartsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridBuilder = new GridPaneBuilder(size);
        table = gridBuilder.createTablePlacingField(table, this);

        // Event choose Ship
        BoxTwo.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(2);
        });
        BoxThree.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(3);
        });
        BoxFour.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(4);
        });
        BoxFive.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(5);
        });

        // Save Grid List
        shipPartsList = table.getChildren();
        // Build up Choosen Ships Properties
        setChoosenShipProperties();

        // Set Starting Ship
        if (Game.logicController.getCountTwoShip() != 0) {
            chooseShip(2);
        } else {
            chooseShip(3);
        }

        // if (Game.logicController.allShipPlaced()) {
        // Next.setDisable(false);
        // } else {
        // Next.setDisable(true);
        // }

        // Next.setOnAction(event -> {
        // // Screen wechseln
        // try {
        // Game.showPlayingFieldWindow();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // });

        EditShips.setOnAction(event -> {
            if (editMode) {
                setEditMode(false);
                determineNewChoosenShip();
            } else {
                setEditMode(true);
                chooseShip(0);
            }
        });

        Random.setOnAction(event -> {
            Game.logicController.shuffleShips();
            gridBuilder.redrawPlacingField();
            ;
            setChoosenShipProperties();
            // Next.setDisable(false);
            chooseShip(0);
        });

        Clear.setOnAction(event -> {
            Game.logicController.initializeGameField();
            gridBuilder.redrawPlacingField();
            setChoosenShipProperties();
        });

        textLeftClick.setText(GUIConstants.explTextPlacingLeft);
        textRightClick.setText(GUIConstants.explTextPlacingRight);

        gridBuilder.redrawPlacingField();
    }

    // Event Handler (Called by GridPaneBuilder)
    public void handlePaneOnMouseEntered(Pane pane) {
        hoverShip(pane);
        currentPane = pane;
    }

    // Event Handler (Called by GridPaneBuilder)
    public void handlePaneOnMouseClicked(Pane pane, MouseButton button) {
        if (button == MouseButton.PRIMARY) {
            if (editMode) {
                if (replaceShipMode) {
                    placeShip(pane);
                    setReplaceShipMode(false);
                    chooseShip(0);
                    gridBuilder.redrawPlacingField();
                } else {
                    replaceShip(pane);
                    setReplaceShipMode(true);
                    gridBuilder.redrawPlacingField();
                    hoverShip(pane);
                }
            } else {
                if (currentShip != 0) {
                    placeShip(pane);
                    gridBuilder.redrawPlacingField();
                }

            }
        } else if (button == MouseButton.SECONDARY) {
            if (editMode && !replaceShipMode) {
                deleteShip(pane);
                gridBuilder.redrawPlacingField();
            } else if (!editMode || replaceShipMode) {
                rotateShip();
            }
        }
        setChoosenShipProperties();
    }

    @FXML
    public void handleBack(MouseEvent event) throws IOException {
        Game.showGameSettingsWindow();
    }

    @FXML
    public void handleNext(MouseEvent event) throws IOException {
        Game.showPlayingFieldWindow();
    }

    private void setChoosenShipProperties() {
        labelTwo.setText(Game.logicController.getCountTwoShip() + "x");
        labelThree.setText(Game.logicController.getCountThreeShip() + "x");
        labelFour.setText(Game.logicController.getCountFourShip() + "x");
        labelFive.setText(Game.logicController.getCountFiveShip() + "x");

        if (Game.logicController.getCountTwoShip() == 0) {
            BoxTwo.setDisable(true);
            if (currentShip == 2) {
                determineNewChoosenShip();
            }
        } else {
            BoxTwo.setDisable(false);
        }
        if (Game.logicController.getCountThreeShip() == 0) {
            BoxThree.setDisable(true);
            if (currentShip == 3) {
                determineNewChoosenShip();
            }
        } else {
            BoxThree.setDisable(false);
        }
        if (Game.logicController.getCountFourShip() == 0) {
            BoxFour.setDisable(true);
            if (currentShip == 4) {
                determineNewChoosenShip();
            }
        } else {
            BoxFour.setDisable(false);
        }
        if (Game.logicController.getCountFiveShip() == 0) {
            BoxFive.setDisable(true);
            if (currentShip == 5) {
                determineNewChoosenShip();
            }
        } else {
            BoxFive.setDisable(false);
        }
    }

    private void determineNewChoosenShip() {
        for (int i = 2; i <= 5; i++) {
            switch (i) {
                case 2:
                    if (currentShip != 2 && !BoxTwo.isDisabled()) {
                        chooseShip(2);
                        return;
                    }
                    break;
                case 3:
                    if (currentShip != 3 && !BoxThree.isDisabled()) {
                        chooseShip(3);
                        return;
                    }
                    break;
                case 4:
                    if (currentShip != 4 && !BoxFour.isDisabled()) {
                        chooseShip(4);
                        return;
                    }
                    break;
                case 5:
                    if (currentShip != 5 && !BoxFive.isDisabled()) {
                        chooseShip(5);
                        return;
                    }
                    break;
            }
        }
    }

    private void hoverShip(Pane pane) {

        HoverState[] states = Game.logicController.getHoverStateStatus(shipPartsList.indexOf(pane), currentShip,
                isHorizontal);

        noPlacingAllowed = gridBuilder.hoverShip(states);
    }

    private void placeShip(Pane pane) {
        if (noPlacingAllowed == false) {
            Game.logicController.placeShip(shipPartsList.indexOf(pane), currentShip, isHorizontal);
        }
        if (Game.logicController.allShipPlaced() == true) {
            // Next.setDisable(false);
            chooseShip(0);
        }
    }

    private void deleteShip(Pane pane) {
        if (Game.logicController.deleteShip(shipPartsList.indexOf(pane))) {
            // Next.setDisable(true);
        }
    }

    private void replaceShip(Pane pane) {
        int shipSize = Game.logicController.getShipSize(shipPartsList.indexOf(pane));
        boolean isHorizontal = Game.logicController.isShipHorizontal(shipPartsList.indexOf(pane));
        deleteShip(pane);
        this.currentShip = shipSize;
        this.isHorizontal = isHorizontal;
    }

    private void rotateShip() {
        if (isHorizontal) {
            isHorizontal = false;
        } else {
            isHorizontal = true;
        }
        gridBuilder.redrawPlacingField();
        hoverShip(currentPane);

    }

    private void unchooseActualShip() {
        HBox box = getBoxShip(currentShip);
        if (box != null) {
            box.setStyle("-fx-border-color: none ;");
        }
    }

    private void chooseShip(int ship) {

        unchooseActualShip();
        currentShip = ship;
        HBox box = getBoxShip(ship);
        if (box != null) {
            box.setStyle( // TODO: hier statt setStyle das jeweilige Bild einsetzen (gemeinsam mit der
                    // TODO: unchooseActualShip (Z. 218))
                    "-fx-border-color: black ; -fx-border-radius: 7px;");
        }
    }

    private HBox getBoxShip(int ship) {
        switch (ship) {
            case 2:
                return BoxTwo;
            case 3:
                return BoxThree;
            case 4:
                return BoxFour;
            case 5:
                return BoxFive;
            default:
                return null;
        }
    }

    private void setEditMode(boolean mode) {
        editMode = mode;
        EditShips.setSelected(mode);
        setHelpTexts();
    }

    private void setReplaceShipMode(boolean mode) {
        replaceShipMode = mode;
        setHelpTexts();
    }

    private void setHelpTexts() {
        if (editMode && !replaceShipMode) {
            textLeftClick.setText(GUIConstants.explTextEditLeft);
            textRightClick.setText(GUIConstants.explTextEditRight);
        } else {
            textLeftClick.setText(GUIConstants.explTextPlacingLeft);
            textRightClick.setText(GUIConstants.explTextPlacingRight);
        }
    }
}
