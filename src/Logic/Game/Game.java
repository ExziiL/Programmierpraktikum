package Logic.Game;

import Logic.Game.Exceptions.*;

import static Logic.main.LogicConstants.*;

import Logic.main.*;

import java.util.ArrayList;

public class Game {
    protected int size = 0;
    protected String name;
    protected int[][] spielfeld;
    protected Player player;
    protected ArrayList<Ship> ships = new ArrayList<Ship>();
    protected int countTwoShip;
    protected int countThreeShip;
    protected int countFourShip;
    protected int countFiveShip;

    public Game() {


    }

    public void initialisiereSpielfeld() {
        System.out.println("Spielfeld initialisiert");
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setSize(int s) throws FalscheSpielfeldGroesse {
        this.size = s;
        if (size < 5 || size > 30) {
            throw new FalscheSpielfeldGroesse(size);
        }

        spielfeld = new int[size][size];

        determineNumbersOfShips();
    }

    public int getSize() {
        return size;
    }

    protected Player createPlayer(String name, PlayerType t) throws FalscherSpielertyp {
        // erstelle je nach Spielertyp eine Unterklasse des Typ Spieler
        switch (t) {
            case ONLINE:
                return new OnlinePlayer(this, name);
            case OFFLINE:
                return new OfflinePlayer(this, name);
            case SELF:
                return new Player(this, name);
        }

        throw new FalscherSpielertyp();
    }

    public int getCountTwoShip() {
        return countTwoShip;
    }

    public int getCountThreeShip() {
        return countThreeShip;
    }

    public int getCountFourShip() {
        return countFourShip;
    }

    public int getCountFiveShip() {
        return countFiveShip;
    }

    private void determineNumbersOfShips() {
        // 30 % der Spielfeldgröße
        int places = ((size * size) * 30) / 100;
        int placesForShip = 0;
        int placesRemaining = 0;
        int percentTwo = 0;
        int percentThree = 0;
        int percentFour = 0;
        int percentFive = 0;
        countTwoShip = 0;
        countThreeShip = 0;
        countFourShip = 0;
        countFiveShip = 0;


        // die ersten Spielfelder werden fest bestimmt
        if (size < 9) {
            switch (size) {
                case 5:
                    addShip(2, 2);
                    addShip(3, 1);
                    break;
                case 6:
                    addShip(2, 2);
                    addShip(3, 2);
                    break;
                case 7:
                    addShip(2, 2);
                    addShip(3, 2);
                    addShip(4, 1);
                    break;
                case 8:
                    addShip(2, 2);
                    addShip(3, 3);
                    addShip(5, 1);
                    break;
            }
            return;
        }


        // Prozente je nach Spielgröße
        if (size >= 9 && size <= 15) {

            percentTwo = 15;
            percentThree = 30;
            percentFour = 35;
            percentFive = 20;
        } else if (size >= 16 && size <= 21) {

            percentTwo = 5;
            percentThree = 35;
            percentFour = 35;
            percentFive = 25;
        } else if (size >= 22) {
            percentTwo = 0;
            percentThree = 20;
            percentFour = 40;
            percentFive = 40;
        }


        // Zweier Shiffe vergeben
        placesForShip = (places * percentTwo) / 100;
        addShip(2, placesForShip / 2);
        placesRemaining += placesForShip % 2;

        // Dreier Shiffe vergeben
        placesForShip = (places * percentThree) / 100;
        addShip(3, placesForShip / 3);
        placesRemaining += placesForShip % 3;

        // Vierer Shiffe vergeben
        placesForShip = (places * percentFour) / 100;
        addShip(4, placesForShip / 4);
        placesRemaining += placesForShip % 4;

        // Fünfer Shiffe vergeben
        placesForShip = (places * percentFive) / 100;
        addShip(5, placesForShip / 5);
        placesRemaining += placesForShip % 5;

        // Restliche Plätze vergeben Reihenfolge 3 -> 4 -> 5 -> 2
        while (placesRemaining > 2) {
            if ((placesRemaining - 3) >= 0) {
                placesRemaining -= 3;
                addShip(3, 1);
            }
            if ((placesRemaining - 4) >= 0) {
                placesRemaining -= 4;
                addShip(4, 1);
            }
            if ((placesRemaining - 5) >= 0) {
                placesRemaining -= 5;
                addShip(5, 1);
            }
            if ((placesRemaining - 2) >= 0) {
                placesRemaining -= 2;
                addShip(2, 1);
            }
        }

    }


    private void addShip(int size, int number) {
        for (int i = 0; i < number; i++) {

            switch (size) {
                case 2:
                    countTwoShip++;
                    break;
                case 3:
                    countThreeShip++;
                    break;
                case 4:
                    countFourShip++;
                    break;
                case 5:
                    countFiveShip++;
                    break;
            }

            ships.add(new Ship(size));
        }
    }
}
