package GUI.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.input.*;

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

    private int size = App.logicController.getGameSize();

    private ObservableList children;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 0;
        double paneSize = setPaneSize();
        for (int i = 0; i < size * size; i++) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #66CDAA;");
            pane.setPrefWidth(paneSize);
            pane.setPrefHeight(paneSize);
            pane.setId("field" + row + column);

            if (column == size) {
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

             pane.setOnMouseEntered(event -> {
                placingShips(pane);
            });

            pane.setOnMouseExited(event -> {
                unplacingShips(pane);
            });
        }

        children = table.getChildren();
        setLabelTexts();
    }

    @FXML
    public void handleMouseRightClicked(ActionEvent event) throws  IOException{

      //  if (event.getButton() == MouseEvent.BUTTON2){
      //      System.out.println("Rechte Maustatste");
      //  }
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        App.zeigeSpieleinstellungen();
    }

    private double setPaneSize() {
        double hight;
        double width;
        if (size < 9) {
            hight = 175;
            width = 175;
        } else if (size < 15) {
            hight = 140;
            width = 140;
        } else if (size < 20) {
            hight = 115;
            width = 115;
        } else if (size < 25) {
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

    private int[] placingShips(Pane pane) {
        int shipIndex = children.indexOf(pane);
        if ((shipIndex - size) >= 0 && (shipIndex + size) < (size * size )) {
            Pane bug = (Pane) children.get(shipIndex - size);
            Pane back = (Pane) children.get(shipIndex + size);

            // check
            setColorPane(pane, "#FF0000");
            setColorPane(bug, "#FF0000");
            setColorPane(back, "#FF0000");
        }
        //gib indexe zurück
        return null;
    }
    private void unplacingShips(Pane pane){
        int shipIndex = children.indexOf(pane);
        if ((shipIndex - size) >= 0 && (shipIndex + size) < (size * size)) {
            Pane bug = (Pane) children.get(shipIndex - size);
            Pane back = (Pane) children.get(shipIndex + size);

            setColorPane(pane, "#66CDAA");
            setColorPane(bug, "#66CDAA");
            setColorPane(back, "#66CDAA");
        }
    }

    private void setColorPane(Pane pane, String color){
        pane.setStyle("-fx-background-color: " + color);
    }
}

