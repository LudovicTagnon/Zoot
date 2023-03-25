package zoot.arbre.expressions;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS() {
        return cste + "\n";
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public boolean isConstante() {
        return true;
    }


}
