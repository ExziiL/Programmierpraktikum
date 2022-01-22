package Logic.main;

import Logic.Game.Game;
import Network.*;

public class OnlinePlayer extends Player {

    public OnlinePlayer(Game game) {
        super(game);
    }

    @Override
    public boolean takeTurn() {
        int[] xy = netplay.getShotAt();

        if (game.getgameElementStatus(xy[0], xy[1]) == LogicConstants.GameElementStatus.MISS) {
            netplay.sendAnswer(0);
        } else if (game.getgameElementStatus(xy[0], xy[1]) == LogicConstants.GameElementStatus.SHIP) {
            netplay.sendAnswer(1);
        } else if (game.isShipDestroyed(xy[0], xy[1]) > 0) {
            netplay.sendAnswer(2);
        }
        return false;
    }

    @Override
    public boolean shoot(int x, int y) {
        netplay = Network.getNetplay();
        int isHit = netplay.shoot(x, y);

        if (isHit == 0) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
        } else if (isHit == 1 || isHit == 2) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return true;
        } else {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.ERROR);
        }
        return false;
    }
}
