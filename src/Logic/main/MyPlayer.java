package Logic.main;

import Logic.Game.Game;
import Network.Network;

public class MyPlayer extends Player {


    private String name;

    public MyPlayer(Game game, String name) {
        super(game);
        this.name = name;
    }

    @Override
    public int takeTurn() {
        return 1;
    }

    @Override
    public int shoot(int x, int y) {
        if (game.getGameMode() == LogicConstants.GameMode.OFFLINE) {
            return super.shoot(x, y);
        } else if (game.getGameMode() == LogicConstants.GameMode.ONLINE) {
            netplay = Network.getNetplay();
            int isHit = netplay.shoot(x, y);

            if (isHit == 0) {
                game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.MISS);
                return 1; // miss
            } else if (isHit == 1) {
                game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
                return 0; // hit
            } else if (isHit == 2) {
                game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.HIT);
                return 2; // hit
            } else {
                game.setgameElementStatus(x, y, LogicConstants.GameElementStatus.ERROR);
            }
            return isHit;
        }
        return 1;
    }
}