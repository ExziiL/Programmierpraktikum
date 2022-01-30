package Logic.Game;

import Logic.main.Ship;

import java.util.ArrayList;

import static Logic.main.LogicConstants.GameElementStatus;

public class GameElement {

    //region Variables
    private GameElementStatus status;
    private ArrayList<Ship> closeShips = new ArrayList<>();
    private Ship ship = null;
    private int part;
    //endregion

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

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

    /**
     * Sets the Variable ship to the Parameter ship if the status is Hit or Ship.
     * Else the Ship is added to the Arraylist closeShips
     * @param ship Object of the Class Ship
     */
    public void setShip(Ship ship) {
        if (status == GameElementStatus.SHIP ||
                status == GameElementStatus.HIT) {
            this.ship = ship;
        } else if (status == GameElementStatus.CLOSE) {
            closeShips.add(ship);
        }
    }

    public Ship getShip() {
        return ship;
    }

    /**
     * Checks if a Ship is Close to another Ship, if so returns true
     * @param ship Object of the Class Ship
     * @return True if a Ship is in the Arraylist closeShips
     */
    public boolean isShipClose(Ship ship) {
        for (int i = 0; i < closeShips.size(); i++) {
            if (closeShips.get(i) == ship) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the Object ship in the ArrayList closeShips
     * @param ship Object of the Class Ship
     * @return if the Removal of the ship in the closeShips arrayList was successful
     */
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
