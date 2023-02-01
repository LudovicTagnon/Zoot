package zoot.arbre.declarations;

public class EntreeFonction {
    private final String nomFonction;
    private final int nbParam;
    private final String type;

    public EntreeFonction() {
        this.nomFonction = null;
        this.nbParam = 0;
        this.type = null;
    }

    public EntreeFonction(String nom, int nbParam, String type) {
        this.nomFonction = nom;
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
