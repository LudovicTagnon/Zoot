package zoot.arbre;

import zoot.arbre.instructions.Instruction;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    
    protected ArrayList<ArbreAbstrait> programme ; //Passe en ArrayList<ArbreAbstrait> pour régler les conflits (Instruction de base)

    public BlocDInstructions(int n) {
        super(n) ;
        programme = new ArrayList<>() ;
    }
    
    public void ajouter(ArbreAbstrait i) {
        programme.add(i) ;
    } //Modification dû a ArrayList

    @Override
    public void verifier() {
        for (ArbreAbstrait i : programme) {   //Modification dû a ArrayList
            i.verifier();
        }
    }
    
    @Override
    public String toMIPS() {
        //Boucle sur les instructions
        StringBuilder sb = new StringBuilder();
        sb.append("#Début du programme\n");
        sb.append(".data\nnewline: .asciiz \"\\n\""); //Créer un saut de ligne
        sb.append("\n\n.text"); //Début du code
        //Todo: Ajouter les variables avec déplacement de $sp
        for (ArbreAbstrait i : programme) {   //Modification dû a ArrayList
            sb.append(i.toMIPS());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return programme.toString() ;
    }

}
