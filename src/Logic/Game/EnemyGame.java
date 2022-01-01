package Logic.Game;

import Logic.Game.Exceptions.FalseFieldSize;
import Logic.main.LogicConstants;
import Logic.main.MyPlayer;

public class EnemyGame extends Game {

    public EnemyGame(int size) {
        super();
        try {
            setSize(size);

        } catch (FalseFieldSize e) {
            System.out.println(e);
        }

        player = new MyPlayer(this, name);
        determineNumberOfShips();
        shuffleShips();
    }

    public boolean shoot(int index) {
        int x = index % size;
        int y = index / size;

        return player.shoot(x, y);
    }
}
