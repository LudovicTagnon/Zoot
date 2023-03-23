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
        //TODO: Adapter si exp est un appel de fonction
        String mips = "\n#Ecrire "+exp.getNom()+"\n" ;
        int sys = 1; //Syscall pour afficher un entier

        if (!exp.isFonc()){ //Si l'expression n'est pas une fonction
            if (exp.isConstante()) { //Si l'expression est une constante
                if (exp.getType().equals("booleen")) { //constante booleenne
                    sys = 4; //Syscall pour afficher une chaine de caractères
                    mips += "la $a0, ";
                    if (exp.getNom().equals("vrai")) {
                        mips += "vrai\n";
                    } else {
                        mips += "faux\n";
                    }
                } else { //constante entière
                    mips += "li $a0, "+exp.getNom()+"\n";
                }
            } else { //Si l'expression est une variable
                if (exp.getType().equals("booleen")) { //variable booleenne
                    sys = 4; //Syscall pour afficher une chaine de caractères
                    mips += "lw $t0, "+exp.toMIPS()+"\n";
                    mips += "beq $s0, $t0, Sinon"+exp.getNoLigne()+"\n";
                    mips += "la $a0, faux\n";
                    mips += "b FinSi"+exp.getNoLigne()+"\n";
                    mips += "Sinon"+exp.getNoLigne()+":"+"\n";
                    mips += "la $a0, vrai\n";
                    mips += "FinSi"+exp.getNoLigne()+":"+"\n";
                } else { //Variable entière
                    mips += "lw $v0, "+exp.toMIPS()+"\n";
                    mips += "move $a0, $v0\n";
                }
            }
        } else { //Si l'expression est une fonction
            mips += exp.toMIPS();
            mips += "move $a0, $v0\n";
            if (exp.getType().equals("booleen")) { //fonction booleenne
                sys = 4; //Syscall pour afficher une chaine de caractères
                mips += "move $t0, $v0\n";
                mips += "beq $s0, $v0, Sinon"+exp.getNoLigne()+"\n";
                mips += "la $a0, faux\n";
                mips += "b FinSi"+exp.getNoLigne()+"\n";
                mips += "Sinon"+exp.getNoLigne()+":"+"\n";
                mips += "la $a0, vrai\n";
                mips += "FinSi"+exp.getNoLigne()+":"+"\n";
            }
        }

        //Affichage de l'expression
        mips += "li $v0, "+sys+"\n";
        mips += "syscall\n";
        //Saut de ligne
        mips += "#Saut de ligne\nli $v0, 4\n" ;
        mips += "la $a0, newline\n" ;
        mips += "syscall\n" ;

        return mips ;
    }

}
