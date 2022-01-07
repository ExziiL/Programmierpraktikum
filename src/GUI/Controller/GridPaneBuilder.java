package GUI.Controller;

import GUI.GUIConstants;
import GUI.Game;
import Utilities.HoverState;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GridPaneBuilder {
    private int size;
    private ObservableList shipPartsGamerList;
    private ObservableList shipPartsEnemyList;
    private ObservableList shipPartsPlacingList;
    private ObservableList<Node> numberLables;
    private ObservableList<Node> letterLables;

    public GridPaneBuilder(int size) {
        this.size = size;
    }

    public GridPane createTableEnemy(GridPane tableEnemy) {

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
           //if (i == 0) {
           //    column++;
           //    continue;
           //}
           //if (i % (size + 1) == 0) {
           //    String b = Integer.toString(row - 1);
           //    Label verticallLabel = new Label(b);
           //    verticallLabel.setMinWidth(15);
           //    verticallLabel.setAlignment(Pos.CENTER_RIGHT);
           //    if (size > 20) {
           //        verticallLabel.setFont(Font.font(10));
           //    } else if (size > 25) {
           //        verticallLabel.setFont(Font.font(0.5));
           //    }
           //    tableEnemy.add(verticallLabel, column++, row);
           //} else if (row == 0) {
           //    String b = Integer.toString(column - 1);
           //    Label horizonatlLabel = new Label(b);
           //    horizonatlLabel.setPrefWidth(paneSize);
           //    horizonatlLabel.setAlignment(Pos.CENTER);
           //    if (size > 20) {
           //        horizonatlLabel.setFont(Font.font(1));
           //    } else if (size > 25) {
           //        horizonatlLabel.setFont(Font.font(0.5));
           //    }
           //    tableEnemy.add(horizonatlLabel, column++, row);
           //} else {
                pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
                pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
                pane.setPrefWidth(paneSize);
                pane.setPrefHeight(paneSize);
                pane.setId("field" + row + column);
                tableEnemy.add(pane, column++, row);
            //}
            // GridPane.setMargin(pane, new Insets(0.5, 0.5, 0.5, 0.5));

            // set Gridpane Hights
            //tableEnemy.setPrefHeight(Region.USE_COMPUTED_SIZE);
            //tableEnemy.setMinHeight(Region.USE_COMPUTED_SIZE);
            //tableEnemy.setMaxHeight(Region.USE_PREF_SIZE);

            // set Gridpane Widths
            //tableEnemy.setPrefWidth(Region.USE_COMPUTED_SIZE);
            //tableEnemy.setMinWidth(Region.USE_COMPUTED_SIZE);
            //tableEnemy.setMaxWidth(Region.USE_PREF_SIZE);

        }
        // Save Grid List
        shipPartsEnemyList = tableEnemy.getChildren().filtered(node -> node instanceof Pane);
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
            //if (i == 0) {
            //    column++;
            //    continue;
            //}
            //if (i % (size + 1) == 0) {
            //    String b = Integer.toString(row - 1);
            //    Label verticallLabel = new Label(b);
            //    verticallLabel.setMinWidth(15);
            //    verticallLabel.setAlignment(Pos.CENTER_RIGHT);
            //    if (size > 20) {
            //        verticallLabel.setFont(Font.font(10));
            //    } else if (size > 25) {
            //        verticallLabel.setFont(Font.font(0.5));
            //    }
            //    tableGamer.add(verticallLabel, column++, row);
            //} else if (row == 0) {
            //    String b = Integer.toString(column - 1);
            //    Label horizonatlLabel = new Label(b);
            //    horizonatlLabel.setPrefWidth(paneSize);
            //    horizonatlLabel.setAlignment(Pos.CENTER);

            //    if (size > 20) {
            //        horizonatlLabel.setFont(Font.font(1));
            //    } else if (size > 25) {
            //        horizonatlLabel.setFont(Font.font(0.5));
            //    }
            //    tableGamer.add(horizonatlLabel, column++, row);
            //} else {
            pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
            pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);
            pane.setId("field" + row + column);
            tableGamer.add(pane, column++, row);
            // }

            // GridPane.setMargin(pane, new Insets(0.5, 0.5, 0.5, 0.5));

            // set Gridpane Hights
            //tableGamer.setPrefHeight(Region.USE_COMPUTED_SIZE);
            //tableGamer.setMinHeight(Region.USE_COMPUTED_SIZE);
            //tableGamer.setMaxHeight(Region.USE_PREF_SIZE);

            // set Gridpane Widths
            //tableGamer.setPrefWidth(Region.USE_COMPUTED_SIZE);
            //tableGamer.setMinWidth(Region.USE_COMPUTED_SIZE);
            //tableGamer.setMaxWidth(Region.USE_PREF_SIZE);

        }
        // Save Grid List
        shipPartsGamerList = tableGamer.getChildren().filtered(node -> node instanceof Pane);
        redrawPanesWithoutClose(shipPartsGamerList);

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
                pane.setStyle("-fx-background-color: " + GUIConstants.colorGameField + ";");
                pane.setStyle("-fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
                pane.setPrefSize(paneSize, paneSize);
                pane.setId("field" + row + column);
                tablePlacing.add(pane, column++, row);
            }

            // GridPane.setMargin(pane, new Insets(0.5, 0.5, 0.5, 0.5));

            // set Gridpane Hights
            // tablePlacing.setPrefHeight(Region.USE_COMPUTED_SIZE);
            // tablePlacing.setMinHeight(Region.USE_COMPUTED_SIZE);
            // tablePlacing.setMaxHeight(Region.USE_PREF_SIZE);

            // set Gridpane Widths
            // tablePlacing.setPrefWidth(Region.USE_COMPUTED_SIZE);
            // tablePlacing.setMinWidth(Region.USE_COMPUTED_SIZE);
            // tablePlacing.setMaxWidth(Region.USE_PREF_SIZE);

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

    public boolean hoverShip(HoverState[] states) {
        Pane newPane = null;
        boolean noPlacingAllowed = false;

        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                newPane = (Pane) shipPartsPlacingList.get(states[i].getIndex());

                switch (states[i].getStatus()) {
                    case SHIP:
                        setColorPane(newPane, GUIConstants.colorShip);
                        break;
                    case ERROR:
                        setColorPane(newPane, GUIConstants.colorError);
                        noPlacingAllowed = true;
                        break;
                    case CLOSE:
                        setColorPane(newPane, GUIConstants.colorClose);
                        break;
                }
            }
        }
        return noPlacingAllowed;
    }

    private double setPaneSize() {
        double height;
        if (size < 9) {
            height = 93;
        } else if (size < 15) {
            height = 31;
        } else if (size < 20) {
            height = 24;
        } else if (size < 25) {
            height = 19;
        } else {
            height = 15;
        }
        return height;
    }

    private void redrawPanes(ObservableList list) {
        for (int j = 0; j < list.size(); j++) {
            Pane pane = (Pane) list.get(j);

            switch (Game.logicController.getGameElementStatus(j)) {
                case SHIP:
                    setColorPane(pane, GUIConstants.colorShip);
                    break;
                case CLOSE:
                    setColorPane(pane, GUIConstants.colorClose);
                    break;
                default: // WATER
                    setColorPane(pane, GUIConstants.colorGameField);
                    break;
            }
        }
    }

    private void redrawPanesWithoutClose(ObservableList list) {
        for (int j = 0; j < list.size(); j++) {
            Pane pane = (Pane) list.get(j);

            switch (Game.logicController.getGameElementStatus(j)) {
                case SHIP:
                    setColorPane(pane, GUIConstants.colorShip);
                    break;
                default: // WATER
                    setColorPane(pane, GUIConstants.colorGameField);
                    break;
            }
        }
    }

    private void setColorPane(Pane pane, String color) {
        pane.setStyle("-fx-background-color: " + color + ";" + " -fx-border-color: " + GUIConstants.colorGameFieldBorder + ";");
    }

}
