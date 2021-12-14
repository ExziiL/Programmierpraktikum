package Logic.Game;

import Logic.main.Ship;

import static Logic.main.LogicConstants.*;

public class GameElement {

    private GameElementStatus status;
    private Ship ship;

    public GameElement() {
        setStatus(GameElementStatus.WATER);
    }

    public GameElementStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameElementStatus status) {
        this.status = status;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
