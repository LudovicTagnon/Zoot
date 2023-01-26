package zoot.arbre.expressions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;

public class Idf extends Expression{
    private String nom;

    private Symbole s;

    public Idf(String nom, int n) {
        super(n);
        this.nom = nom; //nom de la variable
    }

    @Override
    public void verifier() {
        s = TDS.getInstance().existe(nom); //vérification de l'existence
    }

    @Override
    public String toMIPS() {
        return null;
    }
}
