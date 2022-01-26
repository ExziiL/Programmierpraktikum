package Logic.main;

import Logic.Game.Game;
import Network.Network;

public class OnlinePlayer extends Player {

    public OnlinePlayer(Game game) {
        super(game);
    }

    @Override
    public int takeTurn() {
        // 0 = hit
        // 1 = miss
        // 2 = save

        netplay = Network.getNetplay();
        System.out.println(netplay);
        int[] xy = netplay.receiveSaveOrShot();
        if (xy[0] == 0) {
            LogicConstants.GameElementStatus status = game.getgameElementStatus(xy[1], xy[2]);
            if (status == LogicConstants.GameElementStatus.WATER || status == LogicConstants.GameElementStatus.CLOSE) {
                game.setgameElementStatus(xy[1], xy[2], LogicConstants.GameElementStatus.MISS);
                netplay.sendAnswer(0);
                return 1;
            } else if (status == LogicConstants.GameElementStatus.SHIP) {
                game.setgameElementStatus(xy[1], xy[2], LogicConstants.GameElementStatus.HIT);

                if (game.isMyShipDestroyed(xy[1], xy[2])) {
                    netplay.sendAnswer(2);
                    return 0;
                } else {
                    netplay.sendAnswer(1);
                    return 0;
                }
            }

        } else if (xy[0] == 1) {

            return 2;
        }
        return 1;
    }

    @Override
    public int shoot(int x, int y) {
        //hit = 0
        //miss = 1

        netplay = Network.getNetplay();
        int isHit = netplay.shoot(x, y);

        if (isHit == 0) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
            return 1;
        } else if (isHit == 1) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return 0;
        } else if (isHit == 2) {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
            return 2;
        } else {
            game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.ERROR);
        }
        return 0;
    }
}
