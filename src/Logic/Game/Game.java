package Logic.Game;

import Logic.Game.Exceptions.FalseFieldSize;
import Logic.main.*;
import Utilities.HoverState;
import Utilities.MyRandom;
import Utilities.Point;

import java.util.ArrayList;

import static Logic.main.LogicConstants.*;


public class Game {
    protected int size = 0;
    protected String name;
    protected GameMode gameMode;
    protected GameElement[][] gameField;
    protected ArrayList<Ship> ships = new ArrayList<>();
    protected Player player;
    protected int countTwoShip;
    protected int countThreeShip;
    protected int countFourShip;
    protected int countFiveShip;

    /**
     * Sets the Name of the Player
     *
     * @param n Name of Player
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * Sets the Size of the Game Field
     *
     * @param s Game Field Size
     */
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

    /**
     * Get the Size of the Game Field
     *
     * @return Game Field Size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Get the Name of the Player
     *
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the Mode of the  (Online/Offline)
     *
     * @param m Game Mode
     */
    public void setGameMode(GameMode m) {
        this.gameMode = m;
    }

    /**
     * Return Status of Game Element
     *
     * @param index of the Panel
     * @return Game Element Status
     */
    public GameElementStatus getgameElementStatus(int index) {
        int x = index % size;
        int y = index / size;

        return gameField[x][y].getStatus();
    }

    /**
     * Return Status of Game Element
     *
     * @param x Coordinate of the Panel
     * @param y Coordinate of the Panel
     * @return Game Element Status
     */
    public GameElementStatus getgameElementStatus(int x, int y) {

        return gameField[x][y].getStatus();
    }

    /**
     * Set Status of Game Element
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param status new Status
     */
    public void setgameElementStatus(int x, int y, GameElementStatus status) {
        gameField[x][y].setStatus(status);
    }

    /**
     * Check if all Ships are placed
     *
     * @return true if all ships are placed
     */
    public boolean allShipPlaced() {
        return getCountTwoShip() == 0 &&
                getCountThreeShip() == 0 &&
                getCountFourShip() == 0 &&
                getCountFiveShip() == 0;
    }

    /**
     * Gets Number of Two sized Ships, which aren't placed
     *
     * @return Number of Two sized Ships
     */
    public int getCountTwoShip() {
        return countTwoShip;
    }

    /**
     * Gets Number of Three sized Ships, which aren't placed
     *
     * @return Number of Three sized Ships
     */
    public int getCountThreeShip() {
        return countThreeShip;
    }

    /**
     * Gets Number of Four sized Ships, which aren't placed
     *
     * @return Number of Four sized Ships
     */
    public int getCountFourShip() {
        return countFourShip;
    }

    /**
     * Gets Number of Five sized Ships, which aren't placed
     *
     * @return Number of Five sized Ships
     */
    public int getCountFiveShip() {
        return countFiveShip;
    }

    /**
     * Initialize Game Field. Un place all Ships.
     * Determine Number of Ships
     */
    public void initializeGameField() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameField[i][j].init();
            }
        }
        ships = new ArrayList<>();
        determineNumberOfShips();
    }

    /**
     * Return a List of States, with Index, where the ship is and where the Edges are
     * And the list contains Error States, when placed near other ships
     *
     * @param index        of the middle Part of the ship
     * @param isHorizontal Orientation of the ship
     * @param ShipSize     Size of Ship
     * @return List of States, with Index
     */
    public HoverState[] getHoverStateStatus(int index, int ShipSize, boolean isHorizontal) {
        ArrayList<HoverState> stateList = new ArrayList<>();

        Point p = matchIndex(index);

        int x = p.x;
        int y = p.y;


        GameElementStatus shipStatus;
        if (shipinGameField(p, ShipSize, isHorizontal)) {
            shipStatus = GameElementStatus.SHIP;
        } else {
            shipStatus = GameElementStatus.ERROR;
        }

        switch (ShipSize) {
            case 2:

                if (isHorizontal == false) {


                    // Zweier Schiff oben
                    stateList = setShip(p.x, p.y, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesTop(x, y, stateList);

                    // Zweier Shiff unten
                    stateList = setShip(x, y + 1, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesButton(x, y + 1, stateList);

                } else {

                    // Zweier Schiff links
                    stateList = setShip(x, y, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesLeft(x, y, stateList);

                    // Zweier Shiff rechts
                    stateList = setShip(x + 1, y, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesRight(x + 1, y, stateList);
                }
                break;

            case 3:
                if (isHorizontal == false) {

                    // Dreier Schiff oben
                    stateList = setShip(x, y - 1, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesTop(x, y - 1, stateList);

                    // Dreier Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesLeftRight(x, y, stateList);

                    // Dreier Shiff unten
                    stateList = setShip(x, y + 1, stateList, shipStatus, 3, ShipSize, isHorizontal);
                    stateList = setEdgesButton(x, y + 1, stateList);

                } else {

                    // Dreier Schiff links
                    stateList = setShip(x - 1, y, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesLeft(x - 1, y, stateList);


                    // Dreier Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesUpDown(x, y, stateList);

                    // Dreier Shiff unten
                    stateList = setShip(x + 1, y, stateList, shipStatus, 3, ShipSize, isHorizontal);
                    stateList = setEdgesRight(x + 1, y, stateList);
                }
                break;

            case 4:
                if (isHorizontal == false) {

                    // Vierer Schiff oben
                    stateList = setShip(x, y - 1, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesTop(x, y - 1, stateList);

                    // Vierer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesLeftRight(x, y, stateList);

                    // Vierer Shiff mitte
                    stateList = setShip(x, y + 1, stateList, shipStatus, 3, ShipSize, isHorizontal);
                    stateList = setEdgesLeftRight(x, y + 1, stateList);

                    // Vierer Shiff unten
                    stateList = setShip(x, y + 2, stateList, shipStatus, 4, ShipSize, isHorizontal);
                    stateList = setEdgesButton(x, y + 2, stateList);

                } else {

                    // Vierer Schiff links
                    stateList = setShip(x - 1, y, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesLeft(x - 1, y, stateList);


                    // Vierer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesUpDown(x, y, stateList);

                    // Vierer Shiff mitte
                    stateList = setShip(x + 1, y, stateList, shipStatus, 3, ShipSize, isHorizontal);
                    stateList = setEdgesUpDown(x + 1, y, stateList);


                    // Vierer Shiff rechts + 2
                    stateList = setShip(x + 2, y, stateList, shipStatus, 4, ShipSize, isHorizontal);
                    stateList = setEdgesRight(x + 2, y, stateList);
                }
                break;

            case 5:
                if (isHorizontal == false) {

                    // Fünfer Schiff oben + 2
                    stateList = setShip(x, y - 2, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesTop(x, y - 2, stateList);

                    // Fünfer Schiff oben + 1
                    stateList = setShip(x, y - 1, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesLeftRight(x, y - 1, stateList);


                    // Fünfer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus, 3, ShipSize, isHorizontal);
                    stateList = setEdgesLeftRight(x, y, stateList);

                    // Fünfer Shiff unten + 1
                    stateList = setShip(x, y + 1, stateList, shipStatus, 4, ShipSize, isHorizontal);
                    stateList = setEdgesLeftRight(x, y + 1, stateList);

                    // Fünfer Shiff unten + 2
                    stateList = setShip(x, y + 2, stateList, shipStatus, 5, ShipSize, isHorizontal);
                    stateList = setEdgesButton(x, y + 2, stateList);


                } else {

                    // Fünfer Schiff links + 2
                    stateList = setShip(x - 2, y, stateList, shipStatus, 1, ShipSize, isHorizontal);
                    stateList = setEdgesLeft(x - 2, y, stateList);

                    // Fünfer Schiff links
                    stateList = setShip(x - 1, y, stateList, shipStatus, 2, ShipSize, isHorizontal);
                    stateList = setEdgesUpDown(x - 1, y, stateList);

                    // Fünfer Shiff mitte
                    stateList = setShip(x, y, stateList, shipStatus, 3, ShipSize, isHorizontal);
                    stateList = setEdgesUpDown(x, y, stateList);

                    // Fünfer Shiff mitte
                    stateList = setShip(x + 1, y, stateList, shipStatus, 4, ShipSize, isHorizontal);
                    stateList = setEdgesUpDown(x + 1, y, stateList);


                    // Vierer Shiff rechts + 2
                    stateList = setShip(x + 2, y, stateList, shipStatus, 5, ShipSize, isHorizontal);
                    stateList = setEdgesRight(x + 2, y, stateList);

                }

                break;

        }
        return stateList.toArray(new HoverState[stateList.size()]);
    }

    /**
     * Places a ship in the Game Element Array
     *
     * @param index        of the middle Part of the ship
     * @param isHorizontal Orientation of the ship
     * @param ShipSize     Size of Ship
     * @return true if ship is placed - false when not possible
     */
    public boolean placeShip(int index, int ShipSize, boolean isHorizontal) {
        int x = 0;
        int y = 0;
        Ship ship = null;
        // aktuellen Status des Schiffes holen
        HoverState[] hoverStates = getHoverStateStatus(index, ShipSize, isHorizontal);

        // prüfen ob Shiff plazierbar ist
        for (int i = 1; i < (hoverStates.length); i++) {
            if (hoverStates[i] != null && hoverStates[i].getStatus() == GameElementStatus.ERROR) {
                return false;
            }
        }

        // Schiff aus Arraylist ermitteln
        for (int s = 0; s < ships.size(); s++) {
            if (ships.get(s).getSize() == ShipSize) {
                ship = ships.get(s);
                removeShip(s);
                break;
            }
        }

        ship.setHorizontal(isHorizontal);

        // Status und Shiff auf Spielfeld ändern
        for (int i = 0; i < hoverStates.length; i++) {
            if (hoverStates[i] != null) {
                x = hoverStates[i].getIndex() % size;
                y = hoverStates[i].getIndex() / size;

                gameField[x][y].setStatus(hoverStates[i].getStatus());
                gameField[x][y].setShip(ship);
                gameField[x][y].setPart(hoverStates[i].getPart());

            }
        }
        return true;
    }

    /**
     * Randomize Placing of all Ships
     */
    public void shuffleShips() {
        int x = 0;
        int y = 0;
        int shipSize = 0;
        int trys = 0;
        boolean isHorizontal;

        initializeGameField();

        while (allShipPlaced() != true) {
            // Zufällig einen Platz aussuchen
            Point p = new Point(MyRandom.getRandomNumberInRange(0, size - 1), MyRandom.getRandomNumberInRange(0, size - 1));
            isHorizontal = MyRandom.getRandomBoolean();

            if (getCountFiveShip() != 0) {
                shipSize = 5;
            } else if (getCountFourShip() != 0) {
                shipSize = 4;
            } else if (getCountThreeShip() != 0) {
                shipSize = 3;
            } else if (getCountTwoShip() != 0) {
                shipSize = 2;
            }

            if (shipinGameField(p, shipSize, isHorizontal)) {
                // wenn nicht platzierbar bricht placeShip ab
                boolean placed = placeShip(matchIndex(p.x, p.y), shipSize, isHorizontal);

                if (placed == false && trys++ > 100) {
                    initializeGameField();
                    trys = 0;
                }
            }
        }
    }

    /**
     * Gets the Size of chosen Ship
     *
     * @param index of Pane
     * @return Size of Ship
     */
    public int getShipSize(int index) {
        int x = index % size;
        int y = index / size;

        return getShipSize(x, y);
    }

    /**
     * Gets the Size of chosen Ship
     *
     * @param x Coordinate of the Panel
     * @param y Coordinate of the Panel
     * @return Size of Ship
     */
    public int getShipSize(int x, int y) {

        if (gameField[x][y].getShip() != null) {
            return gameField[x][y].getShip().getSize();
        }
        return 0;
    }

    /**
     * Gets Ships Rotation
     *
     * @param index of Pane
     * @return true if Ship is horizontal
     */
    public boolean isShipHorizontal(int index) {
        int x = index % size;
        int y = index / size;
        Ship ship;

        if (gameField[x][y].getStatus() == GameElementStatus.SHIP) {
            return gameField[x][y].getShip().isHorizontal();
        }
        return false;
    }

    /**
     * Return the part of the ship
     * 1, 2, 3, 4, 5
     *
     * @param index of Pane
     * @return Part of the ship
     */
    public int getPartofShip(int index) {
        Point p = matchIndex(index);

        return gameField[p.x][p.y].getPart();
    }

    /**
     * Return if ship is destoryed
     *
     * @param x Coordinate of the Panel
     * @param y Coordinate of the Panel
     * @return true if Ship is destroyed
     */
    public boolean isShipDestroyed(int x, int y) {
        int countTrys = 0;

        if (gameField[x][y].getStatus() == GameElementStatus.SHIP || gameField[x][y].getStatus() == GameElementStatus.HIT) {

            Ship ship = gameField[x][y].getShip();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // if all Parts of the Ship are HIT return true
                    if (countTrys == ship.getSize()) {
                        return true;
                    }
                    // if a Pane is found which isn't destroyed return false
                    // else if Pane is HIT and the same Ship then increase Counter
                    if (gameField[i][j].getStatus() == GameElementStatus.SHIP && gameField[i][j].getShip() == ship) {
                        return false;
                    } else if (gameField[i][j].getStatus() == GameElementStatus.HIT && gameField[i][j].getShip() == ship) {
                        countTrys++;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return if all ship are destroyed
     *
     * @return true if all Ship are destroyed
     */
    public boolean allShipDestroyed() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // if a Ship is found not all are destroyed
                if (gameField[i][j].getStatus() == GameElementStatus.SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Delete a ship in the Array
     *
     * @param index of Pane
     * @return true if Ship was deleted
     */
    public boolean deleteShip(int index) {
        int x = index % size;
        int y = index / size;
        Ship ship;

        if (gameField[x][y].getStatus() == GameElementStatus.SHIP) {
            ship = gameField[x][y].getShip();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (gameField[i][j].getStatus() == GameElementStatus.CLOSE && gameField[i][j].isShipClose(ship)) {
                        gameField[i][j].removeShip(ship);
                        if (gameField[i][j].getCountShips() == 0) {
                            gameField[i][j].init();
                        }
                    } else if (gameField[i][j].getStatus() == GameElementStatus.SHIP && gameField[i][j].getShip() == ship) {
                        gameField[i][j].init();
                    }
                }
            }

            addShip(ship.getSize(), 1);
            return true;
        }
        return false;
    }

    /**
     * Return if a Coordinate is in the Array
     *
     * @param x Coordinate of the Panel
     * @param y Coordinate of the Panel
     * @return true if Coordinate is in the Array
     */
    public boolean inGameField(int x, int y) {

        if (x >= 0 && x < size &&
                y >= 0 && y < size) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Matches x y Coordinates to an Index of the Pane
     *
     * @param x Coordinate of the Panel
     * @param y Coordinate of the Panel
     * @return Index of the Pane
     */
    protected int matchIndex(int x, int y) {

        return y * (size) + x;
    }

    /**
     * Matches Index to x y Coordinates of the Pane
     *
     * @param index of Pane
     * @return Point of Pane
     */
    protected Point matchIndex(int index) {
        return new Point(index % size, index / size);
    }

    /**
     * Matches Index to x y Coordinates of the Pane
     *
     * @param size   of ship
     * @param number of ship inserted
     */
    protected void addShip(int size, int number) {
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

    /**
     * Return if ship is in Game Field
     *
     * @param p            x y Coordinate of the middle of the ship
     * @param ShipSize
     * @param isHorizontal Orientation of the ship
     */
    protected boolean shipinGameField(Point p, int ShipSize, boolean isHorizontal) {
        int x = p.x;
        int y = p.y;

        if (inGameField(x, y) == false) {
            return false;
        }
        if (gameField[x][y].getStatus() == GameElementStatus.SHIP ||
                gameField[x][y].getStatus() == GameElementStatus.CLOSE) {
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

    /**
     * Remove Ship from ship Array List
     *
     * @param index of Array List Index
     */
    protected void removeShip(int index) {

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

    /**
     * Create an Instance of a Player (Online / Offline / Self)
     *
     * @param t Player Type
     * @return Instance of Player
     */
    protected Player createPlayer(PlayerType t) {
        // erstelle je nach Spielertyp eine Unterklasse des Typ Spieler
        switch (t) {
            case ONLINE:
                return new OnlinePlayer(this);
            case OFFLINE:
                return new OfflinePlayer(this);
            case SELF:
                return new MyPlayer(this, name);
            default:
                return null;
        }
    }

    /**
     * Determine Number of Ships dependent on Game Field Size
     */
    protected void determineNumberOfShips() {
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

    /**
     * Add a new Ship to the Hover State List to an existing list
     *
     * @param x            Coordinate of the Panel
     * @param y            Coordinate of the Panel
     * @param isHorizontal Orientation of the ship
     * @param status       of the ship (SHIP or ERROR)
     * @param part         of the ship
     * @param shipSize     of the Ship
     * @param states       List of old States (ship will be added)
     * @return New List of Hover States
     */
    private ArrayList<HoverState> setShip(int x, int y, ArrayList<HoverState> states, GameElementStatus status, int part, int shipSize, boolean isHorizontal) {

        if (inGameField(x, y)) {
            states.add(new HoverState(matchIndex(x, y), status, part, shipSize, isHorizontal));
        }
        return states;
    }


    /**
     * Sets Close State to a Game Element
     *
     * @param x Coordinate of the Panel
     * @param y Coordinate of the Panel
     */
    private HoverState setEdge(int x, int y) {
        if (inGameField(x, y)) {
            // Ecke kann einach eingefügt werden
            if (getgameElementStatus(x, y) != GameElementStatus.SHIP) {
                return new HoverState(matchIndex(x, y), GameElementStatus.CLOSE);
            } // Ecke überlappt mit einem anderen Shiff
            else // if (getgameElementStatus(x, y) == GameElementStatus.SHIP) {
                return new HoverState(matchIndex(x, y), GameElementStatus.ERROR);
        }

        return null;
    }


    // ___________________________________________Ränder der Shiffe setzen__________________________________

    /**
     * Add Edges to the Top of a ship
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param states List of old States (Edges will be added)
     * @return New List of Hover States
     */
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

    /**
     * Add Edges to the Button of a ship
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param states List of old States (Edges will be added)
     * @return New List of Hover States
     */
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

    /**
     * Add Edges to the Left of a ship
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param states List of old States (Edges will be added)
     * @return New List of Hover States
     */
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

    /**
     * Add Edges to the Right of a ship
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param states List of old States (Edges will be added)
     * @return New List of Hover States
     */
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

    /**
     * Add Edges to the Left and Right of a ship
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param states List of old States (Edges will be added)
     * @return New List of Hover States
     */
    private ArrayList<HoverState> setEdgesLeftRight(int x, int y, ArrayList<HoverState> states) {
        //Rand links
        states.add(setEdge(x - 1, y));

        //Rand rechts
        states.add(setEdge(x + 1, y));

        return states;
    }

    /**
     * Add Edges to the Up and Button of a ship
     *
     * @param x      Coordinate of the Panel
     * @param y      Coordinate of the Panel
     * @param states List of old States (Edges will be added)
     * @return New List of Hover States
     */
    private ArrayList<HoverState> setEdgesUpDown(int x, int y, ArrayList<HoverState> states) {
        // Rand unten
        states.add(setEdge(x, y + 1));

        // Rand oben
        states.add(setEdge(x, y - 1));

        return states;
    }
}
