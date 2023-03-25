package zoot.arbre;

public class Symbole {
    private int decalage ;
    private int numBloc ;
    private String type ;

    public Symbole(String t, int b) {
        type = t ;
        numBloc = b ;
    }

    public boolean isParametre() {
        return false ;
    }

    // Getters and setters


    public int getDecalage() {
        return decalage;
    }

    public void setDecalage(int decalage) {
        this.decalage = decalage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumBloc() {
        return numBloc;
    }

    public int getNbParams() {
        return 0;
    }
}
