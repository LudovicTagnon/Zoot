package zoot.arbre;

public class SymboleFct extends Symbole {
    private int nbParams;

    public SymboleFct(String type) {
        super(type, 0);
    }

    @Override
    public boolean isParametre() {
        return true;
    }

    //Getters and setters


    public int getNbParams() {
        return nbParams;
    }

    public void setNbParams(int nbParams) {
        this.nbParams = nbParams;
    }
}
