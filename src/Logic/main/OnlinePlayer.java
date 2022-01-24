package Logic.main;

import Logic.Game.Game;
import Network.*;

public class OnlinePlayer extends Player {

    public OnlinePlayer(Game game) {
        super(game);
    }

    @Override
    public boolean takeTurn() {
        netplay = Network.getNetplay();
        System.out.println(netplay);
        int[] xy = netplay.getShotAt();


        LogicConstants.GameElementStatus status = game.getgameElementStatus(xy[0], xy[1]);
        if (status == LogicConstants.GameElementStatus.WATER || status == LogicConstants.GameElementStatus.CLOSE) {
            netplay.sendAnswer(0);
        } else if (status == LogicConstants.GameElementStatus.SHIP) {
            netplay.sendAnswer(1);
            return true;
        } else if (game.isShipDestroyed(xy[0], xy[1]) > 0) {
            netplay.sendAnswer(2);
            return true;
        }
        return false;
    }

    @Override
    public int shoot(int x, int y) {
        netplay = Network.getNetplay();
        int isHit = netplay.shoot(x, y);

        if (isHit == 0) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
        } else if (isHit == 1) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return 1;
        } else if (isHit == 2) {
            game.setgameElementStatus(x,y, LogicConstants.GameElementStatus.HIT);
            return 2;
        } else {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.ERROR);
        }
        return 0;
    }
}
