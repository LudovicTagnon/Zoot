package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;

public class Ecrire extends Instruction {

    protected Expression exp ;

    public Ecrire (Expression e, int n) {
        super(n) ;
        exp = e ;
    }

    @Override
    public void verifier() {
        exp.verifier();
    }

    @Override
    public String toMIPS() {
        //throw new UnsupportedOperationException("fonction toMips non définie ") ;

        String mips = "\n#Valeur dans le $v0\n" ;
        if (exp.isConstante()){
            mips += "li";
        } else {
            mips += "lw";
        }

        mips += " $v0, " + exp.toMIPS() ;
        // Print the result to the console
        mips += "#Déplace la valeur à afficher dans $a0\nmove $a0, $v0\n" ;
        mips += "#Numéro du read\nli $v0, 1\n" ;
        mips += "#Appel système\nsyscall\n" ;
        mips += "#Saut de ligne\nli $v0, 4\n" ;
        mips += "la $a0, newline\n" ;
        mips += "syscall\n" ;
        return mips ;
    }

}
