package Logic.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import GUI.*;


public class Controller {
    public Controller() {
    }

    public void SpielLaden() {

    }

    public void SpielStarten() {

    }

    public void SpielSpeichern() {
    }

    public void Einstellungen() {
    }

    @FXML
    private Button start;
    @FXML
    private Button einstellungen;
    @FXML
    private Button laden;
    @FXML
    private Button beenden;


    @FXML
    private void initialize() {
        start.setOnAction(event ->
            System.out.println("Erfolg!")
        );

        einstellungen.setOnAction(event -> {

        });

        laden.setOnAction(event -> {

        });

        beenden.setOnAction(event ->
            System.exit(0)
        );

    }

}