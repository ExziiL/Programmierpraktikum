package Logic.main;

import Logic.Game.Game;
import Utilities.MyRandom;
import Utilities.Point;

import java.util.ArrayList;

import static Logic.main.LogicConstants.Direction.*;

public class OfflinePlayer extends Player {

    private class nextHits {
        Point p = null;
        LogicConstants.Direction direction = NONE;

        public nextHits(int x, int y, LogicConstants.Direction dir) {
            this.p = new Point(x, y);
            this.direction = dir;
        }

        public nextHits(Point p, LogicConstants.Direction dir) {
            this(p.x, p.y, dir);
        }

        public Point getPointDirection() {
            switch (direction) {
                case RIGHT:
                    return new Point(p.x + 1, p.y);
                case LEFT:
                    return new Point(p.x - 1, p.y);
                case DOWN:
                    return new Point(p.x, p.y + 1);
                case UP:
                    return new Point(p.x, p.y - 1);
                default:
                    return new Point(p.x, p.y);
            }
        }
    }

    private ArrayList<nextHits> nextHits = new ArrayList<>();

    public OfflinePlayer(Game game) {
        super(game);
    }

    @Override
    public int shoot(int x, int y) {
        return super.shoot(x, y);
    }

    @Override
    public boolean takeTurn() {
        OfflinePlayer.nextHits nextHit = null;
        Point shipHitted;
        Point shootInDirection;
        int isHit;
        if (nextHits.isEmpty()) {
            isHit = shootRandom();
        } else {
            nextHit = nextHits.get(0);
            nextHits.remove(0);

            shipHitted = nextHit.p;
            shootInDirection = nextHit.getPointDirection();

            isHit = shoot(shootInDirection.x, shootInDirection.y);
            if (isHit > 0) {
                // Delete shots in Opposite Direction because there can not be a ship
                deleteOppositeDirection(shipHitted, nextHit.direction);

                if (game.isMyShipDestroyed(shipHitted.x, shipHitted.y)) {
                    // Add Shot in same direction
                    addShootDirection(shootInDirection, nextHit.direction);
                }
            }

        }
        return isHit > 0;
    }

    private int shootRandom() {
        Point hit;
        int isHit = 0;

        // Define random Point in Game
        do {
            hit = new Point(MyRandom.getRandomNumberInRange(0, game.getSize() - 1), MyRandom.getRandomNumberInRange(0, game.getSize() - 1));

        } while (isHitorMiss(hit.x, hit.y) || isSurroundingHit(hit.x, hit.y));

        isHit = shoot(hit.x, hit.y);
        // if a Ship is Hit then try in all Directions
        if (isHit > 0) {
            addShotInAllDirection(hit);
        }
        return isHit;
    }

    private void addShotInAllDirection(Point hit) {
        addShootRight(hit);
        addShootLeft(hit);
        addShootUp(hit);
        addShootDown(hit);
    }

    private void addShootRight(Point p) {

        if (game.inGameField(p.x + 1, p.y) && !isHitorMiss(p.x + 1, p.y)) {
            nextHits.add(new nextHits(p, RIGHT));
        }
    }

    private void addShootLeft(Point p) {
        if (game.inGameField(p.x - 1, p.y) && !isHitorMiss(p.x - 1, p.y)) {
            nextHits.add(new nextHits(p, LEFT));
        }
    }

    private void addShootUp(Point p) {
        if (game.inGameField(p.x, p.y - 1) && !isHitorMiss(p.x, p.y - 1)) {
            nextHits.add(new nextHits(p, UP));
        }
    }

    private void addShootDown(Point p) {
        if (game.inGameField(p.x, p.y + 1) && !isHitorMiss(p.x, p.y + 1)) {
            nextHits.add(new nextHits(p, DOWN));
        }
    }

    private void addShootDirection(Point p, LogicConstants.Direction direction) {
        switch (direction) {
            case RIGHT:
                addShootRight(p);
                break;
            case LEFT:
                addShootLeft(p);
                break;
            case DOWN:
                addShootDown(p);
                break;
            case UP:
                addShootUp(p);
                break;
        }
    }

    private void deleteOppositeDirection(Point delPoint, LogicConstants.Direction direction) {
        for (int i = 0; i < nextHits.size(); i++) {
            if (nextHits.get(i).p.equals(delPoint)) {

                if (direction == LEFT || direction == RIGHT) {
                    if (nextHits.get(i).direction == UP || nextHits.get(i).direction == DOWN) {
                        nextHits.remove(i);
                    }
                } else if (direction == DOWN || direction == UP) {
                    if (nextHits.get(i).direction == RIGHT || nextHits.get(i).direction == LEFT) {
                        nextHits.remove(i);
                    }
                }
            }
        }
    }

    private boolean isHitorMiss(int x, int y) {

        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.MISS ||
                game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {
            return true;
        }
        return false;
    }

    private boolean isHit(int x, int y) {
        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {
            return true;
        }
        return false;
    }

    private boolean isSurroundingHit(int x, int y) {

        if (game.inGameField(x + 1, y) && isHit(x + 1, y)) {
            return true;
        }
        if (game.inGameField(x - 1, y) && isHit(x - 1, y)) {
            return true;
        }
        if (game.inGameField(x, y + 1) && isHit(x, y + 1)) {
            return true;
        }

        if (game.inGameField(x, y - 1) && isHit(x, y - 1)) {
            return true;
        }

        if (game.inGameField(x + 1, y - 1) && isHit(x + 1, y - 1)) {
            return true;
        }
        if (game.inGameField(x + 1, y + 1) && isHit(x + 1, y + 1)) {
            return true;
        }

        if (game.inGameField(x - 1, y - 1) && isHit(x - 1, y - 1)) {
            return true;
        }
        if (game.inGameField(x - 1, y + 1) && isHit(x - 1, y + 1)) {
            return true;
        }

        return false;
    }

}
