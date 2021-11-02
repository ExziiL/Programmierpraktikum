package Logic;

import Logic.Ausnahmen.FalscheSpielfeldGroesse;

public class Spielfeld {
    private int groesse;
    private int[][] spielfeld;

    public Spielfeld(int groesse) throws FalscheSpielfeldGroesse {

        if (groesse < 5 || groesse > 30) {
            throw new Logic.Ausnahmen.FalscheSpielfeldGroesse(groesse);
        }

        this.groesse = groesse;
        spielfeld = new int[groesse][groesse];
    }

}
