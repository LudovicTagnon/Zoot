package zoot.arbre.instructions;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.TDS;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Si extends Instruction{

    private Expression condition;
    private ArbreAbstrait instruction;
    private ArbreAbstrait sinon;
    private final String label = "condi" + TDS.getInstance().getCpt();
    public Si(Expression e, ArbreAbstrait a, ArbreAbstrait b, int n) {
        super(n);
        condition = e;
        if (a != null) {
            instruction = a;
        } else {
            instruction = new BlocDInstructions(0); //vide
        }
        if (b != null) {
            sinon = b;
        } else {
            sinon = new BlocDInstructions(0); //vide
        }
    }

    @Override
    public void verifier() {
        condition.verifier();
        try {
            if (!condition.getResultType().equals("booleen")) {
                throw new TypeIncompatibleException(condition, noLigne);
            } else {
                instruction.verifier();
                sinon.verifier();
            }
        } catch (TypeIncompatibleException e) {
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String mips = "#if "+condition.getNom()+"\n";
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
        mips += "#else = not("+condition.getNom()+")\n";
        mips += label+":\n"; //si vrai, donc on ne rentre pas dans si
        mips += sinon.toMIPS();
        mips += "end_"+label+":\n";
        return mips;
    }

    @Override
    public boolean isReturn() {
        boolean returnSi = false;
        boolean returnSinon = false;
        boolean result = false;

        BlocDInstructions inst = (BlocDInstructions) instruction;
        for (ArbreAbstrait i : inst.getInstructions()){
            if (i.isReturn()){
                returnSi = true;
            }
        }

        BlocDInstructions inst2 = (BlocDInstructions) sinon;
        for (ArbreAbstrait i2 : inst2.getInstructions()){
            if (i2.isReturn()){
                returnSinon = true;
            }
        }

        if (inst.getInstructions().size() != 0){
            result = returnSi;
        }

        if (inst2.getInstructions().size() != 0){
            result = returnSinon;
        }

        if (inst.getInstructions().size() != 0 && inst2.getInstructions().size() != 0 ){
            result = returnSi && returnSinon;
        }

        return result;
    }
}
