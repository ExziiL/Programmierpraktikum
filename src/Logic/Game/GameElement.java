package Logic.Game;

import Logic.main.Ship;

import java.util.ArrayList;

import static Logic.main.LogicConstants.GameElementStatus;

public class GameElement {
    private GameElementStatus status;
    private ArrayList<Ship> closeShips = new ArrayList<>();
    private Ship ship = null;


    public GameElement() {
        setStatus(GameElementStatus.WATER);
    }

    public void init() {
        setStatus(GameElementStatus.WATER);
        closeShips = new ArrayList<>();
        ship = null;
    }

    public GameElementStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameElementStatus status) {
        this.status = status;
    }

    public int getCountShips() {
        return closeShips.size();
    }

    public void setShip(Ship ship) {
        if (status == GameElementStatus.SHIP) {
            this.ship = ship;
        } else if (status == GameElementStatus.CLOSE) {
            closeShips.add(ship);
        }
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isShipClose(Ship ship){
        for(int i = 0; i<closeShips.size(); i++){
            if(closeShips.get(i) == ship){
                return true;
            }
        }
        return false;
    }

    public boolean removeShip(Ship ship) {
        try {
            return closeShips.remove(ship);
        } catch (UnsupportedOperationException e) {
            return false;
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
