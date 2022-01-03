package Logic.main;

import Logic.Game.Game;
import Utilities.MyRandom;
import Utilities.Point;

public class OfflinePlayer extends Player {


    public OfflinePlayer(Game game) {
        super(game);
    }


    @Override
    public void takeTurn() {
        Point firstHit = null;
        LogicConstants.Direction direction = LogicConstants.Direction.NONE;
        boolean treffer;

        if (firstHit != null && direction != LogicConstants.Direction.NONE) {

            switch (direction) {
                case UP:
                    shootDirection(firstHit, LogicConstants.Direction.DOWN);
                    break;


            }
        }







    //   while (isHit) {

    //       Point p = determineNextShot();


    //       // kurz warten
    //       try {
    //           Thread.sleep(100);
    //       } catch (InterruptedException e) {
    //           e.printStackTrace();
    //       }

    //       isHit = shoot(p.x, p.y);
    //   }
    }

    private Point determineNextShot() {


        int x = 0, y = 0;
        // Zufällig einen Platz aussuchen
        x = MyRandom.getRandomNumberInRange(0, game.getSize() - 1);
        y = MyRandom.getRandomNumberInRange(0, game.getSize() - 1);

        return new Point(x, y);
    }

    private void shootDirection(Point p, LogicConstants.Direction direction) {
        boolean isHit = true;

        while (isHit) {

            switch (direction) {
                case UP:
                    p.y--;
                    break;
                case DOWN:
                    p.y++;
                    break;
                case LEFT:
                    p.x--;
                    break;
                case RIGHT:
                    p.x++;
                    break;
            }

            if(game.inGameField(p.x, p.y)){
                isHit = shoot(p.x,p.y);
            }else{
                isHit = false;
            }
        }
    }

}
