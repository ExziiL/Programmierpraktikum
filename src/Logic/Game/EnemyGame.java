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

    }

    public int shoot(int index) {
        int x = index % size;
        int y = index / size;
        int hit = 0;

        if (GUI.Game.logicController.getGameMode() == LogicConstants.GameMode.OFFLINE) {
            hit = player.shoot(x, y);

            if (hit == 0 && isMyShipDestroyed(x, y)) {

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
        } else if (GUI.Game.logicController.getGameMode() == LogicConstants.GameMode.ONLINE) {
            hit = player.shoot(x, y);
            int shipSize = getCountOfDestroyedShip(x, y);
            if (hit == 2 && shipSize > 0) {
                hit = 0;
                switch (shipSize) {
                    case 1:
                        break;
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
        destroyedTwoShips = allTwoShip;
        destroyedThreeShips = allThreeShip;
        destroyedFourShips = allFourShip;
        destroyedFiveShips = allFiveShip;
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
    public void setGameMode(LogicConstants.GameMode m) {
        super.setGameMode(m);
        if (gameMode == LogicConstants.GameMode.OFFLINE) {
            determineNumberOfShips();
            shuffleShips();
        }
        setDestroyedShips();

        // switch (gameMode) {
        //     case BOT:
        //         player = createPlayer(LogicConstants.PlayerType.OFFLINE);
        //         break;
        //     case OFFLINE:
        //         player = player = new MyPlayer(this, name);
        //         break;
        // }
    }

    @Override
    public boolean allShipDestroyed() {
        return (destroyedTwoShips == 0 && destroyedThreeShips == 0 && destroyedFourShips == 0 && destroyedFiveShips == 0);
    }
}
