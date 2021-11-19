package Logic.main;

//import java.awt.*;

import Logic.Game.*;

public class Player {

    protected Game game;
    protected String name;
    protected int countShots;
    protected int countHits;
    protected int count_destroyed;

    protected static int countMoves;

    public String shoot;

    public Player(Game game, String name) {
        this.game = game;
        this.name = name;

        System.out.println(name);
    }

    public void shoot() {
    }

    public void placeShips() {
    }

    public void statistik() {

        System.out.println("Anzahl Schüsse: " + countShots);
        System.out.println("Anzahl Treffer: " + countHits);
        System.out.println("Zerstört: " + count_destroyed);
    }

    ;

    // public Point feuern(){}

    /*
     * public Point getroffen(){ switch (spielarray[""][""]){ case 0:
     * System.out.println("Verfehlt!");break; case 1:
     * System.out.println("Treffer!");break; case 2:
     * System.out.println("Versenkt!!!");break; default: break; } ; }
     */
}
