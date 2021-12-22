package Logic.Game;

import Logic.Game.Exceptions.*;

import static Logic.main.LogicConstants.*;

import Logic.main.*;
import Utilities.*;
import Utilities.Exception.*;

import java.util.ArrayList;


public class Game {
    protected int size = 0;
    protected String name;
    protected GameElement[][] gameField;
    protected Player player;
    protected ArrayList<Ship> ships = new ArrayList<>();
    protected int countTwoShip;
    protected int countThreeShip;
    protected int countFourShip;
    protected int countFiveShip;


    public void setName(String n) {
        this.name = n;
    }

    public void setSize(int s) throws FalseFieldSize {
        this.size = s;
        if (size < 5 || size > 30) {
            throw new FalseFieldSize(size);
        }

        gameField = new GameElement[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameField[i][j] = new GameElement();
            }
        }

        determineNumberOfShips();
    }

    public int getSize() {
        return size;
    }

    protected Player createPlayer(String name, PlayerType t) throws FalsePlayerType {
        // erstelle je nach Spielertyp eine Unterklasse des Typ Spieler
        switch (t) {
            case ONLINE:
                return new OnlinePlayer(this, name);
            case OFFLINE:
                return new OfflinePlayer(this, name);
            case SELF:
                return new Player(this, name);
        }

        throw new FalsePlayerType();
    }

    public boolean checkPlaceForShip(Ship ship, int x, int y, boolean vertical) {
        return true;
    }

    public GameElementStatus getgameElementStatus(int element) {
        int x = element % size;
        int y = element / size;

        return gameField[x][y].getStatus();
    }

    public GameElementStatus getgameElementStatus(int x, int y) {
        return gameField[x][y].getStatus();
    }

    public boolean allShipPlaced() {
        return getCountTwoShip() == 0 &&
                getCountThreeShip() == 0 &&
                getCountFourShip() == 0 &&
                getCountFiveShip() == 0;
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

    public HoverState[] getHoverStateStatus(int index, int ShipSize, boolean isHorizontal) throws ShipOutofGame {
        ArrayList<HoverState> stateList = new ArrayList<>();
        int x = index % size;
        int y = index / size;

        GameElementStatus shipStatus;
        if (ShipinGameField(x, y, ShipSize, isHorizontal)) {
            shipStatus = GameElementStatus.SHIP;
        } else {
            shipStatus = GameElementStatus.ERROR;
        }

        switch (ShipSize) {
            case 2:

                if (isHorizontal == false) {


                    // Zweier Schiff oben
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesTop(x, y, stateList);

                    // Zweier Shiff unten
                    stateList = setShip(x, y + 1, stateList, shipStatus);
                    stateList = setEdgesButton(x, y + 1, stateList);

                } else {

                    // Zweier Schiff links
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesLeft(x, y, stateList);

                    // Zweier Shiff rechts
                    stateList = setShip(x + 1, y, stateList, shipStatus);
                    stateList = setEdgesRight(x + 1, y, stateList);
                }
                break;

            case 3:
                if (isHorizontal == false) {

                    // Dreier Schiff oben
                    stateList = setShip(x, y - 1, stateList, shipStatus);
                    stateList = setEdgesTop(x, y - 1, stateList);

                    // Dreier Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesLeftRight(x, y, stateList);

                    // Dreier Shiff unten
                    stateList = setShip(x, y + 1, stateList, shipStatus);
                    stateList = setEdgesButton(x, y + 1, stateList);

                } else {

                    // Dreier Schiff links
                    stateList = setShip(x - 1, y, stateList, shipStatus);
                    stateList = setEdgesLeft(x - 1, y, stateList);


                    // Dreier Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesUpDown(x, y, stateList);

                    // Dreier Shiff unten
                    stateList = setShip(x + 1, y, stateList, shipStatus);
                    stateList = setEdgesRight(x + 1, y, stateList);
                }
                break;

            case 4:
                if (isHorizontal == false) {

                    // Vierer Schiff oben
                    stateList = setShip(x, y - 1, stateList, shipStatus);
                    stateList = setEdgesTop(x, y - 1, stateList);

                    // Vierer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesLeftRight(x, y, stateList);

                    // Vierer Shiff mitte
                    stateList = setShip(x, y + 1, stateList, shipStatus);
                    stateList = setEdgesLeftRight(x, y + 1, stateList);

                    // Vierer Shiff unten
                    stateList = setShip(x, y + 2, stateList, shipStatus);
                    stateList = setEdgesButton(x, y + 2, stateList);

                } else {

                    // Vierer Schiff links
                    stateList = setShip(x - 1, y, stateList, shipStatus);
                    stateList = setEdgesLeft(x - 1, y, stateList);


                    // Vierer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesUpDown(x, y, stateList);

                    // Vierer Shiff mitte
                    stateList = setShip(x + 1, y, stateList, shipStatus);
                    stateList = setEdgesUpDown(x + 1, y, stateList);


                    // Vierer Shiff rechts + 2
                    stateList = setShip(x + 2, y, stateList, shipStatus);
                    stateList = setEdgesRight(x + 2, y, stateList);
                }
                break;

            case 5:
                if (isHorizontal == false) {


                    // Fünfer Schiff oben + 2
                    stateList = setShip(x, y - 2, stateList, shipStatus);
                    stateList = setEdgesTop(x, y - 2, stateList);

                    // Fünfer Schiff oben + 1
                    stateList = setShip(x, y - 1, stateList, shipStatus);
                    stateList = setEdgesLeftRight(x, y - 1, stateList);


                    // Fünfer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesLeftRight(x, y, stateList);

                    // Fünfer Shiff unten + 1
                    stateList = setShip(x, y + 1, stateList, shipStatus);
                    stateList = setEdgesLeftRight(x, y + 1, stateList);

                    // Fünfer Shiff unten + 2
                    stateList = setShip(x, y + 2, stateList, shipStatus);
                    stateList = setEdgesButton(x, y + 2, stateList);


                } else {

                    // Fünfer Schiff links + 2
                    stateList = setShip(x - 2, y, stateList, shipStatus);
                    stateList = setEdgesLeft(x - 2, y, stateList);

                    // Fünfer Schiff links
                    stateList = setShip(x - 1, y, stateList, shipStatus);
                    stateList = setEdgesUpDown(x - 1, y, stateList);

                    // Fünfer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus);
                    stateList = setEdgesUpDown(x, y, stateList);

                    // Fünfer Shiff mitte
                    stateList = setShip(x + 1, y, stateList, shipStatus);
                    stateList = setEdgesUpDown(x + 1, y, stateList);


                    // Vierer Shiff rechts + 2
                    stateList = setShip(x + 2, y, stateList, shipStatus);
                    stateList = setEdgesRight(x + 2, y, stateList);

                }

                break;

        }
        return stateList.toArray(new HoverState[stateList.size()]);
    }


    public void placeShip(int index, int ShipSize, boolean isHorizontal) {
        int x = 0;
        int y = 0;
        Ship ship = null;
        try {
            // aktuellen Status des Schiffes holen
            HoverState[] hoverStates = getHoverStateStatus(index, ShipSize, isHorizontal);

            // Shiff aus Arreylist ermitteln
            for (int s = 0; s < ships.size(); s++) {
                if (ships.get(s).getSize() == ShipSize) {
                    ship = ships.get(s);
                    removeShip(s);
                    break;
                }
            }

            // Status und Shiff auf Spielfeld ändern
            for (int i = 0; i < hoverStates.length; i++) {
                if (hoverStates[i] != null) {
                    x = hoverStates[i].getIndex() % size;
                    y = hoverStates[i].getIndex() / size;

                    gameField[x][y].setStatus(hoverStates[i].getStatus());
                    if (hoverStates[i].getStatus() == GameElementStatus.SHIP) {
                        gameField[x][y].setShip(ship);
                    }
                }
            }
        } catch (ShipOutofGame e) {
            return;
        }
    }

    public void shuffleShips() {
        int x = 0;
        int y = 0;
        int shipSize = 0;
        boolean isHorizontal;

        initializeGameField();

        while (allShipPlaced() != true) {

            // Zufällig einen Platz aussuchen
            x = MyRandom.getRandomNumberInRange(0, size - 1);
            y = MyRandom.getRandomNumberInRange(0, size - 1);
            isHorizontal = MyRandom.getRandomBoolean();

            if (getCountTwoShip() != 0) {
                shipSize = 2;
            } else if (getCountThreeShip() != 0) {
                shipSize = 3;
            } else if (getCountFourShip() != 0) {
                shipSize = 4;
            } else if (getCountFiveShip() != 0) {
                shipSize = 5;
            }

            if (ShipinGameField(x, y, shipSize, isHorizontal)) {
                placeShip(matchIndex(x, y), shipSize, isHorizontal);
            }
        }
    }

    private void initializeGameField() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameField[i][j].init();
            }
        }
        ships = new ArrayList<>();
        determineNumberOfShips();
    }


    private void determineNumberOfShips() {
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


    private void removeShip(int index) {

        switch (ships.get(index).getSize()) {
            case 2:
                countTwoShip--;
                break;
            case 3:
                countThreeShip--;
                break;
            case 4:
                countFourShip--;
                break;
            case 5:
                countFiveShip--;
                break;
        }

        ships.remove(index);
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


    private boolean ShipinGameField(int x, int y, int ShipSize, boolean isHorizontal) {
        if (inGameField(x, y) == false) {
            return false;
        }
        switch (ShipSize) {
            case 2:
                if (isHorizontal == false) {
                    if (inGameField(x, y + 1)) {
                        return true;
                    }
                } else {
                    if (inGameField(x + 1, y)) {
                        return true;
                    }
                }
                break;
            case 3:
                if (isHorizontal == false) {
                    if (inGameField(x, y + 1) && inGameField(x, y - 1)) {
                        return true;
                    }
                } else {
                    if (inGameField(x - 1, y) && inGameField(x + 1, y)) {
                        return true;
                    }
                }
                break;
            case 4:
                if (isHorizontal == false) {
                    if (inGameField(x, y - 1) && inGameField(x, y + 1) && inGameField(x, y + 2)) {
                        return true;
                    }
                } else {
                    if (inGameField(x - 1, y) && inGameField(x + 1, y) && inGameField(x + 2, y)) {
                        return true;
                    }
                }
                break;

            case 5:
                if (isHorizontal == false) {
                    if (inGameField(x, y - 2) && inGameField(x, y - 1) && inGameField(x, y + 1) && inGameField(x, y + 2)) {
                        return true;
                    }
                } else {
                    if (inGameField(x - 2, y) && inGameField(x - 1, y) && inGameField(x + 1, y) && inGameField(x + 2, y)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private boolean inGameField(int x, int y) {

        if (x >= 0 && x < size &&
                y >= 0 && y < size) {
            return true;
        } else {
            return false;
        }
    }


    private HoverState setEdge(int x, int y) {
        if (inGameField(x, y)) {
            // Ecke kann einach eingefügt werden
            if (getgameElementStatus(x, y) != GameElementStatus.CLOSE && getgameElementStatus(x, y) != GameElementStatus.SHIP) {
                return new HoverState(matchIndex(x, y), GameElementStatus.CLOSE);
            } // Ecke überlappt mit einem anderen Shiff
            else if (getgameElementStatus(x, y) == GameElementStatus.SHIP) {
                return new HoverState(matchIndex(x, y), GameElementStatus.ERROR);
            }
        }
        return null;
    }

    private int matchIndex(int x, int y) {

        return y * (size) + x;
    }


    private ArrayList<HoverState> setShip(int x, int y, ArrayList<HoverState> states, GameElementStatus status) {

        if (inGameField(x, y)) {
            states.add(new HoverState(matchIndex(x, y), status));
        }
        return states;
    }

    // ___________________________________________Ränder der Shiffe setzen__________________________________
    private ArrayList<HoverState> setEdgesTop(int x, int y, ArrayList<HoverState> states) {

        // Rand oben
        states.add(setEdge(x, y - 1));

        // Rand rechts
        states.add(setEdge(x + 1, y));

        //Rand links
        states.add(setEdge(x - 1, y));

        //Rand rechtoben
        states.add(setEdge(x + 1, y - 1));

        //Rand link oben
        states.add(setEdge(x - 1, y - 1));

        return states;
    }

    private ArrayList<HoverState> setEdgesButton(int x, int y, ArrayList<HoverState> states) {
        //states.add(new HoverState(index, GameElementStatus.SHIP));

        // Rand unten
        states.add(setEdge(x, y + 1));

        // Rand rechts
        states.add(setEdge(x + 1, y));

        //Rand links
        states.add(setEdge(x - 1, y));

        //Rand recht unten
        states.add(setEdge(x + 1, y + 1));

        //Rand link unten
        states.add(setEdge(x - 1, y + 1));


        return states;
    }

    private ArrayList<HoverState> setEdgesLeft(int x, int y, ArrayList<HoverState> states) {

        // Rand unten
        states.add(setEdge(x, y + 1));

        // Rand oben
        states.add(setEdge(x, y - 1));

        //Rand links
        states.add(setEdge(x - 1, y));

        //Rand links oben
        states.add(setEdge(x - 1, y - 1));

        //Rand links unten
        states.add(setEdge(x - 1, y + 1));


        return states;
    }

    private ArrayList<HoverState> setEdgesRight(int x, int y, ArrayList<HoverState> states) {

        // Rand unten
        states.add(setEdge(x, y + 1));

        // Rand oben
        states.add(setEdge(x, y - 1));

        //Rand rechts
        states.add(setEdge(x + 1, y));

        //Rand rechts oben
        states.add(setEdge(x + 1, y - 1));

        //Rand links unten
        states.add(setEdge(x + 1, y + 1));


        return states;
    }

    private ArrayList<HoverState> setEdgesLeftRight(int x, int y, ArrayList<HoverState> states) {
        //Rand links
        states.add(setEdge(x - 1, y));

        //Rand rechts
        states.add(setEdge(x + 1, y));

        return states;
    }

    private ArrayList<HoverState> setEdgesUpDown(int x, int y, ArrayList<HoverState> states) {
        // Rand unten
        states.add(setEdge(x, y + 1));

        // Rand oben
        states.add(setEdge(x, y - 1));

        return states;
    }

    // ___________________________________________Random Generator__________________________________

}
