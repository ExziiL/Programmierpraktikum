package GUI.Controller;

import Logic.main.LogicConstants;
import Utilities.Exception.ShipOutofGame;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import GUI.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Utilities.HoverState;

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
    private AnchorPane placingField;

    @FXML
    private HBox BoxTwo;
    @FXML
    private HBox BoxThree;
    @FXML
    private HBox BoxFour;
    @FXML
    private HBox BoxFive;
    @FXML
    private Button Next;
    @FXML
    private Button EditShips;
    @FXML
    private Button Random;

    private int size = Game.logicController.getGameSize();
    private boolean isHorizontal = false;
    private boolean noPlacingAllowed = false;
    private int currentShip = 0;
    private Pane currentPane;
    private ObservableList shipPartsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 0;
        double paneSize = setPaneSize();
        for (int i = 0; i < size * size; i++) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: white;");
            pane.setStyle("-fx-border-color: #999898 ;");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);

            if (column == size) {
                column = 0;
                row++;
            }
            pane.setId("field" + row + column);
            table.add(pane, column++, row);


            //GridPane.setMargin(pane, new Insets(0.5, 0.5, 0.5, 0.5));

            // set Gridpane Hights
            table.setPrefHeight(Region.USE_COMPUTED_SIZE);
            table.setMinHeight(Region.USE_COMPUTED_SIZE);
            table.setMaxHeight(Region.USE_PREF_SIZE);

            // set Gridpane Widths
            table.setPrefWidth(Region.USE_COMPUTED_SIZE);
            table.setMinWidth(Region.USE_COMPUTED_SIZE);
            table.setMaxWidth(Region.USE_PREF_SIZE);

            pane.setOnMouseEntered(event -> {
                hoverShip(pane);
                currentPane = pane;
            });

            pane.setOnMouseExited(event -> {
                redrawPanes();
            });

            pane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (currentShip != 0) {
                        placeShip(pane);
                        redrawPanes();
                        setChoosenShipProperties();
                    } else {

                        resetShip(pane);
                    }
                }
            });

        }

        // Event Shiff wechseln
        placingField.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (isHorizontal) {
                    isHorizontal = false;
                } else {
                    isHorizontal = true;
                }
                redrawPanes();
                hoverShip(currentPane);
            }
        });

        // Event Shiff auswählen
        BoxTwo.setOnMouseClicked(event -> {
            chooseShip(2);
        });
        BoxThree.setOnMouseClicked(event -> {
            chooseShip(3);
        });
        BoxFour.setOnMouseClicked(event -> {
            chooseShip(4);
        });
        BoxFive.setOnMouseClicked(event -> {
            chooseShip(5);
        });

        // Grid liste Speichern
        shipPartsList = table.getChildren();
        // Auswahl Shiffe aufbauen
        setChoosenShipProperties();

        // Startshiff auswählen
        if (Game.logicController.getCountTwoShip() != 0) {
            chooseShip(2);
        } else {
            chooseShip(3);
        }

        // Weiter Butten ausblenden
        Next.setDisable(true);
        Next.setOnAction(event -> {
            // Screen wechseln
        });

        EditShips.setOnAction(event -> {
            chooseShip(0);
        });

        Random.setOnAction(event -> {
            Game.logicController.shuffleShips();
            redrawPanes();
            setChoosenShipProperties();
        });


    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Game.showGameSettingsWindow();
    }

    private double setPaneSize() {
        double hight;
        if (size < 9) {
            hight = 175;
        } else if (size < 15) {
            hight = 140;
        } else if (size < 20) {
            hight = 115;
        } else if (size < 25) {
            hight = 100;
        } else {
            hight = 80;
        }
        return hight;
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
        }
        if (Game.logicController.getCountThreeShip() == 0) {
            BoxThree.setDisable(true);
            if (currentShip == 3) {
                determineNewChoosenShip();
            }
        }
        if (Game.logicController.getCountFourShip() == 0) {
            BoxFour.setDisable(true);
            if (currentShip == 4) {
                determineNewChoosenShip();
            }
        }
        if (Game.logicController.getCountFiveShip() == 0) {
            BoxFive.setDisable(true);
            if (currentShip == 5) {
                determineNewChoosenShip();
            }
        }
    }

    private void determineNewChoosenShip() {
        for (int i = 2; i <= 5; i++) {
            switch (i) {
                case 2:
                    if (currentShip != 2 && !BoxTwo.isDisabled()) {
                        chooseShip(2);
                    }
                    break;
                case 3:
                    if (currentShip != 3 && !BoxThree.isDisabled()) {
                        chooseShip(3);
                    }
                    break;
                case 4:
                    if (currentShip != 4 && !BoxFour.isDisabled()) {
                        chooseShip(4);
                    }
                    break;
                case 5:
                    if (currentShip != 5 && !BoxFive.isDisabled()) {
                        chooseShip(5);
                    }
                    break;
            }
        }
    }

    private void hoverShip(Pane pane) {
        Pane newPane = null;
        noPlacingAllowed = false;
        try {
            HoverState[] states = Game.logicController.getHoverStateStatus(shipPartsList.indexOf(pane), currentShip, isHorizontal);

            for (int i = 0; i < states.length; i++) {
                if (states[i] != null) {
                    newPane = (Pane) shipPartsList.get(states[i].getIndex());

                    switch (states[i].getStatus()) {
                        case SHIP:
                            setColorPane(newPane, "black");
                            break;
                        case ERROR:
                            setColorPane(newPane, "red");
                            noPlacingAllowed = true;
                            break;
                        case CLOSE:
                            setColorPane(newPane, "#E0F8E6");
                            break;
                    }
                }
            }
        } catch (ShipOutofGame e) {


        }
    }

    private void placeShip(Pane pane) {
        if (noPlacingAllowed == false) {
            Game.logicController.placeShip(shipPartsList.indexOf(pane), currentShip, isHorizontal);
        }
        if (Game.logicController.allShipPlaced() == true) {
            Next.setDisable(false);
        }
    }

    private void resetShip(Pane pane) {
        if (Game.logicController.getGameElementStatus(shipPartsList.indexOf(pane)) == LogicConstants.GameElementStatus.SHIP) {


        }
    }

    private void redrawPanes() {
        for (int j = 0; j < shipPartsList.size(); j++) {
            Pane pane = (Pane) shipPartsList.get(j);

            switch (Game.logicController.getGameElementStatus(j)) {
                case SHIP:
                    setColorPane(pane, "black");
                    break;
                case CLOSE:
                    setColorPane(pane, "#E0F8E6");
                    break;
                default: // WATER
                    setColorPane(pane, "white");
                    break;
            }

        }
    }

    private void setColorPane(Pane pane, String color) {
        pane.setStyle("-fx-background-color: " + color + ";" + " -fx-border-color: #999898;");
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
            box.setStyle("-fx-border-color: black ;");
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

    private Pane[] getShipParts(Pane pane) {
        Pane[] shipParts = new Pane[currentShip];
        int shipIndex = shipPartsList.indexOf(pane);
        if (shipIndex != 0 || currentShip == 2) {

            switch (currentShip) {
                case 2:
                    if (isHorizontal == false) {
                        if ((shipIndex + size) < (size * size)) {
                            shipParts[0] = pane;
                            shipParts[1] = (Pane) shipPartsList.get(shipIndex + size);
                        }
                    } else {
                        if ((shipIndex + 1) % size > 0) {
                            shipParts[0] = pane;
                            shipParts[1] = (Pane) shipPartsList.get(shipIndex + 1);
                        }
                    }
                    break;
                case 3:
                    if (isHorizontal == false) {
                        if ((shipIndex - size) >= 0 && (shipIndex + size) < (size * size)) {
                            shipParts[0] = (Pane) shipPartsList.get(shipIndex - size);
                            shipParts[1] = pane;
                            shipParts[2] = (Pane) shipPartsList.get(shipIndex + size);
                        }
                    } else {
                        if ((shipIndex - 1) % size < (size - 1) && (shipIndex + 1) % size > 0) {
                            shipParts[0] = (Pane) shipPartsList.get(shipIndex - 1);
                            shipParts[1] = pane;
                            shipParts[2] = (Pane) shipPartsList.get(shipIndex + 1);
                        }
                    }
                    break;
                case 4:
                    if (isHorizontal == false) {
                        if ((shipIndex - size) >= 0 && (shipIndex + size < (size * size))
                                && (shipIndex + (2 * size) < (size * size))) {
                            shipParts[0] = (Pane) shipPartsList.get(shipIndex - size);
                            shipParts[1] = pane;
                            shipParts[2] = (Pane) shipPartsList.get(shipIndex + size);
                            shipParts[3] = (Pane) shipPartsList.get(shipIndex + (2 * size));
                        }
                    } else {
                        if ((shipIndex - 1) % size < (size - 1) && (shipIndex + 1) % size > 0
                                && (shipIndex + 2) % size > 0) {
                            shipParts[0] = (Pane) shipPartsList.get(shipIndex - 1);
                            shipParts[1] = pane;
                            shipParts[2] = (Pane) shipPartsList.get(shipIndex + 1);
                            shipParts[3] = (Pane) shipPartsList.get(shipIndex + 2);
                        }
                    }
                    break;

                case 5:
                    if (isHorizontal == false) {
                        if ((shipIndex - size) >= 0 && (shipIndex - (2 * size)) >= 0 &&
                                (shipIndex + size < (size * size)) && (shipIndex + (2 * size) < (size * size))) {
                            shipParts[0] = (Pane) shipPartsList.get(shipIndex - (2 * size));
                            shipParts[1] = (Pane) shipPartsList.get(shipIndex - size);
                            shipParts[2] = pane;
                            shipParts[3] = (Pane) shipPartsList.get(shipIndex + size);
                            shipParts[4] = (Pane) shipPartsList.get(shipIndex + (2 * size));
                        }
                    } else {
                        if ((shipIndex - 1) % size < (size - 1) && ((shipIndex + size - 2) % size) < (size - 2) &&
                                (shipIndex + 1) % size > 0 && (shipIndex + 2) % size > 0) {
                            shipParts[0] = (Pane) shipPartsList.get(shipIndex - 2);
                            shipParts[1] = (Pane) shipPartsList.get(shipIndex - 1);
                            shipParts[2] = pane;
                            shipParts[3] = (Pane) shipPartsList.get(shipIndex + 1);
                            shipParts[4] = (Pane) shipPartsList.get(shipIndex + 2);
                        }
                    }
                    break;
            }
        }
        return shipParts;
    }


}
