package zoot.arbre.declarations;


public class Entree {

    private final EntreeVariable variable;
    private final EntreeFonction fonction;

    public Entree(String nom, int nbParam, String type) {
        this.fonction = new EntreeFonction(nom, nbParam, type);
        this.variable = new EntreeVariable(nom);
    }

    public String getNom(){
        return variable.getNom();
    }
    public int getNbParam(){
        return fonction.getNbParam();
    }
    public String getType(){
        return fonction.getType();
    }
    public String toString(){
        //TODO
        return "";
    }
}
