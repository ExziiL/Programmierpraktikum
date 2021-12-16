package Logic.main;

import Logic.Game.*;
import Logic.Game.Exceptions.FalseFieldSize;

import static Logic.main.LogicConstants.*;

public class Controller {
    private MyGame myGame;

    public Controller() {
        myGame = new MyGame();
    }

    public void loadGame() {

    }

    public void startGame() {

    }

    public void saveGame() {
    }

    public void settings() {
    }

    public void exitGame() {
        System.exit(0);
    }

    public void setName(String n) {
        myGame.setName(n);
    }

    public void setGameSize(int n) {

        try {
            myGame.setSize(n);
        } catch (FalseFieldSize falseFieldSize) {
            System.out.println("Falsche Spielfeldgröße");
        }
    }

    public int getGameSize() {
        return myGame.getSize();
    }

    public int getCountTwoShip() {
        return myGame.getCountTwoShip();
    }

    public int getCountThreeShip() {
        return myGame.getCountThreeShip();
    }

    public int getCountFourShip() {
        return myGame.getCountFourShip();
    }

    public int getCountFiveShip() {
        return myGame.getCountFiveShip();
    }

    public GameElementStatus getGameElementStatus(int element) {
        return myGame.gameElementStatus(element);
    }

    public boolean checkPlaceForShip(Ship ship, int x, int y, boolean vertical) {

        return myGame.checkPlaceForShip(ship, x, y, vertical);
    }

}
