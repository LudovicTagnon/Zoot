package zoot.arbre.declarations;


public abstract class Entree {

    protected String nom;   //nom (de la variable ou de la fonction)

    protected int noLigne;

    public Entree(String nom, int ligne) {
        this.nom = nom;
        this.noLigne = ligne;
    }

    public String getNom(){
        return nom;
    }

    public int getNoLigne(){
        return noLigne;
    }
}
