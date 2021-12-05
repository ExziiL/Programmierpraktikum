package Logic.main;

import Logic.Game.*;
import Logic.Game.Exceptions.FalscheSpielfeldGroesse;

public class Controller {
    private MyGame myGame;

    public Controller() {
        myGame = new MyGame();
    }

    public void SpielLaden() {

    }

    public void SpielStarten() {

    }

    public void SpielSpeichern() {
    }

    public void Einstellungen() {
    }

    public void beenden() {
        System.exit(0);
    }

    public void setName(String n) {
        myGame.setName(n);
    }

    public void setGameSize(int n) {

        try {
            myGame.setSize(n);
        } catch (FalscheSpielfeldGroesse falscheSpielfeldGroesse) {
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

    public boolean checkPlaceforShip(Ship ship, int x, int y, boolean vertical) {

        return myGame.checkPlaceforShip(ship, x, y, vertical);
    }

}
