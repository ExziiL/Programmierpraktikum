package GUI.Controller;

import GUI.GUIConstants;
import GUI.Game;
import Utilities.HoverState;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class GridPaneBuilder {
    private final int size;
    private ObservableList<Node> shipPartsGamerList;
    private ObservableList<Node> shipPartsEnemyList;
    private ObservableList<Node> shipPartsPlacingList;
    private ObservableList<Node> numberLables;
    private ObservableList<Node> letterLables;

    private final Image error = new Image("assets/neueSchiffe/error2.png");
    private final Image water = new Image("assets/neueSchiffe/smallNormalGridPaneBorder2.png");
    private final Image close = new Image("assets/neueSchiffe/schiff_border.png");

    private final Image twoShipFirstHor = new Image("assets/neueSchiffe/2er_oben_vertikal2_small.png");
    private final Image twoShipSecondHor = new Image("assets/neueSchiffe/schiff_schwarz.png");
    private final Image twoShipFirstVert = new Image("assets/neueSchiffe/2er_oben_vertikal2_small.png");
    private final Image twoShipSecondVert = new Image("assets/neueSchiffe/2er_unten_vertikal.png");

    private final Image shipBlack = new Image("assets/neueSchiffe/schiff_schwarz.png");

    public GridPaneBuilder(int size) {
        this.size = size;
    }

    public GridPane createTableEnemy(GridPane tableEnemy, PlayingFieldController controller) {

        int column = 0;
        int row = 0;
        double paneSize = setPaneSize();

        // Build up Grid pane and set Events for Panes
        for (int i = 0; i < size * size; i++) {
            Pane pane = new Pane();

            if (column == size) {
                column = 0;
                row++;
            }
            // if (i == 0) {
            // column++;
            // continue;
            // }
            // if (i % (size + 1) == 0) {
            // String b = Integer.toString(row - 1);
            // Label verticallLabel = new Label(b);
            // verticallLabel.setMinWidth(15);
            // verticallLabel.setAlignment(Pos.CENTER_RIGHT);
            // if (size > 20) {
            // verticallLabel.setFont(Font.font(10));
            // } else if (size > 25) {
            // verticallLabel.setFont(Font.font(0.5));
            // }
            // tableEnemy.add(verticallLabel, column++, row);
            // } else if (row == 0) {
            // String b = Integer.toString(column - 1);
            // Label horizonatlLabel = new Label(b);
            // horizonatlLabel.setPrefWidth(paneSize);
            // horizonatlLabel.setAlignment(Pos.CENTER);
            // if (size > 20) {
            // horizonatlLabel.setFont(Font.font(1));
            // } else if (size > 25) {
            // horizonatlLabel.setFont(Font.font(0.5));
            // }
            // tableEnemy.add(horizonatlLabel, column++, row);
            // } else {
            pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
            pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);
            pane.setId("field" + row + column);
            tableEnemy.add(pane, column++, row);
            //}
            // Events
            pane.setOnMouseEntered(event -> setColorPane(pane, GUIConstants.colorShotMarker));

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
        double paneSize = setPaneSize();

        // Build up Grid pane and set Events for Panes
        for (int i = 0; i < (size) * (size); i++) {
            Pane pane = new Pane();

            if (column == (size)) {
                column = 0;
                row++;
            }
            // if (i == 0) {
            // column++;
            // continue;
            // }
            // if (i % (size + 1) == 0) {
            // String b = Integer.toString(row - 1);
            // Label verticallLabel = new Label(b);
            // verticallLabel.setMinWidth(15);
            // verticallLabel.setAlignment(Pos.CENTER_RIGHT);
            // if (size > 20) {
            // verticallLabel.setFont(Font.font(10));
            // } else if (size > 25) {
            // verticallLabel.setFont(Font.font(0.5));
            // }
            // tableGamer.add(verticallLabel, column++, row);
            // } else if (row == 0) {
            // String b = Integer.toString(column - 1);
            // Label horizonatlLabel = new Label(b);
            // horizonatlLabel.setPrefWidth(paneSize);
            // horizonatlLabel.setAlignment(Pos.CENTER);

            // if (size > 20) {
            // horizonatlLabel.setFont(Font.font(1));
            // } else if (size > 25) {
            // horizonatlLabel.setFont(Font.font(0.5));
            // }
            // tableGamer.add(horizonatlLabel, column++, row);
            // } else {

            pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
            pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);
            pane.setId("field" + row + column);
            tableGamer.add(pane, column++, row);
            // }
        }
        // Save Grid List
        shipPartsGamerList = tableGamer.getChildren().filtered(node -> node instanceof Pane);

        redrawGamerPanes();

        return tableGamer;
    }

    public GridPane createTablePlacingField(GridPane tablePlacing, PlacingFieldController controller) {
        int column = 0;
        int row = 0;
        double paneSize = setPaneSize();

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

    private double setPaneSize() {
        double height;
        if (size < 9) {
            height = 85;
        } else if (size < 15) {
            height = 40;
        } else if (size < 20) {
            height = 31;
        } else if (size < 25) {
            height = 24;
        } else {
            height = 19;
        }
        return height;
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
            image.setImage(shipBlack);
            //  if (isHorizontal) {
            //      switch (shipSize) {
            //          case 2:
            //              switch (part) {
            //                  case 1:
            //                      image.setImage(twoShipFirstHor);
            //                      break;
            //                  case 2:
            //                      image.setImage(twoShipSecondHor);
            //                      break;
            //              }
            //              break;
            //      }
            //  } else {
            //      switch (shipSize) {
            //          case 2:
            //              switch (part) {
            //                  case 1:
            //                      image.setImage(twoShipFirstVert);
            //                      break;
            //                  case 2:
            //                      image.setImage(twoShipSecondVert);
            //                      break;
            //              }
            //              break;
            //      }
            //  }
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
            image.setImage(close);
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
        ImageView pictureOfShip = null;
        for (Node e : children) {
            if (e.getClass() == ImageView.class) {
                return (ImageView) e;
            }
        }
        return null;
    }

}
