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
            throw new TypeIncompatibleException(variable, exp);
        }
    }

    @Override
    public String toMIPS() {
        String mips = "\n#Affectation d'une variable\n";

        if (exp.isConstante()){
            mips += "li ";
        } else {
            mips += "lw "; //load word si exp est une variable
        }

        mips += "$v0, "+exp.toMIPS(); //store la valeur dans v0
        mips += "sw $v0, ";
        mips += variable.toMIPS();
        return mips;
    }
}
