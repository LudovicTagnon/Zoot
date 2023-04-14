package zoot.arbre.instructions;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.TDS;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Si extends Instruction{

    private Expression condition;
    private ArbreAbstrait instruction;
    private final String label = "condi" + TDS.getInstance().getCpt();
    public Si(Expression e, ArbreAbstrait a, int n) {
        super(n);
        condition = e;
        instruction = a;
    }

    @Override
    public void verifier() {
        condition.verifier();
        try {
            if (!condition.getResultType().equals("booleen")) {
                throw new TypeIncompatibleException(condition, noLigne);
            } else {
                instruction.verifier();
            }
        } catch (TypeIncompatibleException e) {
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String mips = "#if "+condition.toMIPS();
        if (!condition.isFonc()) {
            if (condition.isConstante()) { //si exp est une constante
                mips += "li "; //load
            } else {
                mips += "lw "; //load word si exp est une variable
            }
            mips += "$v0, " + condition.toMIPS(); //store la valeur dans v0
        } else { //si exp est une fonction
            mips += condition.toMIPS();
        }
        mips += "beq $s0,$v0,"+label+"\n"; //si $v0 == faux
        mips += instruction.toMIPS(); //code si $v0 vrai, donc on rentre dans le si
        mips += "j end_"+label+"\n";
        mips += label+":\n"; //si vrai, donc on ne rentre pas dans si
        mips += "end_"+label+":\n";
        return mips;
    }
}
