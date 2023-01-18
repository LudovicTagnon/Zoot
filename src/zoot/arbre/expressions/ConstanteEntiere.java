package zoot.arbre.expressions;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS() {
        //throw new UnsupportedOperationException("fonction toMips non définie ") ;
        return "li $v0, " + cste + "\n";
    }

}
