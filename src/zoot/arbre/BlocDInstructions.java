package zoot.arbre;

import zoot.arbre.declarations.Fonction;
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
        if (TDS.getInstance().getDebut()) { //Si c'est le début du programme
            TDS.getInstance().setDebut(false); //ce n'est plus le début
            sb.append("#Début du programme\n");
            sb.append(".data\nnewline: .asciiz \"\\n\""); //Créer un saut de ligne
            sb.append("\n\n.text\nmain:"); //Début du code principal
            sb.append("\nmove $s7, $sp"); //Sauvegarde de la valeur de $sp
            sb.append("\naddi $sp, $sp, " + TDS.getInstance().getTailleVariable());
            for (ArbreAbstrait i : programme) {   //Modification dû a ArrayList
                sb.append(i.toMIPS());
            }

            ArrayList<Fonction> fonctions = TDS.getInstance().getFonctions();
            for (Fonction f : fonctions) {
                sb.append(f.toMIPS());
            }
        } else {
            for (ArbreAbstrait i : programme) {
                sb.append(i.toMIPS());
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return programme.toString() ;
    }

}
