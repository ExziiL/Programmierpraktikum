package Logic.main;

import Logic.DocumentWriter.DocumentWriter;
import GUI.Game;
import Logic.Game.EnemyGame;
import Logic.Game.Exceptions.FalseFieldSize;
import Logic.Game.MyGame;
import Utilities.HoverState;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import static Logic.main.LogicConstants.GameElementStatus;
import static Logic.main.LogicConstants.GameMode;

public class Controller {
    //region Local Variables
    private MyGame myGame;
    private EnemyGame enemyGame;
    private boolean concratulation;
    private DocumentWriter writer;
    //endregion

    /**
     * Constructor for Controller-Class creates a new Game
     */
    public Controller() {
        myGame = new MyGame();
        Timestamp instant = Timestamp.from(Instant.now());
        writer = new DocumentWriter(instant);
    }

    /**
     * Exits the Game-Window
     */
    public void exitGame() {
        System.exit(0);
    }

    /**
     * Sets the name of the player
     *
     * @param n name of player
     */
    public void setName(String n) {
        myGame.setName(n);
    }

    /**
     * Sets the wanted Size of the Playingfield. If something goes wrong a FalseFieldSize Exception will be thrown
     *
     * @param n Playingfield-Size
     */
    public void setGameSize(int n) {
        try {
            myGame.setSize(n);
            createEnemyGame();
        } catch (FalseFieldSize falseFieldSize) {
            System.out.println("Falsche Spielfeldgröße");
        }
    }

    public void determineNumberOfShips(){
        myGame.determineNumberOfShips();
    }


    /**
     * Sets the Mode in which the Game is executed
     *
     * @param m Gamemode: Offline/Online
     */
    public void setGameMode(GameMode m) {
        myGame.setGameMode(m);
    }

    /**
     * Gets the Playingfield-Size
     * @return Playingfield-Size
     */
    public int getGameSize() {
        return myGame.getSize();
    }

    /**
     * Gets the Game Mode
     * @return Game Mode
     */
    public GameMode getGameMode(){
        return myGame.getGameMode();
    }

    /**
     * Gets the Players Name
     * @return Name of Player
     */
    public String getName() {
        return myGame.getName();
    }

    /**
     * Gets the amount of Size 2 Ships
     * @return count of ships of Size 2
     */
    public int getCountTwoShip() {
        return myGame.getCountTwoShip();
    }

    /**
     * Gets the amount of Size 3 Ships
     * @return count of ships of Size 3
     */
    public int getCountThreeShip() {
        return myGame.getCountThreeShip();
    }

    /**
     * Gets the amount of Size 4 Ships
     * @return count of ships of Size 4
     */
    public int getCountFourShip() {
        return myGame.getCountFourShip();
    }

    /**
     *Gets the amount of Size 5 Ships
     * @return count of ships of Size 5
     */
    public int getCountFiveShip() {
        return myGame.getCountFiveShip();
    }

    public int getAllTwoShips() {
        return myGame.getAllTwoShips();
    }

    public int getAllThreeShips() {
        return myGame.getAllThreeShips();
    }

    public int getAllFourShips() {
        return myGame.getAllFourShips();
    }

    public int getAllFiveShips() {
        return myGame.getAllFiveShips();
    }

    /**
     * Gets the Status of the Pane with index
     *
     * @param index of a pane
     * @return Status of the index-pane
     */
    public GameElementStatus getGameElementStatus(int index) {
        return myGame.getgameElementStatus(index);
    }

    /**
     * Gets a List of Hover-States of the Ship
     * @param index of Pane
     * @param size of selected Ship
     * @param isHorizontal Orientation of the Ship
     * @return HoverState[]: list of Indexes and States
     */
    public HoverState[] getHoverStateStatus(int index, int size, boolean isHorizontal) {
        return myGame.getHoverStateStatus(index, size, isHorizontal);
    }

    /**
     * Placing a Ship in Playing- and Placing-Field
     * @param index of Pane where the Midpoint of Ship should be placed
     * @param size of the Ship
     * @param isHorizontal Orientation of Ship
     */
    public void placeShip(int index, int size, boolean isHorizontal) {
        myGame.placeShip(index, size, isHorizontal);
    }

    /**
     * Are all Ship placed ?
     * @return true if all Ships are placed - false if not
     */
    public boolean allShipPlaced() {
        return myGame.allShipPlaced();
    }

    /**
     * Randomize Placing of all Ships
     */
    public void shuffleShips() {
        myGame.shuffleShips();
    }

    /**
     * Initialize Gamefield
     */
    public void initializeGameField() {
        myGame.initializeGameField();
    }

    /**
     * Deletes the Ship
     * @param index of Pane
     * @return true if Ship successfully deleted
     */
    public boolean deleteShip(int index) {
        return myGame.deleteShip(index);
    }

    /**
     * Gets the Size of chosen Ship
     * @param index of Pane
     * @return Size of Ship
     */
    public int getShipSize(int index) {
        return myGame.getShipSize(index);
    }

    /**
     * Gets Ships Rotation
     * @param index of Pane
     * @return true if Ship is horizontal
     */
    public boolean isShipHorizontal(int index) {
        return myGame.isShipHorizontal(index);
    }

    /**
     * Gets Ships Part (For Display)
     *
     * @param index of Pane
     * @return the Part of the Ship( 1, 2, ... ,5)
     */
    public int getPartofShip(int index) {
        return myGame.getPartofShip(index);
    }
    /**
     * Checks if Pane is Part of a Ship
     * @param index of Pane
     * @return true if Status of Pane is SHIP
     */
    public boolean isElementShip(int index) {
        return myGame.getgameElementStatus(index) == GameElementStatus.SHIP;
    }

    /**
     * Checks if all Ships are destroyed
     * @return true if every Ship is destroyed
     */
    public boolean allShipsDestroyed() {
        return myGame.allShipDestroyed();
    }

    /**
     * Sets if Player has won
     * @param concratulation
     */
    public void setConcratulation(boolean concratulation) {
        this.concratulation = concratulation;
    }

    public boolean isConcratulation() {
        return concratulation;
    }

    //region Methods for Enemy-Game

    /**
     * Gets the States of Panes in the Enemy-Playingfield
     * @param index of Pane
     * @return Status of Pane
     */
    public GameElementStatus getEnemyElementStatus(int index) {
        return enemyGame.getgameElementStatus(index);
    }

    /**
     * Creates an instance of EnemyGame
     */
    public void createEnemyGame() {
        enemyGame = new EnemyGame(getGameSize());
    }

    /**
     * Checks if a Ship of the Enemy is hit
     * @param index of Pane
     * @return true if EnemyShip is hit
     */
    public boolean shoot(int index) {
        return enemyGame.shoot(index);
    }

    /**
     * Checks if Enemy hit a Ship
     * @return true if enemy hits a Ship
     */
    public boolean enemyTurn() {
        return myGame.enemyTurn();
    }

    /**
     * Checks if Enemy has lost
     * @return true if all EnemyShip are destroyed
     */
    public boolean allEnemyShipsDestroyed() {
        return enemyGame.allShipDestroyed();
    }

    /**
     * Gets Count of Destroyed Ships
     *
     * @param size of Ship
     * @return Count of Destroyed Ship of specific Size
     */
    public int getDestroyedShips(int size) {
        return enemyGame.getDestroyedShips(size);
    }
    //endregion


    //region Methods for Save Game / Online Game

    public void initDocument() {

        writer.writeSize(getGameSize());
        writer.writeShips(myGame.getAllTwoShips(), myGame.getAllThreeShips(), myGame.getAllFourShips(), myGame.getAllFiveShips());

    }

    public void save() {

        writer.writeGameMode(myGame.getGameMode());
        writer.writeShipsDestroyed(enemyGame.getDestroyedShips(2),enemyGame.getDestroyedShips(3),enemyGame.getDestroyedShips(4),enemyGame.getDestroyedShips(5));
        writer.writeEnemyGameField(enemyGame.getGameField());
        writer.writeMyGameField(myGame.getGameField());
        writer.save();
        writer.close();
    }

    public ArrayList<String> getAllSaveFiles() {

        return writer.getAllSaveFiles();
    }

    public boolean deleteFile(String s){
        return DocumentWriter.deleteFile(s);
    }

    public void setWriter(DocumentWriter writer) {

        this.writer = writer;
    }

    public void loadGame() {
        int x;
        int y;
        GameElementStatus status;

        ArrayList<String> input = writer.load();

        for (String s : input) {
            String[] split = s.split(" ");

            switch (split[0]) {
                case "size":
                    setGameSize(Integer.parseInt(split[1]));
                    break;

                case "gameMode":
                    myGame.setGameMode(GameMode.valueOf(split[1]));
                    break;
                case "shipsDestroyed":
                    enemyGame.setDestroyedTwoShips(Integer.parseInt(split[1]));
                    enemyGame.setDestroyedThreeShips(Integer.parseInt(split[2]));
                    enemyGame.setDestroyedFourShips(Integer.parseInt(split[3]));
                    enemyGame.setDestroyedFiveShips(Integer.parseInt(split[4]));
                    break;
                case "ships":
                    myGame.setAllTwoShips(Integer.parseInt(split[1]));
                    myGame.setAllThreeShips(Integer.parseInt(split[2]));
                    myGame.setAllFourShips(Integer.parseInt(split[3]));
                    myGame.setAllFiveShips(Integer.parseInt(split[4]));
                    break;
                case "MyGame":

                    x = Integer.parseInt(split[1]);
                    y = Integer.parseInt(split[2]);
                    status = interpretStatusByNumber(Integer.parseInt(split[3]));
                    myGame.setgameElementStatus(x, y, status);

                    if (status == GameElementStatus.SHIP || status == GameElementStatus.HIT) {
                        myGame.setGameElementShip(x, y, Integer.parseInt(split[4]), Boolean.parseBoolean(split[5]), Integer.parseInt(split[6]));
                    }
                    break;
                case "EnemyGame":
                    x = Integer.parseInt(split[1]);
                    y = Integer.parseInt(split[2]);

                    status = interpretStatusByNumber(Integer.parseInt(split[3]));
                    enemyGame.setgameElementStatus(x, y, status);

                    if (status == GameElementStatus.SHIP || status == GameElementStatus.HIT) {
                        enemyGame.setGameElementShip(x, y, Integer.parseInt(split[4]), Boolean.parseBoolean(split[5]),Integer.parseInt(split[6]));
                    }
                    break;
            }
        }
    }


    private GameElementStatus interpretStatusByNumber(int s) {

        switch (s) {
            case 1:
                return GameElementStatus.SHIP;
            case 2:
                return GameElementStatus.MISS;
            case 3:
                return GameElementStatus.CLOSE;
            case 4:
                return GameElementStatus.ERROR;
            case 5:
                return GameElementStatus.HIT;
            default:
                return GameElementStatus.WATER;
        }
    }


    //endregion
}
