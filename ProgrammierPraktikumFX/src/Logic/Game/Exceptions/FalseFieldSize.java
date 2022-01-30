package Logic.Game.Exceptions;

public class FalseFieldSize extends Exception {
    public final int groesse;

    public FalseFieldSize(int groesse) {
        super();
        this.groesse = groesse;
    }

}
