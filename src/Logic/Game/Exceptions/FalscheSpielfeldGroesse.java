package Logic.Game.Exceptions;

public class FalscheSpielfeldGroesse extends Exception {
    public final int groesse;

    public FalscheSpielfeldGroesse(int groesse) {
        super();
        this.groesse = groesse;
    }

}
