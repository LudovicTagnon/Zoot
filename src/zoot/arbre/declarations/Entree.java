package zoot.arbre.declarations;


public abstract class Entree {

    protected String nom;   //nom (de la variable ou de la fonction)

    public Entree(String nom) {
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }
}
