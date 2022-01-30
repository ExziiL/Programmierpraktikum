package GUI.Controller;

import GUI.GUIConstants;
import GUI.Game;
import Logic.main.LogicConstants;
import Network.Client;
import Network.Network;
import Network.Server;
import Utilities.HoverState;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private Text textHeader;
    @FXML
    private ImageView Next;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView arrowTwo;
    @FXML
    private ImageView arrowThree;
    @FXML
    private ImageView arrowFour;
    @FXML
    private ImageView arrowFive;
    @FXML
    private Text Message;

    private GridPaneBuilder gridBuilder;
    private final int size = Game.logicController.getGameSize();
    private boolean isHorizontal = false;
    private boolean noPlacingAllowed = false;
    private boolean editMode = false;
    private boolean replaceShipMode = false;
    private int currentShip = 2;
    private Pane currentPane;
    private ObservableList shipPartsList;
    private Thread networkThread;
    private Network netplay;

    private final Image rightArrow = new Image("assets/Icons/angle-double-small-right.png");
    private final Image rightArrowDisabled = new Image("assets/Icons/angle-double-small-right-grey.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridBuilder = new GridPaneBuilder(size);

        table = gridBuilder.createTablePlacingField(table, this);

        // Event choose Ship
        BoxTwo.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(2);
        });
        BoxTwo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
                if (currentShip == 2) {
                    arrowTwo.setStyle("-fx-opacity: 1;");
                } else {
                    arrowTwo.setStyle("-fx-opacity: 0.3;");
                }
            }
        });
        BoxTwo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
                if (currentShip == 2) {
                    arrowTwo.setStyle("-fx-opacity: 1;");
                } else {
                    arrowTwo.setStyle("-fx-opacity: 0;");
                }
            }
        });

        BoxThree.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(3);
        });
        BoxThree.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
                if (currentShip == 3) {
                    arrowThree.setStyle("-fx-opacity: 1;");
                } else {
                    arrowThree.setStyle("-fx-opacity: 0.3;");
                }
            }
        });
        BoxThree.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
                if (currentShip == 3) {
                    arrowThree.setStyle("-fx-opacity: 1;");
                } else {
                    arrowThree.setStyle("-fx-opacity: 0;");
                }
            }
        });

        BoxFour.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(4);
        });
        BoxFour.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
                if (currentShip == 4) {
                    arrowFour.setStyle("-fx-opacity: 1;");
                } else {
                    arrowFour.setStyle("-fx-opacity: 0.3;");
                }
            }
        });
        BoxFour.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
                if (currentShip == 4) {
                    arrowFour.setStyle("-fx-opacity: 1;");
                } else {
                    arrowFour.setStyle("-fx-opacity: 0;");
                }
            }
        });

        BoxFive.setOnMouseClicked(event -> {
            setEditMode(false);
            chooseShip(5);
        });
        BoxFive.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(true);
                if (currentShip == 5) {
                    arrowFive.setStyle("-fx-opacity: 1;");
                } else {
                    arrowFive.setStyle("-fx-opacity: 0.3;");
                }
            }
        });
        BoxFive.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game.toggleCursorHand(false);
                if (currentShip == 5) {
                    arrowFive.setStyle("-fx-opacity: 1;");
                } else {
                    arrowFive.setStyle("-fx-opacity: 0;");
                }
            }
        });

        // Save Grid List
        shipPartsList = table.getChildren().filtered(node -> node instanceof Pane);

        // Build up chosen Ships Properties
        setChoosenShipProperties();

        if (currentShip != 0) {
            // Set Starting Ship
            if (Game.logicController.getCountTwoShip() != 0) {
                chooseShip(2);
            } else {
                chooseShip(3);
            }
        }

        if (Game.logicController.allShipPlaced()) {
            setNextActive(true);
        } else {
            setNextActive(false);
        }

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

            setChoosenShipProperties();
            setNextActive(true);
            chooseShip(0);
            setEditMode(true);
        });

        Clear.setOnAction(event -> {
            Game.logicController.initializeGameField();
            gridBuilder.redrawPlacingField();
            setChoosenShipProperties();
            setNextActive(false);
        });

        textLeftClick.setText(GUIConstants.explTextPlacingLeft);
        textRightClick.setText(GUIConstants.explTextPlacingRight);

        if (Game.logicController.getCountTwoShip() != 0) {
            chooseShip(2);
        } else {
            chooseShip(3);
        }

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
                    setArrowsAtShip(pane, false);
                    chooseShip(0);
                    gridBuilder.redrawPlacingField();
                } else if (Game.logicController.isElementShip(getIndexofPane(pane))) {
                    replaceShip(pane);
                    setReplaceShipMode(true);
                    setArrowsAtShip(pane, true);
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
            } else {
                rotateShip();
            }
        }
        setChoosenShipProperties();
    }

    @FXML
    public void handleBack() throws IOException {
        Game.showGameSettingsWindow();
    }

    @FXML
    public void handleNext(MouseEvent event) throws IOException {
        if (Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE) {
            Message.setText("Warte auf Spieler...");
            Message.setStyle("-fx-text-fill: green");
            networkThread = new Thread(() -> {
                netplay = Network.getNetplay();
                if (netplay instanceof Server) {
                    ((Server) netplay).sendREADY();
                }
                if (netplay instanceof Client) {
                    ((Client) netplay).receiveMessage();
                }
                Platform.runLater(() -> {
                    try {
                        Game.showPlayingFieldWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
            networkThread.start();
        } else {
            Game.showPlayingFieldWindow();
        }
    }

    private void setChoosenShipProperties() {
        labelTwo.setText(Game.logicController.getCountTwoShip() + "x");
        labelThree.setText(Game.logicController.getCountThreeShip() + "x");
        labelFour.setText(Game.logicController.getCountFourShip() + "x");
        labelFive.setText(Game.logicController.getCountFiveShip() + "x");

        if (Game.logicController.getCountTwoShip() == 0) {
            BoxTwo.setDisable(true);
            BoxTwo.setStyle("-fx-opacity: 0.3;");
            if (currentShip == 2) {
                determineNewChoosenShip();
            }
        } else {
            BoxTwo.setDisable(false);
            BoxTwo.setStyle("-fx-opacity: 1;");
        }
        if (Game.logicController.getCountThreeShip() == 0) {
            BoxThree.setDisable(true);
            BoxThree.setStyle("-fx-opacity: 0.3;");
            if (currentShip == 3) {
                determineNewChoosenShip();
            }
        } else {
            BoxThree.setDisable(false);
            BoxThree.setStyle("-fx-opacity: 1;");
        }
        if (Game.logicController.getCountFourShip() == 0) {
            BoxFour.setDisable(true);
            BoxFour.setStyle("-fx-opacity: 0.3;");
            if (currentShip == 4) {
                determineNewChoosenShip();
            }
        } else {
            BoxFour.setDisable(false);
            BoxFour.setStyle("-fx-opacity: 1;");
        }
        if (Game.logicController.getCountFiveShip() == 0) {
            BoxFive.setDisable(true);
            BoxFive.setStyle("-fx-opacity: 0.3;");
            if (currentShip == 5) {
                determineNewChoosenShip();
            }
        } else {
            BoxFive.setDisable(false);
            BoxFive.setStyle("-fx-opacity: 1;");
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
        try {
            Thread t = new Thread(() -> Platform.runLater(() -> {
                HoverState[] states = Game.logicController.getHoverStateStatus(getIndexofPane(pane), currentShip,
                        isHorizontal);
                if (Game.logicController
                        .getGameElementStatus(getIndexofPane(pane)) == LogicConstants.GameElementStatus.SHIP) {
                    Platform.runLater(() -> {
                        Game.toggleCursorHand(true);
                    });
                } else {
                    Game.toggleCursorHand(false);
                }

                noPlacingAllowed = gridBuilder.hoverShip(states);

            }));
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void placeShip(Pane pane) {
        try {
            if (!noPlacingAllowed) {
                Thread t = new Thread(
                        () -> Game.logicController.placeShip(getIndexofPane(pane), currentShip, isHorizontal));
                t.start();
                t.join();
            }
            if (Game.logicController.allShipPlaced()) {
                setNextActive(true);
                chooseShip(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void deleteShip(Pane pane) {
        if (Game.logicController.deleteShip(getIndexofPane(pane))) {
            setNextActive(false);
        }
    }

    private void replaceShip(Pane pane) {
        try {
            Thread t = new Thread(() -> {
                int shipSize = Game.logicController.getShipSize(getIndexofPane(pane));
                boolean isHorizontal = Game.logicController.isShipHorizontal(getIndexofPane(pane));
                deleteShip(pane);
                this.currentShip = shipSize;
                this.isHorizontal = isHorizontal;
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void rotateShip() {
        isHorizontal = !isHorizontal;
        gridBuilder.redrawPlacingField();
        hoverShip(currentPane);

    }

    private void unchooseActualShip() {
        ImageView selectedImg = getSelectedShipArrow(currentShip);

        if (getBoxShip(currentShip) != null) {
            selectedImg.setStyle("-fx-opacity: 0;");
        }
    }

    private void chooseShip(int ship) {
        unchooseActualShip();
        currentShip = ship;
        ImageView selectedImg = getSelectedShipArrow(ship);

        if (getBoxShip(ship) != null) {
            selectedImg.setStyle("-fx-opacity: 1;");
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

    private ImageView getSelectedShipArrow(int ship) {
        switch (ship) {
            case 2:
                return arrowTwo;
            case 3:
                return arrowThree;
            case 4:
                return arrowFour;
            case 5:
                return arrowFive;
            default:
                return null;
        }
    }

    private void setEditMode(boolean mode) {
        editMode = mode;
        EditShips.setSelected(mode);
        if (mode) {
            textHeader.setText("Schiffe bearbeiten");

        } else {
            textHeader.setText("Schiffe platzieren");
        }
        setHelpTexts();

    }

    private void setReplaceShipMode(boolean mode) {
        replaceShipMode = mode;
        if (mode) {
            Game.toggleCursorGrabHand(true);
        } else {
            Game.toggleCursorGrabHand(false);
        }
        setHelpTexts();
    }

    private void setArrowsAtShip(Pane pane, boolean active) {
        Game.logicController.getShipSize(getIndexofPane(pane));

        ImageView selectedImg = getSelectedShipArrow(currentShip);
        if (getSelectedShipArrow(currentShip) != null) {
            selectedImg.setStyle("-fx-opacity: 1;");
        }
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

    private void setNextActive(boolean active) {
        if (active) {
            Next.setImage(rightArrow);
            Next.setDisable(false);
        } else {
            Next.setImage(rightArrowDisabled);
            Next.setDisable(true);
        }
    }

    private int getIndexofPane(Pane pane) {
        return shipPartsList.indexOf(pane);
    }
}
