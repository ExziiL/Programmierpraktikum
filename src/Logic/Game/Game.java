package Logic.Game;

import Logic.Game.Exceptions.*;

import static Logic.main.LogicConstants.*;

import Logic.main.*;
import Utilities.Exception.ShipOutofGame;
import Utilities.Hover.HoverState;

import java.util.ArrayList;


public class Game {
    protected int size = 0;
    protected String name;
    protected GameElement[][] gameField;
    protected Player player;
    protected ArrayList<Ship> ships = new ArrayList<Ship>();
    protected int countTwoShip;
    protected int countThreeShip;
    protected int countFourShip;
    protected int countFiveShip;

    protected enum direction {up, left, down, right, topLeft, topRight, buttonLeft, buttonRight}

    public Game() {


    }

    public void initializeGameField() {
        System.out.println("Spielfeld initialisiert");
    }

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
        ArrayList<HoverState> states = new ArrayList<>();

        int x = index % size;
        int y = index / size;
        int bufferIndex = 0;

        switch (ShipSize) {
            case 2:
                if (isHorizontal == false) {
                    if (inGameField(x, y + 1)) {
                        // Zweier Schiff oben
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesTop(x, y, index, states);

                        // Zweier Shiff unten
                        bufferIndex = matchIndex(index, direction.down, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesButton(x, y + 1, bufferIndex, states);
                    }
                } else {
                    if (inGameField(x + 1, y)) {

                        // Zweier Schiff links
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesLeft(x, y, index, states);

                        // Zweier Shiff rechts
                        bufferIndex = matchIndex(index, direction.right, 1); // Index ermitteln
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesRight(x + 1, y, bufferIndex, states);
                    }
                }
                break;

            case 3:
                if (isHorizontal == false) {

                    if (inGameField(x, y + 1) && inGameField(x, y - 1)) {
                        // Dreier Schiff oben
                        bufferIndex = matchIndex(index, direction.up, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesTop(x, y - 1, bufferIndex, states);

                        // Dreier Shiff mitte
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesLeftRight(x, y, index, states);

                        // Dreier Shiff unten
                        bufferIndex = matchIndex(index, direction.down, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesButton(x, y + 1, bufferIndex, states);
                    }
                } else {
                    if (inGameField(x - 1, y) && inGameField(x + 1, y)) {
                        // Dreier Schiff links
                        bufferIndex = matchIndex(index, direction.left, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesLeft(x - 1, y, bufferIndex, states);


                        // Dreier Shiff mitte
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesUpDown(x, y, index, states);

                        // Dreier Shiff unten
                        bufferIndex = matchIndex(index, direction.right, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesRight(x + 1, y, bufferIndex, states);
                    }
                }
                break;

            case 4:
                if (isHorizontal == false) {

                    if (inGameField(x, y - 1) && inGameField(x, y + 1) && inGameField(x, y + 2)) {
                        // Vierer Schiff oben
                        bufferIndex = matchIndex(index, direction.up, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesTop(x, y - 1, bufferIndex, states);

                        // Vierer Shiff mitte
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesLeftRight(x, y, index, states);

                        // Vierer Shiff mitte
                        bufferIndex = matchIndex(index, direction.down, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesLeftRight(x, y + 1, bufferIndex, states);

                        // Vierer Shiff unten
                        bufferIndex = matchIndex(index, direction.down, 2);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesButton(x, y + 2, bufferIndex, states);
                    }

                } else {
                    if (inGameField(x - 1, y) && inGameField(x + 1, y) && inGameField(x + 2, y)) {
                        // Vierer Schiff links
                        bufferIndex = matchIndex(index, direction.left, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesLeft(x - 1, y, bufferIndex, states);


                        // Vierer Shiff mitte
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesUpDown(x, y, index, states);

                        // Vierer Shiff mitte
                        bufferIndex = matchIndex(index, direction.right, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesUpDown(x + 1, y, bufferIndex, states);


                        // Vierer Shiff rechts + 2
                        bufferIndex = matchIndex(index, direction.right, 2);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesRight(x + 2, y, bufferIndex, states);
                    }
                }
                break;

            case 5:
                if (isHorizontal == false) {

                    if (inGameField(x, y - 2) && inGameField(x, y - 1) && inGameField(x, y + 1) && inGameField(x, y + 2)) {
                        // Fünfer Schiff oben + 2
                        bufferIndex = matchIndex(index, direction.up, 2);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesTop(x, y - 2, bufferIndex, states);

                        // Fünfer Schiff oben + 1
                        bufferIndex = matchIndex(index, direction.up, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesLeftRight(x, y - 1, bufferIndex, states);


                        // Fünfer Shiff mitte
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesLeftRight(x, y, index, states);

                        // Fünfer Shiff unten + 1
                        bufferIndex = matchIndex(index, direction.down, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesLeftRight(x, y + 1, bufferIndex, states);

                        // Vierer Shiff unten + 2
                        bufferIndex = matchIndex(index, direction.down, 2);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesButton(x, y + 2, bufferIndex, states);
                    }

                } else {
                    if (inGameField(x - 2, y) && inGameField(x - 1, y) && inGameField(x + 1, y) && inGameField(x + 2, y)) {
                        // Vierer Schiff links + 2
                        bufferIndex = matchIndex(index, direction.left, 2);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesLeft(x - 2, y, bufferIndex, states);

                        // Vierer Schiff links
                        bufferIndex = matchIndex(index, direction.left, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesUpDown(x - 1, y, bufferIndex, states);

                        // Vierer Shiff mitte
                        states.add(new HoverState(index, GameElementStatus.SHIP));
                        states = setEdgesUpDown(x, y, index, states);

                        // Vierer Shiff mitte
                        bufferIndex = matchIndex(index, direction.right, 1);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesUpDown(x + 1, y, bufferIndex, states);


                        // Vierer Shiff rechts + 2
                        bufferIndex = matchIndex(index, direction.right, 2);
                        states.add(new HoverState(bufferIndex, GameElementStatus.SHIP));
                        states = setEdgesRight(x + 2, y, bufferIndex, states);
                    }
                }

                break;

        }
        return states.toArray(new HoverState[states.size()]);
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

    private boolean inGameField(int x, int y) {

        if (x >= 0 && x < size &&
                y >= 0 && y < size) {
            return true;
        } else {
            return false;
        }
    }

    private HoverState setEdge(int x, int y, int index, direction direction) {
        if (inGameField(x, y)) {
            // Ecke kann einach eingefügt werden
            if (getgameElementStatus(x, y) != GameElementStatus.CLOSE && getgameElementStatus(x, y) != GameElementStatus.SHIP) {
                return new HoverState(matchIndex(index, direction, 1), GameElementStatus.CLOSE);
            } // Ecke überlappt mit einem anderen Shiff
            else if (getgameElementStatus(x, y) == GameElementStatus.SHIP) {
                return new HoverState(matchIndex(index, direction, 1), GameElementStatus.ERROR);
            }
        }
        return null;
    }

    private int matchIndex(int index, direction direction, int range) {
        switch (direction) {
            case up:
                return index - (range * size);

            case down:
                return index + (range * size);

            case left:
                return index - range;

            case right:
                return index + range;
            case topLeft:
                return (index - (range * size)) - 1;
            case topRight:
                return (index - (range * size)) + 1;
            case buttonLeft:
                return (index + (range * size) - 1);
            case buttonRight:
                return (index + (range * size)) + 1;
        }
        return 0;
    }

    // ___________________________________________Ränder der Shiffe setzen__________________________________
    private ArrayList<HoverState> setEdgesTop(int x, int y, int index, ArrayList<HoverState> states) {

        // Rand oben
        states.add(setEdge(x, y - 1, index, direction.up));

        // Rand rechts
        states.add(setEdge(x + 1, y, index, direction.right));

        //Rand links
        states.add(setEdge(x - 1, y, index, direction.left));

        //Rand rechtoben
        states.add(setEdge(x + 1, y - 1, index, direction.topRight));

        //Rand link oben
        states.add(setEdge(x - 1, y - 1, index, direction.topLeft));

        return states;
    }

    private ArrayList<HoverState> setEdgesButton(int x, int y, int index, ArrayList<HoverState> states) {
        states.add(new HoverState(index, GameElementStatus.SHIP));

        // Rand unten
        states.add(setEdge(x, y + 1, index, direction.down));

        // Rand rechts
        states.add(setEdge(x + 1, y, index, direction.right));

        //Rand links
        states.add(setEdge(x - 1, y, index, direction.left));

        //Rand recht unten
        states.add(setEdge(x + 1, y + 1, index, direction.buttonRight));

        //Rand link unten
        states.add(setEdge(x - 1, y + 1, index, direction.buttonLeft));


        return states;
    }

    private ArrayList<HoverState> setEdgesLeft(int x, int y, int index, ArrayList<HoverState> states) {

        // Rand unten
        states.add(setEdge(x, y + 1, index, direction.down));

        // Rand oben
        states.add(setEdge(x, y - 1, index, direction.up));

        //Rand links
        states.add(setEdge(x - 1, y, index, direction.left));

        //Rand links oben
        states.add(setEdge(x - 1, y - 1, index, direction.topLeft));

        //Rand links unten
        states.add(setEdge(x - 1, y + 1, index, direction.buttonLeft));


        return states;
    }

    private ArrayList<HoverState> setEdgesRight(int x, int y, int index, ArrayList<HoverState> states) {

        // Rand unten
        states.add(setEdge(x, y + 1, index, direction.down));

        // Rand oben
        states.add(setEdge(x, y - 1, index, direction.up));

        //Rand rechts
        states.add(setEdge(x + 1, y, index, direction.right));

        //Rand rechts oben
        states.add(setEdge(x + 1, y - 1, index, direction.topRight));

        //Rand links unten
        states.add(setEdge(x + 1, y + 1, index, direction.buttonRight));


        return states;
    }

    private ArrayList<HoverState> setEdgesLeftRight(int x, int y, int index, ArrayList<HoverState> states) {
        //Rand links
        states.add(setEdge(x - 1, y, index, direction.left));

        //Rand rechts
        states.add(setEdge(x + 1, y, index, direction.right));

        return states;
    }

    private ArrayList<HoverState> setEdgesUpDown(int x, int y, int index, ArrayList<HoverState> states) {
        // Rand unten
        states.add(setEdge(x, y + 1, index, direction.down));

        // Rand oben
        states.add(setEdge(x, y - 1, index, direction.up));

        return states;
    }
}
