package Logic.Game;

import Logic.Game.Exceptions.FalseFieldSize;
import Logic.main.LogicConstants;
import Logic.main.MyPlayer;

public class EnemyGame extends Game {

    private int destroyedTwoShips = 0;
    private int destroyedThreeShips = 0;
    private int destroyedFourShips = 0;
    private int destroyedFiveShips = 0;

    public EnemyGame(int size) {
        super();
        try {
            setSize(size);

        } catch (FalseFieldSize e) {
            System.out.println(e);
        }

        player = new MyPlayer(this, name);

        determineNumberOfShips();
        setDestroyedShips();
        shuffleShips();
    }

    public boolean shoot(int index) {
        int x = index % size;
        int y = index / size;
        boolean hit = false;

        if (GUI.Game.logicController.getGameMode() == LogicConstants.GameMode.OFFLINE) {
            hit = player.shoot(x, y);

            if (hit && isShipDestroyed(x, y)) {

                switch (getShipSize(x, y)) {
                    case 2:
                        destroyedTwoShips--;
                        break;
                    case 3:
                        destroyedThreeShips--;
                        break;
                    case 4:
                        destroyedFourShips--;
                        break;
                    case 5:
                        destroyedFiveShips--;
                        break;
                }
            }
        } else if (GUI.Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE){
            player.shoot(x,y);
        }

        return hit;
    }

    public int getDestroyedShips(int size) {
        switch (size) {
            case 2:
                return destroyedTwoShips;

            case 3:
                return destroyedThreeShips;

            case 4:
                return destroyedFourShips;

            case 5:
                return destroyedFiveShips;
            default:
                return 0;
        }
    }

    private void setDestroyedShips() {
        destroyedTwoShips = countTwoShip;
        destroyedThreeShips = countThreeShip;
        destroyedFourShips = countFourShip;
        destroyedFiveShips = countFiveShip;
    }

    public void setDestroyedTwoShips(int destroyedTwoShips) {
        this.destroyedTwoShips = destroyedTwoShips;
    }

    public void setDestroyedThreeShips(int destroyedThreeShips) {
        this.destroyedThreeShips = destroyedThreeShips;
    }

    public void setDestroyedFourShips(int destroyedFourShips) {
        this.destroyedFourShips = destroyedFourShips;
    }

    public void setDestroyedFiveShips(int destroyedFiveShips) {
        this.destroyedFiveShips = destroyedFiveShips;
    }

    @Override
    public boolean allShipDestroyed() {
        return (destroyedTwoShips == 0 && destroyedThreeShips == 0 && destroyedFourShips == 0 && destroyedFiveShips == 0);
    }
}
