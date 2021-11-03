package Logic.Spielfeld;

import Logic.Spielfeld.Ausnahmen.*;

public class Spielfeld {
    private int groesse;
    private int[][] spielfeld;

    public Spielfeld(int groesse) throws FalscheSpielfeldGroesse {

        if (groesse < 5 || groesse > 30) {
            throw new FalscheSpielfeldGroesse(groesse);
        }

        this.groesse = groesse;
        spielfeld = new int[groesse][groesse];
    }
}
