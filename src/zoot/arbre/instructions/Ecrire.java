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
        String mips = "\n#Ecrire " + exp.getNom() + "\n";
        int sys = 1; //Syscall pour afficher un entier

        if (!exp.isComparaison()) { //Si l'expression n'est pas une comparaison
            if (!exp.isFonc()) { //Si l'expression n'est pas une fonction
                if (exp.isConstante()) { //Si l'expression est une constante
                    if (exp.getType().equals("booleen")) { //constante booleenne
                        sys = 4; //Syscall pour afficher une chaine de caractères
                        mips += "la $t0, ";
                        mips += exp.toMIPS() + "\n";
                        mips += "beq $s0, $t0, Sinon" + exp.getNoLigne() + "\n"; //$s0 = faux
                        mips += "la $a0, vrai\n";
                        mips += "b FinSi" + exp.getNoLigne() + "\n";
                        mips += "Sinon" + exp.getNoLigne() + ":" + "\n";
                        mips += "la $a0, faux\n";
                        mips += "FinSi" + exp.getNoLigne() + ":" + "\n";
                    } else { //constante entière
                        mips += "li $a0, " + exp.toMIPS() + "\n";
                    }
                } else { //Si l'expression est une variable
                    if (exp.getType().equals("booleen")) { //variable booleenne
                        sys = 4; //Syscall pour afficher une chaine de caractères
                        mips += "lw $t0, " + exp.toMIPS() + "\n";
                        mips += "beq $s0, $t0, Sinon" + exp.getNoLigne() + "\n"; //$s0 = faux
                        mips += "la $a0, vrai\n";
                        mips += "b FinSi" + exp.getNoLigne() + "\n";
                        mips += "Sinon" + exp.getNoLigne() + ":" + "\n";
                        mips += "la $a0, faux\n";
                        mips += "FinSi" + exp.getNoLigne() + ":" + "\n";
                    } else { //Variable entière
                        mips += "lw $v0, " + exp.toMIPS() + "\n";
                        mips += "move $a0, $v0\n";
                    }
                }
            } else { //Si l'expression est une fonction
                mips += exp.toMIPS();
                mips += "move $a0, $v0\n";
                if (exp.getType().equals("booleen")) { //fonction booleenne
                    sys = 4; //Syscall pour afficher une chaine de caractères
                    mips += "move $t0, $v0\n";
                    mips += "beq $s0, $v0, Sinon" + exp.getNoLigne() + "\n";
                    mips += "la $a0, vrai\n";
                    mips += "b FinSi" + exp.getNoLigne() + "\n";
                    mips += "Sinon" + exp.getNoLigne() + ":" + "\n";
                    mips += "la $a0, faux\n";
                    mips += "FinSi" + exp.getNoLigne() + ":" + "\n";
                }
            }
        } else{ //expression est une comparaison
            sys = 4; //Syscall pour afficher une chaine de caractères
            if (!exp.isFonc()) {
                if (exp.isConstante()) {
                    if (exp.getType().equals("booleen")) { //comparaison booleene
                        mips += "la $v0, " + exp.toMIPS() + "\n";
                    } else { //comparaison entière
                        mips += "li $v0, " + exp.toMIPS();
                    }
                } else {
                    if (exp.getType().equals("booleen")) { //comparaison booleene
                    }
                    mips += "lw $v0, " + exp.toMIPS() + "\n";
                }
            } else { //fonction
                mips += exp.toMIPS();
            }
            mips += "move $a0, $v0\n";
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
