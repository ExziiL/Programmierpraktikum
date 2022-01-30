package Logic.main;

import Logic.Game.Game;
import Network.Network;

public class MyPlayer extends Player {

    private String name;

    /**
     * Constructor
     *
     * @param game Object of the Class Game
     * @param name Name of the Player
     */
    public MyPlayer(Game game, String name) {
        super(game);
        this.name = name;
    }

    @Override
    public int takeTurn() {
        return 1;
    }

    /**
     * Method uses the Shoot-Method of Player when the GAmeMode is Offline. When the GameMode is Online
     * the type of Shot is determined by the Answer is given in the second Part of Network shoot.
     *
     * @param x Coordinate
     * @param y Coordinate
     * @return a 1 if the Shot was a Miss, 0 when it was a Hit and 2 if it was a Hit that sunk a Ship,
     * when in GameMode Online else it returns what shoot from Player returns.
     */
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
                return -1;
            }

        }
        return 1;
    }
}