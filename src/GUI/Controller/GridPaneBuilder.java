package GUI.Controller;

import GUI.GUIConstants;
import GUI.Game;
import Utilities.HoverState;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class GridPaneBuilder {
    //region Variables
    private final int size;
    private ObservableList<Node> shipPartsGamerList;
    private ObservableList<Node> shipPartsEnemyList;
    private ObservableList<Node> shipPartsPlacingList;
    private ObservableList<Node> numberLables;
    private ObservableList<Node> letterLables;
    //endregion

    //region Images
    private Image error;
    private Image water;
    private Image nearShip;
    private Image ship;

    public Image error25;
    public Image error60;
    public Image error100;
    public Image water25;
    public Image water60;
    public Image water100;

    public Image nearShip25;
    public Image nearShip60;
    public Image nearShip100;

    public Image ship25;
    public Image ship60;
    public Image ship100;
    //endregion

    public GridPaneBuilder(int size, Image water, Image ship, Image nearShip, Image error) {
        this.size = size;
        this.water = water;
        this.ship = ship;
        this.nearShip = nearShip;
        this.error = error;

        error25 = new Image("assets/newShips/error25.png");
        error60 = new Image("assets/newShips/error60.png");
        error100 = new Image("assets/newShips/error100.png");

        water25 = new Image("assets/newShips/water25.png");
        water60 = new Image("assets/newShips/water60.png");
        water100 = new Image("assets/newShips/water100.png");

        nearShip25 = new Image("assets/newShips/nearShip25.png");
        nearShip60 = new Image("assets/newShips/nearShip60.png");
        nearShip100 = new Image("assets/newShips/nearShip100.png");

        ship25 = new Image("assets/newShips/ship25.png");
        ship60 = new Image("assets/newShips/ship60.png");
        ship100 = new Image("assets/newShips/ship100.png");


        // ! Größen im Konstruktor setzen
        if (size >= 25) {
            this.water = water25;
            this.ship = ship25;
            this.nearShip = nearShip25;
            this.error = error25;
        } else if (size >= 13) {
            this.water = water60;
            this.ship = ship60;
            this.nearShip = nearShip60;
            this.error = error60;
        } else {
            this.water = water100;
            this.ship = ship100;
            this.nearShip = nearShip100;
            this.error = error100;
        }
    }

    public GridPane createTableEnemy(GridPane tableEnemy, PlayingFieldController controller) {

        int column = 0;
        int row = 0;
        double paneSize = tableEnemy.getPrefHeight() / Game.logicController.getGameSize();

        // Build up Grid pane and set Events for Panes
        for (int i = 0; i < size * size; i++) {
            Pane pane = new Pane();

            if (column == size) {
                column = 0;
                row++;
            }
            if (i == 0) {
                column++;
                continue;
            }
            if (i % (size) == 0) {
                String b = Integer.toString(row - 1);
                Label verticallLabel = new Label(b);
                verticallLabel.setMinWidth(15);
                verticallLabel.setAlignment(Pos.CENTER_RIGHT);
                if (size > 20) {
                    verticallLabel.setFont(Font.font(10));
                } else if (size > 25) {
                    verticallLabel.setFont(Font.font(0.5));
                }
                tableEnemy.add(verticallLabel, column++, row);
            } else if (row == 0) {
                String b = Integer.toString(column - 1);
                Label horizonatlLabel = new Label(b);
                horizonatlLabel.setPrefWidth(paneSize);
                horizonatlLabel.setAlignment(Pos.CENTER);
                if (size > 20) {
                    horizonatlLabel.setFont(Font.font(1));
                } else if (size > 25) {
                    horizonatlLabel.setFont(Font.font(0.5));
                }
                tableEnemy.add(horizonatlLabel, column++, row);
            } else {
                pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
                pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
                pane.setPrefWidth(paneSize);
                pane.setPrefHeight(paneSize);
                pane.setId("field" + row + column);
                tableEnemy.add(pane, column++, row);
            }
            // Events
            pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (controller.isYourTurn()) {
                        setColorPane(pane, GUIConstants.colorShotMarker);
                    }
                }
            });


            pane.setOnMouseExited(event -> redrawEnemyPanes());

            pane.setOnMouseClicked(event -> {

                controller.handleSetOnMouseClicked(event, shipPartsEnemyList.indexOf(pane));


            });

        }
        // Save Grid List

        shipPartsEnemyList = tableEnemy.getChildren().filtered(node -> node instanceof Pane);

        redrawEnemyPanes();
        return tableEnemy;
    }

    public GridPane createTableGamer(GridPane tableGamer) {

        int column = 0;
        int row = 0;
        double paneSize = tableGamer.getPrefHeight() / Game.logicController.getGameSize();

        // Build up Grid pane and set Events for Panes
        for (int i = 0; i < (size) * (size); i++) {
            Pane pane = new Pane();

            if (column == (size)) {
                column = 0;
                row++;
            }
             if (i == 0) {
             column++;
             continue;
             }
             if (i % (size) == 0) {
             String b = Integer.toString(row - 1);
             Label verticallLabel = new Label(b);
             verticallLabel.setMinWidth(15);
             verticallLabel.setAlignment(Pos.CENTER_RIGHT);
             if (size > 20) {
             verticallLabel.setFont(Font.font(10));
             } else if (size > 25) {
             verticallLabel.setFont(Font.font(0.5));
             }
             tableGamer.add(verticallLabel, column++, row);
             } else if (row == 0) {
             String b = Integer.toString(column - 1);
             Label horizonatlLabel = new Label(b);
             horizonatlLabel.setPrefWidth(paneSize);
             horizonatlLabel.setAlignment(Pos.CENTER);

             if (size > 20) {
             horizonatlLabel.setFont(Font.font(1));
             } else if (size > 25) {
             horizonatlLabel.setFont(Font.font(0.5));
             }
             tableGamer.add(horizonatlLabel, column++, row);
             } else {

            pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
            pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);
            pane.setId("field" + row + column);
            tableGamer.add(pane, column++, row);
            }
        }
        // Save Grid List
        shipPartsGamerList = tableGamer.getChildren().filtered(node -> node instanceof Pane);

        redrawGamerPanes();

        return tableGamer;
    }

    public GridPane createTablePlacingField(GridPane tablePlacing, PlacingFieldController controller) {
        int column = 0;
        int row = 0;
        double paneSize = tablePlacing.getPrefHeight() / Game.logicController.getGameSize();

        // Build up Grid pane and set Events for Panes
        for (int i = 0; i < (size + 1) * (size + 1); i++) {
            Pane pane = new Pane();

            if (column == size + 1) {
                column = 0;
                row++;
            }
            if (i == 0) {
                column++;
                continue;
            }
            if (i % (size + 1) == 0) {
                String b = Integer.toString(row - 1);
                Label verticallLabel = new Label(b);
                verticallLabel.setMinWidth(15);
                verticallLabel.setAlignment(Pos.CENTER_RIGHT);
                if (size > 20) {
                    verticallLabel.setFont(Font.font(10));
                } else if (size > 25) {
                    verticallLabel.setFont(Font.font(0.5));
                }
                tablePlacing.add(verticallLabel, column++, row);
            } else if (row == 0) {
                String b = Integer.toString(column - 1);
                Label horizonatlLabel = new Label(b);
                horizonatlLabel.setPrefWidth(paneSize);
                horizonatlLabel.setAlignment(Pos.CENTER);

                if (size > 20) {
                    horizonatlLabel.setFont(Font.font(10));
                } else if (size > 25) {
                    horizonatlLabel.setFont(Font.font(0.5));
                }
                tablePlacing.add(horizonatlLabel, column++, row);
            } else {
                pane.setPrefSize(paneSize, paneSize);
                pane.setId("field" + row + column);
                ImageView image = new ImageView();
                image.setFitHeight(paneSize);
                image.setFitWidth(paneSize);
                pane.getChildren().add(image);
                tablePlacing.add(pane, column++, row);
            }

            pane.setOnMouseEntered(event -> {
                controller.handlePaneOnMouseEntered(pane);
            });

            pane.setOnMouseExited(event -> {
                redrawPanes(shipPartsPlacingList);
            });

            pane.setOnMouseClicked(event -> {
                controller.handlePaneOnMouseClicked(pane, event.getButton());
            });

        }
        // Save Grid List
        shipPartsPlacingList = tablePlacing.getChildren().filtered(node -> node instanceof Pane);
        return tablePlacing;
    }

    public void redrawPlacingField() {
        redrawPanes(shipPartsPlacingList);
    }

    public void redrawGamerPanes() {
        for (int j = 0; j < shipPartsGamerList.size(); j++) {
            Pane pane = (Pane) shipPartsGamerList.get(j);

            switch (Game.logicController.getGameElementStatus(j)) {
                case SHIP:
                    setColorPane(pane, GUIConstants.colorShip);
                    break;
                case HIT:
                    setColorPane(pane, GUIConstants.colorHit);
                    break;
                case MISS:
                    setColorPane(pane, GUIConstants.colorMiss);
                    break;
                default: // WATER
                    setColorPane(pane, GUIConstants.colorGameField);
                    break;
            }
        }
    }

    public void redrawEnemyPanes() {
        for (int j = 0; j < shipPartsEnemyList.size(); j++) {
            Pane pane = (Pane) shipPartsEnemyList.get(j);

            switch (Game.logicController.getEnemyElementStatus(j)) {
                case HIT:
                    setColorPane(pane, GUIConstants.colorHit);
                    break;
                case MISS:
                    setColorPane(pane, GUIConstants.colorMiss);
                    break;
                default: // WATER
                    setColorPane(pane, GUIConstants.colorGameField);
                    break;
            }
        }
    }

    public boolean hoverShip(HoverState[] states) {
        Pane pane = null;
        boolean noPlacingAllowed = false;

        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                pane = (Pane) shipPartsPlacingList.get(states[i].getIndex());

                switch (states[i].getStatus()) {
                    case SHIP:
                        setPictureofShip(pane, states[i].getShipSize(), states[i].getPart(), states[i].isHorizontal());
                        break;
                    case ERROR:
                        setPictureError(pane);
                        noPlacingAllowed = true;
                        break;
                    case CLOSE:
                        setPictureClose(pane);
                        break;
                    default:
                        break;
                }
            }
        }
        return noPlacingAllowed;
    }

    private void redrawPanes(ObservableList list) {
        for (int j = 0; j < list.size(); j++) {
            Pane pane = (Pane) list.get(j);

            switch (Game.logicController.getGameElementStatus(j)) {
                case SHIP:
                    setPictureofShip(pane, Game.logicController.getShipSize(j), Game.logicController.getPartofShip(j),
                            Game.logicController.isShipHorizontal(j));
                    break;
                case CLOSE:
                    setPictureClose(pane);
                    break;
                default: // WATER
                    setPictureWater(pane);
                    break;
            }
        }
    }

    private void setColorPane(Pane pane, String color) {
        pane.setStyle("-fx-background-color: " + color + ";" + " -fx-border-color: " + GUIConstants.colorGameFieldBorder
                + ";");
    }

    private void setPictureofShip(Pane pane, int shipSize, int part, boolean isHorizontal) {

        ImageView image = getImageViewOfPane(pane);
        if (image != null) {
            image.setImage(ship);
            // if (isHorizontal) {
            // switch (shipSize) {
            // case 2:
            // switch (part) {
            // case 1:
            // image.setImage(twoShipFirstHor);
            // break;
            // case 2:
            // image.setImage(twoShipSecondHor);
            // break;
            // }
            // break;
            // }
            // } else {
            // switch (shipSize) {
            // case 2:
            // switch (part) {
            // case 1:
            // image.setImage(twoShipFirstVert);
            // break;
            // case 2:
            // image.setImage(twoShipSecondVert);
            // break;
            // }
            // break;
            // }
            // }
        }

    }

    private void setPictureError(Pane pane) {
        ImageView image = getImageViewOfPane(pane);
        if (image != null) {
            image.setImage(error);
        }
    }

    private void setPictureClose(Pane pane) {
        ImageView image = getImageViewOfPane(pane);
        if (image != null) {
            image.setImage(nearShip);
        }
    }

    private void setPictureWater(Pane pane) {
        ImageView image = getImageViewOfPane(pane);
        if (image != null) {
            image.setImage(water);
        }
    }

    private ImageView getImageViewOfPane(Pane pane) {
        ObservableList<Node> children = pane.getChildren();
        // ImageView pictureOfShip = null;
        for (Node e : children) {
            if (e.getClass() == ImageView.class) {
                return (ImageView) e;
            }
        }
        return null;
    }

}
