package Logic.Game;

import Logic.Game.Exceptions.FalsePlayerType;
import Logic.main.Ship;
import Utilities.HoverState;

import java.util.ArrayList;

import static Logic.main.LogicConstants.*;

public class MyGame extends Game {


    public MyGame() {
        super();
    }

    @Override
    public void setName(String n) {
        super.setName(n);
        try {
            this.player = createPlayer(n, PlayerType.SELF);
        } catch (FalsePlayerType e) {
            System.out.println("Falscher Spielertyp");
        }
    }





}
