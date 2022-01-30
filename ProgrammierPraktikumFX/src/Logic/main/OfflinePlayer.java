package Logic.main;

import Logic.Game.Game;
import Utilities.MyRandom;
import Utilities.Point;

import java.util.ArrayList;

import static Logic.main.LogicConstants.Direction.*;

public class OfflinePlayer extends Player {
    //region Variable
    private ArrayList<nextHits> nextHits = new ArrayList<>();
    //endregion

    /**
     * Constructor
     * @param game Object of the Class Game
     */
    public OfflinePlayer(Game game) {
        super(game);
    }

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

    @Override
    public int shoot(int x, int y) {
        return super.shoot(x, y);
    }

    /**
     * Implements the abstract Method take Turn from Player. It calls the shootRandom Method until a Shot hit a Ship.
     * Then get the first nextHit from the Arraylist to Shoot at this Point and shoots at this Point again.
     * If it misses now the next Direction is test when this Method is call again. If it Hits it will shoot as long
     * as it hits something. Wehen the Ship is sunk, it starts again with randomly shooting Points in the GripPane.
     * @return int 0 if the Shot was a Hit or 1 if it was a Miss
     */
    @Override
    public int takeTurn() {
        // 0 = hit
        // 1 = miss

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
            if (isHit == 0) {
                // Delete shots in Opposite Direction because there can not be a ship
                deleteOppositeDirection(shipHitted, nextHit.direction);

                if (!game.isMyShipDestroyed(shipHitted.x, shipHitted.y)) {
                    // Add Shot in same direction
                    addShootDirection(shootInDirection, nextHit.direction);
                }
            }

        }
        return isHit;
    }

    /**
     * First it chooses randomly a Point as long as it misses or is a Miss surrounding a previous Hit.
     * Is the Shot hit something the addShotInAllDirections is called.
     * @return if the Random Shot hit(0) something
     */
    private int shootRandom() {
        Point hit;
        int isHit = 1;

        // Define random Point in Game
        do {
            hit = new Point(MyRandom.getRandomNumberInRange(0, game.getSize() - 1), MyRandom.getRandomNumberInRange(0, game.getSize() - 1));

        } while (isHitorMiss(hit.x, hit.y) || isSurroundingHit(hit.x, hit.y));

        isHit = shoot(hit.x, hit.y);
        // if a Ship is Hit then try in all Directions
        if (isHit == 0) {
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

    /**
     * After a Shot,which was a Hit, the method goes through the switch case until a Shot hit again
     * @param p Point where the previous Shot with a Hit was placed
     * @param direction in that the next Shot will go
     */
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

    /**
     * Deletes these Points when a Ship can't be there anymore, because of previous Shots that determined the
     * Rotation of the Ship.
     * @param delPoint Point that's gets removed
     * @param direction in that the next Shot will go
     */
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

    /**
     * Checks if something went wrong with setting a new Status for the Pane on the Point, that was shot on.
     * @param x Coordinate
     * @param y Coordinate
     * @return true if the Shot was a Hit or a Miss
     */
    private boolean isHitorMiss(int x, int y) {

        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.MISS ||
                game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {
            return true;
        }
        return false;
    }

    /**
     *  checks if the Pane on these Coordinates has the Status HIT. If so returns true.
     */
    private boolean isHit(int x, int y) {
        if (game.getgameElementStatus(x, y) == LogicConstants.GameElementStatus.HIT) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param x Coordinate
     * @param y Coordinate
     * @return true if Coordinates are in the gamefield and Pane has the Status Hit
     */
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
