package Logic.main;

import Logic.Game.Game;
import Utilities.*;

public class OfflinePlayer extends Player {
    public OfflinePlayer(Game game) {
        super(game);
    }


    @Override
    public void takeTurn() {

        boolean isHit = true;


        while (isHit) {

            Point p = determineNextShot();


            // kurz warten
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isHit = shoot(p.x, p.y);
        }
    }

    private Point determineNextShot() {
        int x = 0, y = 0;
        // Zufällig einen Platz aussuchen
        x = MyRandom.getRandomNumberInRange(0, game.getSize() - 1);
        y = MyRandom.getRandomNumberInRange(0, game.getSize() - 1);

        return new Point(x, y);
    }
}
