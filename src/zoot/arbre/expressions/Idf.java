package zoot.arbre.expressions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;
import zoot.arbre.declarations.Entree;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.NonDeclarerException;

public class Idf extends Expression{
    private Entree e;
    private Symbole s;

    public Idf(Entree entree, int n) {
        super(n);
        e = entree; //nom de la variable
    }

    @Override
    public void verifier() {
        try {
            s = TDS.getInstance().identifier(e); //vérification de l'existence
        } catch (NonDeclarerException e) {
            CollectExcept.getInstance().addException(noLigne, e.getMessage());
        }
    }

    @Override
    public String toMIPS() {
        try {
            return s.getDecalage() + "($s7)\n";
        } catch (NullPointerException e) { //Comme on catch une exception plus haut, il est possible que s soit null
            return "0($s7)\n";
        }
    }

    public String getType(){
        return this.s.getType();
    }

    @Override
    public String getNom() {
        return e.getNom();
    }
}
