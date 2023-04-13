package zoot.arbre.expressions;

public class Parenthese extends Expression{

    Expression exp;

    public Parenthese(Expression e, int n) {
        super(n);
        exp = e;
    }

    @Override
    public void verifier() {
        exp.verifier(); //Todo: autre chose ?
    }

    @Override
    public String toMIPS() {
        return exp.toMIPS(); //Todo: autre chose ?
    }

    @Override
    public String getType() {
        return exp.getResultType();
    }

    @Override
    public String getNom() {
        return "(" + exp.getNom() + ")";
    }

    @Override
    public boolean isConstante() {
        return exp.isConstante();
    }

    @Override
    public boolean isFonc() {
        return exp.isFonc();
    }

    @Override
    public boolean isOperator() {
        return true;
    }
}
