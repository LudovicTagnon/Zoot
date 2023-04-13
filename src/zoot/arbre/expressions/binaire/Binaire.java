package zoot.arbre.expressions.binaire;

import zoot.arbre.expressions.Expression;

public abstract class Binaire extends Expression {

    protected Expression expGauche;
    protected Expression expDroite;

    protected Binaire(Expression eg, Expression ed, int n) {
        super(n);
        this.expGauche = eg;
        this.expDroite = ed;
    }

    @Override
    public abstract void verifier() ;

    @Override
    public abstract String toMIPS();

    @Override
    public abstract String getType();
}
