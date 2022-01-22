package GUI.Controller;

import GUI.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {

    @FXML
    private Label LabelEnd;
    @FXML
    private ImageView PictureEnd;
    @FXML
    private Button NewGame;
    @FXML
    private Button Exit;

    Image winner = new Image("assets/End/Winner.png");
    Image loser = new Image("assets/End/Loser.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (Game.logicController.isConcratulation()) {

            LabelEnd.setText("Glückwunsch " + Game.logicController.getName());
            PictureEnd.setImage(winner);


        } else {
            LabelEnd.setText("Du hast verloren LOOOSER " + Game.logicController.getName());
            PictureEnd.setImage(loser);
        }

        NewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Game.showGameSettingsWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game.logicController.exitGame();
            }
        });

    }

}
