package zoot.arbre.declarations;

public class EntreeFonction extends Entree{
    private final int nbParam;

    public EntreeFonction(String nom, int nbParam) {
        super(nom);
        this.nbParam = nbParam;
    }

    public int getNbParam() {
        return this.nbParam;
    }

}
