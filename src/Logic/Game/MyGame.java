package Logic.Game;

import Logic.Game.Exceptions.FalsePlayerType;
import Logic.main.Ship;

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


    @Override
    public boolean checkPlaceForShip(Ship ship, int x, int y, boolean vertical) {

        switch (ship.getSize()) {
            case 2:
                return checkPlaceForTwoShip(x, y, vertical);
            case 3:
            case 4:
            case 5:
        }

        return true;
    }

    public boolean checkPlaceForTwoShip(int x, int y, boolean vertical) {
   //   if (vertical == true) {


   //   } else {

   //   }
        return true;
    }
}
