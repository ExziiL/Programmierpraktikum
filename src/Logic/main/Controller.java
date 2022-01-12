package Logic.main;

import Logic.Game.EnemyGame;
import Logic.Game.Exceptions.FalseFieldSize;
import Logic.Game.MyGame;
import Utilities.HoverState;

import static Logic.main.LogicConstants.GameElementStatus;
import static Logic.main.LogicConstants.GameMode;

public class Controller {
    private MyGame myGame;
    private EnemyGame enemyGame;

    private boolean concratulation;

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

    public void setGameMode(GameMode m) {
        myGame.setGameMode(m);
    }

    public int getGameSize() {
        return myGame.getSize();
    }

    public String getName() {
        return myGame.getName();
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

    public GameElementStatus getGameElementStatus(int index) {
        return myGame.getgameElementStatus(index);
    }

    public HoverState[] getHoverStateStatus(int index, int size, boolean isHorizontal) {
        return myGame.getHoverStateStatus(index, size, isHorizontal);
    }

    public void placeShip(int element, int size, boolean isHorizontal) {
        myGame.placeShip(element, size, isHorizontal);
    }

    public boolean allShipPlaced() {
        return myGame.allShipPlaced();
    }

    public void shuffleShips() {
        myGame.shuffleShips();
    }

    public void initializeGameField() {
        myGame.initializeGameField();
    }

    public boolean deleteShip(int index) {
        return myGame.deleteShip(index);
    }

    public int getShipSize(int index) {
        return myGame.getShipSize(index);
    }

    public boolean isShipHorizontal(int index) {
        return myGame.isShipHorizontal(index);
    }

    public boolean isElementShip(int index) {
        return myGame.getgameElementStatus(index) == GameElementStatus.SHIP;
    }

    public boolean allShipsDestroyed() {
        return myGame.allShipDestroyed();
    }

    public void setConcratulation(boolean concratulation) {
        this.concratulation = concratulation;
    }

    public boolean isConcratulation() {
        return concratulation;
    }

    // ---------------------------------------------------------------
    // Methods for Enemy Game
    public GameElementStatus getEnemyElementStatus(int index) {
        return enemyGame.getgameElementStatus(index);
    }

    public void createEnemyGame() {
        enemyGame = new EnemyGame(getGameSize());
    }

    public boolean shoot(int index) {
        return enemyGame.shoot(index);
    }

    public boolean enemyTurn() {
        return myGame.enemyTurn();
    }

    public boolean allEnemyShipsDestroyed() {
        return enemyGame.allShipDestroyed();
    }
}
