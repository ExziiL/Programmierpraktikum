package Logic.Spielfeld;

import Logic.Spielfeld.Ausnahmen.*;
import static Logic.main.LogicKonstanten.*;
import Logic.main.*;


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

    private Spieler erstelleSpieler( Spielertyp t )throws FalscherSpielertyp{
        Spieler neuerSpieler;

        if(t == t.OFFLINE){
            neuerSpieler = new OfflineSpieler();

        }else if( t== t.ONLINE) {

            neuerSpieler = new OnlineSpieler();
        }else{
            throw new FalscherSpielertyp();
        }
        return neuerSpieler;
    }
}
