package zoot.arbre.declarations;

public class EntreeFonction extends Entree{
    private final int nbParam;
    private final String type;

    public EntreeFonction() {
        super(""); //nom = "
        this.nbParam = 0;
        this.type = null;
    }

    public EntreeFonction(String nom, int nbParam, String type) {
        super(nom);
        this.nbParam = nbParam;
        this.type = type;
    }

    public int getNbParam() {
        return this.nbParam;
    }

    public String getType() {
        return this.type;
    }
}
