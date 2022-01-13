package Logic.main;

import Logic.Game.EnemyGame;
import Logic.Game.Exceptions.FalseFieldSize;
import Logic.Game.MyGame;
import Utilities.HoverState;

import static Logic.main.LogicConstants.GameElementStatus;
import static Logic.main.LogicConstants.GameMode;

public class Controller {
    //region Local Variables
    private MyGame myGame;
    private EnemyGame enemyGame;
    private boolean concratulation;
    //endregion

    /**
     * Constructor for Controller-Class creates a new Game
     */
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

    /**
     * Exits the Game-Window
     */
    public void exitGame() {
        System.exit(0);
    }

    /**
     * Sets the name of the player
     * @param n name of player
     */
    public void setName(String n) {
        myGame.setName(n);
    }

    /**
     * Sets the wanted Size of the Playingfield. If something goes wrong a FalseFieldSize Exception will be thrown
     * @param n Playingfield-Size
     */
    public void setGameSize(int n) {
        try {
            myGame.setSize(n);
        } catch (FalseFieldSize falseFieldSize) {
            System.out.println("Falsche Spielfeldgröße");
        }
    }

    /**
     * Sets the Mode in which the Game is executed
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

    /**
     * Gets the Status of the Pane with index
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

    public int getPartofShip(int index){
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
    //endregion
}
