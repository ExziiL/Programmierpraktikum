package Logic.main;

//import java.awt.*;

import Logic.Game.Game;
import Network.Network;

public abstract class Player { //TODO Könnten über Unterklasse bestimmen ob determineNumberofShips ausgeführt wird oder nicht

    protected Game game;
    protected Network netplay;

    public Player(Game game) {
        this.game = game;
    }

    public int shoot(int x, int y) {
        // 0 = miss
        // 1 = hit
        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.SHIP) {

            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return 1;
        } else if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {
            return 1;
        } else {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
            return 0;

        }
    }

    public abstract int takeTurn();
}
