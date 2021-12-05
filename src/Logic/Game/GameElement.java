package Logic.Game;
import Logic.main.Ship;

public class GameElement {

    private boolean isHit = false;
    private boolean isShip = false;
    private boolean isNearShip = false;
    private Ship ship;

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isShip() {
        return isShip;
    }

    public void setShip(boolean ship) {
        isShip = ship;
    }

    public boolean isNearShip() {
        return isNearShip;
    }

    public void setNearShip(boolean nearShip) {
        isNearShip = nearShip;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
