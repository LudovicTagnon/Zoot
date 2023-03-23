package zoot.arbre.expressions;

public abstract class Constante extends Expression {

    protected String cste ;
    
    protected Constante(String texte, int n) {
        super(n) ;
        cste = texte ;
    }
    
    @Override
    public void verifier() {
        //throw new UnsupportedOperationException("fonction verfier non définie ") ;
    }

    @Override
    public String getNom() {
        return cste;
    }

    @Override
    public String toString() {
        return cste ;
    }

}
