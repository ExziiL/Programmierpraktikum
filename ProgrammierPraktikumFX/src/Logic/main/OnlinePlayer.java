package Logic.main;

import Logic.Game.Game;
import Network.Network;

public class OnlinePlayer extends Player {

    /**
     * Constructor
     * @param game Object of the Class Game
     */
    public OnlinePlayer(Game game) {
        super(game);
    }

    /**
     * Receives an int Array of Size 3 from the Network-Method receiveSaveOrShot. If the Number in [0] is 0,
     * a shot was fired and the Methode checks if on the Coordinates x = [1] and y = [2] a Ship is or not.
     * If a Ship is on these Coordinates it calls the Network-Method sendAnswer with parameter 1,
     * when it was a Miss a 0 as Parameter and the Shot has sunk a Ship the Parameter is 2.
     * If in [0] is a 1 that means the Game was saved by the other Person.
     * If 99 is in [0] the other Person left the Game without saving.
     * @return an int - 1 = Miss, 0= Hit, 2 = Save and 99 = Other Player left the Game
     */
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
        } else if (xy[0] == 99) {
            return 99;
        }

        return 1;
    }

    /**
     * Uses the shoot-Method of Network to shoot at the Opponent and get an answer if the
     * Shot was a Miss, Hit, or Hit and Sunk. Determined by the Answer, the Status of the affected Pane is changed
     * and a Return value is chosen
     * @param x Coordinate
     * @param y Coordinate
     * @return 1 = Miss, 0 = Hit, 2 = Hit and Sunk
     */
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
