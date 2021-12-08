package GUI.Controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlatzierfeldController implements Initializable {
    @FXML
    private Button zurueck; // braucht man das?
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

    int groesse = App.logicController.getGameSize();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 0;
        double paneSize = setPaneSize();
        for (int i = 0; i < groesse * groesse; i++) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #66CDAA;");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);
            pane.setId("field" + row + column);

            pane.setOnMouseEntered(event -> {
                pane.setStyle("-fx-background-color: #FF0000;");
                placingships(pane);
            });

            pane.setOnMouseExited(event -> {
                table.getChildren();
                pane.setStyle("-fx-background-color: #66CDAA;");
            });
            if (column == groesse) {
                column = 0;
                row++;
            }
            table.add(pane, column++, row);

            GridPane.setMargin(pane, new Insets(0.5, 0.5, 0.5, 0.5));

            // set Gridpane Hights
            table.setPrefHeight(Region.USE_COMPUTED_SIZE);
            table.setMinHeight(Region.USE_COMPUTED_SIZE);
            table.setMaxHeight(Region.USE_PREF_SIZE);

            // set Gridpane Widths
            table.setPrefWidth(Region.USE_COMPUTED_SIZE);
            table.setMinWidth(Region.USE_COMPUTED_SIZE);
            table.setMaxWidth(Region.USE_PREF_SIZE);
        }

        setLabelTexts();
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        App.zeigeSpieleinstellungen();
    }

    @FXML
    void handlehovern(MouseEvent event) throws IOException {

    }

    private double setPaneSize() {
        double hight;
        double width;
        if (groesse < 9) {
            hight = 175;
            width = 175;
        } else if (groesse < 15) {
            hight = 140;
            width = 140;
        } else if (groesse < 20) {
            hight = 115;
            width = 115;
        } else if (groesse < 25) {
            hight = 100;
            width = 100;
        } else {
            hight = 80;
            width = 80;
        }
        return hight;
    }

    private void setLabelTexts() {
        labelTwo.setText(App.logicController.getCountTwoShip() + "x");
        labelThree.setText(App.logicController.getCountThreeShip() + "x");
        labelFour.setText(App.logicController.getCountFourShip() + "x");
        labelFive.setText(App.logicController.getCountFiveShip() + "x");

    }

    private int[] placingships(Pane pane){
        ObservableList children = table.getChildren();

        int shipind = children.indexOf(pane);
        Pane bug = (Pane) children.get(shipind - 1);
        Pane back = (Pane) children.get(shipind + 1);

        bug.setStyle("-fx-background-color: #FF0000");
        back.setStyle("-fx-background-color: #FF0000");

        return null;
    }
}

