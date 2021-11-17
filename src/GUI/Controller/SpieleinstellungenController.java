package GUI.Controller;

import GUI.Spieleinstellungen.Spieleinstellungen;
import Logic.main.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import GUI.App;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SpieleinstellungenController {
    private App app;
    private int[] spielfeldGroessenWerte = new int[26];

    @FXML
    private Button weiter;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> spielfeldgroesse;

    @FXML
    void initialize() {
        for (int i = 0; i < spielfeldGroessenWerte.length; i++) {
            int x = i + 5;
            spielfeldGroessenWerte[i] = x;
            spielfeldgroesse.getItems().add(x + " x " + x);
        }
        spielfeldgroesse.getSelectionModel().selectFirst();

    }

    @FXML
    void zurueck(ActionEvent event) throws IOException {
        app.zeigeSpielStarten(); // Wieso ist heis app klein geschrieben und weiter unten Groß?
    }

    @FXML
    void nameEingabe(ActionEvent event) throws IOException {

    }

    @FXML
    void handle_weiter(ActionEvent event) throws IOException {

        App.zeigePlatzierfeld();

        App.logicController.setName(name.getCharacters().toString());
        App.logicController
                .setSpielfeldGroesse(spielfeldGroessenWerte[spielfeldgroesse.getSelectionModel().getSelectedIndex()]);
    }

}
