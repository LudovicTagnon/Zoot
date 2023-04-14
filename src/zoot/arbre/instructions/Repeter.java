package zoot.arbre.instructions;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.TDS;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Repeter extends Instruction{
    private Expression repetition;
    private ArbreAbstrait instruction;
    private final String label = "rep" + TDS.getInstance().getCpt();
    public Repeter(Expression e, ArbreAbstrait a, int n) {
        super(n);
        repetition = e;
        instruction = a;
    }

    @Override
    public void verifier() {
        repetition.verifier();
        try {
            if (!repetition.getResultType().equals("booleen")) {
                throw new TypeIncompatibleException(repetition, noLigne);
            } else {
                instruction.verifier();
            }
        } catch (TypeIncompatibleException e) {
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String mips = "#Repeter "+repetition.getNom()+"\n";
        mips += label+":\n";
        mips += instruction.toMIPS()+"\n";
        if (!repetition.isFonc()) {
            if (repetition.isConstante()) { //si exp est une constante
                mips += "li "; //load
            } else {
                mips += "lw "; //load word si exp est une variable
            }
            mips += "$v0, " + repetition.toMIPS(); //store la valeur dans v0
        } else { //si exp est une fonction
            mips += repetition.toMIPS();
        }
        mips += "beq $v0,$s0,"+label+"\n";
        return mips;
    }
}