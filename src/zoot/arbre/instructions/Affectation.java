package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;
import zoot.exceptions.CollectExcept;
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
        try {
            if (!variable.getType().equals(exp.getType())) {
                throw new TypeIncompatibleException(variable, exp); //On lance une exception si les types ne sont pas compatibles
            }
        } catch (TypeIncompatibleException e) {
            CollectExcept.getInstance().addException(noLigne, e.getMessage()); //On ajoute l'exception à la liste des exceptions
        }
    }

    @Override
    public String toMIPS() {
        String mips = "\n#Affectation de "+exp.getNom()+" à "+variable.getNom()+"\n";

        if (!exp.isFonc()) {
            if (exp.isConstante()) { //si exp est une constante
                mips += "li ";
            } else {
                mips += "lw "; //load word si exp est une variable
            }

            mips += "$v0, " + exp.toMIPS(); //store la valeur dans v0
            mips += "sw $v0, ";
            mips += variable.toMIPS();
        } else { //si exp est une fonction
            mips += exp.toMIPS();
            mips += "\nsw $v0, ";
            mips += variable.toMIPS();
        }
        return mips+"\n";
    }
}
