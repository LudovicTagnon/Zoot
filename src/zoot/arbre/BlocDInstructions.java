package zoot.arbre;

import zoot.arbre.instructions.Instruction;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    
    protected ArrayList<Instruction> programme ;

    public BlocDInstructions(int n) {
        super(n) ;
        programme = new ArrayList<>() ;
    }
    
    public void ajouter(Instruction i) {
        programme.add(i) ;
    }

    @Override
    public void verifier() {
        throw new UnsupportedOperationException("fonction verifier non définie ") ;
    }
    
    @Override
    public String toMIPS() {
        //Boucle sur les instructions
        StringBuilder sb = new StringBuilder();
        sb.append("#Début du programme\n");
        sb.append(".data\nnewline: .asciiz \"\\n\""); //Créer un saut de ligne
        sb.append("\n\n.text"); //Début du code
        for (Instruction i : programme) {
            sb.append(i.toMIPS());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return programme.toString() ;
    }

}