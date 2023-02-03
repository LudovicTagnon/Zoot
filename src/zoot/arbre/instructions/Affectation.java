package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;
import zoot.exceptions.TypeIncompatibleException;

public class Affectation extends Instruction{

    private final Idf variable;
    private final Expression exp;

    public Affectation(int n, Idf idf, Expression e) {
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
        StringBuilder sb = new StringBuilder("#Affectation d'un variable\n");
        sb.append(exp.toMIPS()); //store la valeur dans v0
        sb.append("sw $v0, ");
        sb.append(variable.toMIPS());
        return sb.toString();
    }
}
