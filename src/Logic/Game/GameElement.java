package Logic.Game;

import Logic.main.Ship;

import static Logic.main.LogicConstants.*;

import java.util.ArrayList;

public class GameElement {
    private GameElementStatus status;
    private ArrayList<Ship> ships = new ArrayList<>();


    public GameElement() {
        setStatus(GameElementStatus.WATER);
    }

    public void init() {
        setStatus(GameElementStatus.WATER);
        ships = new ArrayList<>();

    }

    public GameElementStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameElementStatus status) {
        this.status = status;
    }

    public int getCountShips() {
        return ships.size();
    }

    public void setShip(Ship ship) {
        ships.add(ship);
    }

    public Ship getShip(int index) {
        return ships.get(index);
    }

    public boolean removeShip(Ship ship) {
        try {
            return ships.remove(ship);
        } catch (UnsupportedOperationException e) {
            return false;
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
