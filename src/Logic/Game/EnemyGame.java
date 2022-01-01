package Logic.Game;

import Logic.Game.Exceptions.FalseFieldSize;
import Logic.main.LogicConstants;

public class EnemyGame extends Game {

    public EnemyGame(int size) {
        super();
        try {
            setSize(size);

        } catch (FalseFieldSize e) {
            System.out.println(e);
        }

        determineNumberOfShips();
        shuffleShips();
    }


    public boolean shoot(int index) {
        int x = index % size;
        int y = index / size;

        if (gameField[x][y].getStatus() == LogicConstants.GameElementStatus.SHIP) {
            gameField[x][y].setStatus(LogicConstants.GameElementStatus.HIT);
            return true;
        }
        return false;
    }
}
