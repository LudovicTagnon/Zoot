package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;
import zoot.exceptions.TypeIncompatibleException;

public class Affectation extends Instruction{

    private final Idf variable;
    private final Expression exp;

    protected Affectation(int n, Idf idf, Expression e) {
        super(n);
        variable = idf;
        exp = e;
    }

    @Override
    public void verifier() {
        variable.verifier();
        exp.verifier();
        if(!variable.getType().equals(exp.getType())){
            //TODO
            throw new TypeIncompatibleException(variable, exp);
        }
    }

    @Override
    public String toMIPS() {
        //TODO
        return null;
    }
}
