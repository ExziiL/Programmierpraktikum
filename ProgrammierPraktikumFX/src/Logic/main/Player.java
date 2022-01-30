package Logic.main;

//import java.awt.*;

import Logic.Game.Game;
import Network.Network;

public abstract class Player {

    //region Variables
    protected Game game;
    protected Network netplay;
    //endregion

    /**
     * Constructor
     *
     * @param game Object of one of the Subclasses of Player
     */
    public Player(Game game) {
        this.game = game;
    }

    /**
     * Basic shoot-Methode that is use in the OfflineGame. The game Object checks if on the
     * Pane on the (x,y)Coordinate is a Ship or Nothing.
     *
     * @param x Coordinate
     * @param y Coordinate
     * @return if a Ship is Hit, Sunk or Missed
     */
    public int shoot(int x, int y) {
        // 0 = hit
        // 1 = miss
        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.SHIP) {

            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return 0;
        } else if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {
            return 0;
        } else {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
            return 1;

        }
    }

    public abstract int takeTurn();
}
