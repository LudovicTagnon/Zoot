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
        throw new UnsupportedOperationException("fonction verfier non définie ") ;
    }

    @Override
    public String toMIPS() {
        //throw new UnsupportedOperationException("fonction toMips non définie ") ;

        String mips = "" ;
        // Evaluate the expression
        mips += exp.toMIPS() ;
        // Print the result to the console
        mips += "li $v0, 1\n" ;
        mips += "syscall\n" ;
        mips += "li $v0, 4\n" ;
        mips += "la $a0, newline\n" ;
        mips += "syscall\n" ;
        return mips ;
    }

}
