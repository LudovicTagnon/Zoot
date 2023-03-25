package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;
import zoot.exceptions.AnalyseException;
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
                throw new TypeIncompatibleException(variable, exp, noLigne); //On lance une exception si les types ne sont pas compatibles
            }
        } catch (AnalyseException e) {
            CollectExcept.getInstance().addException(e); //On ajoute l'exception à la liste des exceptions
        }
    }

    @Override
    public String toMIPS() {
        String mips = "\n#Affectation de "+exp.getNom()+" à "+variable.getNom()+"\n";

        if (!exp.isFonc()) {
            if (exp.isConstante()) { //si exp est une constante
                if (exp.getType().equals("booleen")) { //si exp est un booléen
                    mips += "la "; //load
                } else { //si exp est un entier
                    mips += "li "; //load
                }

            } else {
                mips += "lw "; //load word si exp est une variable
            }

            mips += "$v0, " + exp.toMIPS(); //store la valeur dans v0
            mips += "\nsw $v0, ";
            mips += variable.toMIPS();
        } else { //si exp est une fonction
            mips += exp.toMIPS();
            mips += "\nsw $v0, ";
            mips += variable.toMIPS();
        }
        return mips+"\n";
    }
}
