package Logic.main;

//import java.awt.*;

import Logic.Game.Game;

public abstract class Player { //TODO Könnten über Unterklasse bestimmen ob determineNumberofShips ausgeführt wird oder nicht

    protected Game game;


    public Player(Game game) {
        this.game = game;
    }

    public boolean shoot(int x, int y) {

        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.SHIP) {

            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return true;
        } else if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {

            return true;
        } else {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
            return false;

        }
    }

    public abstract boolean takeTurn();
}
