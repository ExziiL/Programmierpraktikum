package Logic.main;

import Logic.Game.Game;
import Utilities.MyRandom;
import Utilities.Point;

import java.util.concurrent.TimeUnit;

import static Logic.main.LogicConstants.Direction.*;

public class OfflinePlayer extends Player {
    // last Direction
    private LogicConstants.Direction direction = NONE;
    // last Point which was hit
    private Point hit = null;
    // did the last Shot hit
    private boolean wasHit = false;


    public OfflinePlayer(Game game) {
        super(game);
    }

    @Override
    public boolean shoot(int x, int y) {
        // wait shortly
        //   try {
        //       Thread.sleep(100);
        //   } catch (InterruptedException e) {
        //       e.printStackTrace();
        //   }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return super.shoot(x, y);
    }

    @Override
    public void takeTurn() {

        boolean isHit = true;
        boolean isDestroyed = false;

        // if a Ship is hit and not destroyed then destroy the rest of the Ship (Shoot in Other Direction)
        if (hit != null && direction != NONE) {

            do {

                switch (direction) {
                    case UP:
                        // Shoot Down
                        hit.y++;
                        break;
                    case DOWN:
                        //Shoot Up
                        hit.y--;
                        break;
                    case LEFT:
                        //Shoot Right
                        hit.x++;
                        break;
                    case RIGHT:
                        //Shoot Left
                        hit.x--;
                        break;
                }

                isHit = tryShot(hit.x, hit.y);
                if (isHit) {
                    isDestroyed = tryDestroyed(hit.x, hit.y);
                }

            } while (isHit && !isDestroyed);

            hit = null;
            direction = NONE;
        }


        if (wasHit) {

            // if right was not a Miss and in Game Field try Shoot Right
            if (game.inGameField(hit.x + 1, hit.y) && game.getgameElementStatus(hit.x + 1, hit.y) != LogicConstants.GameElementStatus.MISS) {

                Point nextHit = new Point(hit.x, hit.y);

                // Shoot right until Miss / Destroyed or OutofGame
                do {
                    nextHit.x++;
                    isHit = tryShot(nextHit.x, nextHit.y);
                    if (isHit) {
                        isDestroyed = tryDestroyed(nextHit.x, nextHit.y);
                        direction = RIGHT;
                    }

                } while (isHit && !isDestroyed);

                // if left was not a Miss and in Game Field try Shoot Left
            } else if (game.inGameField(hit.x - 1, hit.y) && game.getgameElementStatus(hit.x - 1, hit.y) != LogicConstants.GameElementStatus.MISS) {

                Point nextHit = new Point(hit.x, hit.y);


                // Shoot right until Miss / Destroyed or OutofGame
                do {
                    nextHit.x--;
                    isHit = tryShot(nextHit.x, nextHit.y);
                    if (isHit) {
                        isDestroyed = tryDestroyed(nextHit.x, nextHit.y);
                        direction = LEFT;
                    }

                } while (isHit && !isDestroyed);

                // if up was not a Miss and in Game Field try Shoot up
            } else if (game.inGameField(hit.x, hit.y - 1) && game.getgameElementStatus(hit.x, hit.y - 1) != LogicConstants.GameElementStatus.MISS) {

                Point nextHit = new Point(hit.x, hit.y);


                // Shoot right until Miss / Destroyed or OutofGame
                do {
                    nextHit.y--;
                    isHit = tryShot(nextHit.x, nextHit.y);
                    if (isHit) {
                        isDestroyed = tryDestroyed(nextHit.x, nextHit.y);
                        direction = UP;
                    }

                } while (isHit && !isDestroyed);

                // if down was not a Miss and in Game Field try Shoot down
            } else if (game.inGameField(hit.x, hit.y + 1) && game.getgameElementStatus(hit.x, hit.y + 1) != LogicConstants.GameElementStatus.MISS) {

                Point nextHit = new Point(hit.x, hit.y);


                // Shoot right until Miss / Destroyed or OutofGame
                do {
                    nextHit.y++;

                    isHit = tryShot(nextHit.x, nextHit.y);
                    if (isHit) {
                        isDestroyed = tryDestroyed(nextHit.x, nextHit.y);
                        direction = DOWN;
                    }

                } while (isHit && !isDestroyed);
            }

            if (isDestroyed) {
                shootRandom();
            }

        } else {
            shootRandom();
        }
    }

    private void initStatusVariables() {
        wasHit = false;
        direction = NONE;
        hit = null;
    }


    private boolean tryShot(int x, int y) {
        if (game.inGameField(x, y)) {
            return this.shoot(x, y);
        } else {
            return false;
        }
    }

    private boolean tryDestroyed(int x, int y) {
        if (game.inGameField(x, y)) {
            boolean destroyed = game.isShipDestroyed(x, y);
            if (destroyed) {
                initStatusVariables();
            }
            return destroyed;
        } else {
            return false;
        }
    }

    private void shootRandom() {
        // Define random Point in Game
        do {
            hit = new Point(MyRandom.getRandomNumberInRange(0, game.getSize() - 1), MyRandom.getRandomNumberInRange(0, game.getSize() - 1));
        } while (game.getgameElementStatus(hit.x, hit.y) == LogicConstants.GameElementStatus.HIT
                && game.getgameElementStatus(hit.x, hit.y) == LogicConstants.GameElementStatus.MISS);


        wasHit = shoot(hit.x, hit.y);
        // if a Ship is Hit then try again
        if (wasHit) {
            takeTurn();
        }
    }

}
