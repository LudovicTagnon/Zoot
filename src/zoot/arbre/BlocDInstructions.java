package zoot.arbre;

import zoot.arbre.declarations.Fonction;
import zoot.arbre.declarations.LFCT;
import zoot.arbre.instructions.Instruction;

import java.util.ArrayList;
import java.util.Iterator;

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

        if (LFCT.getInstance().getDebut()) {
            LFCT.getInstance().setDebut(false); //On met le début à false pour la suite
            Iterator<Fonction> it = LFCT.getInstance().getIterator();
            while (it.hasNext()) { //On vérifie les fonctions
                Fonction f = it.next();
                f.verifier();
            }
            LFCT.getInstance().setDebut(true); //On remet le début à true pour toMips()
        }
    }

    @Override
    public String toMIPS() {
        //Boucle sur les instructions
        StringBuilder sb = new StringBuilder();
        if (LFCT.getInstance().getDebut()) { //Si c'est le début du programme (main)
            LFCT.getInstance().setDebut(false); //ce n'est plus le début
            sb.append("#Début du programme\n");
            sb.append(".data\nnewline: .asciiz \"\\n\""); //Créer un saut de ligne
            sb.append("\nvrai: .asciiz \"vrai\"\nfaux: .asciiz \"faux\"\n"); //Affichage vrai/faux
            sb.append("\n\n.text\nmain:"); //Début du code principal
            sb.append("\nla $s0, 0\n"); //Initialisation de $s0 à faux (pour beq)
            sb.append("\nmove $s7, $sp"); //Sauvegarde de la valeur de $sp
            sb.append("\naddi $sp, $sp, " + TDS.getInstance().getTailleVariable());
            for (ArbreAbstrait i : programme) {   //Modification dû a ArrayList
                sb.append(i.toMIPS());
            }
            sb.append("b fin\n\n"); //On saute à la fin du programme

            Iterator<Fonction> it = LFCT.getInstance().getIterator();
            while (it.hasNext()) {
                Fonction f = it.next();
                sb.append(f.toMIPS());
            }
            sb.append("\nfin:\nli $v0, 10\nsyscall"); //Fin du programme on redonne la main au système
        } else { //Sinon c'est une fonction (pas main)
            for (ArbreAbstrait i : programme) {
                sb.append(i.toMIPS());
            }
        }

        return sb.toString();
    }

    public ArrayList<ArbreAbstrait> getInstructions() {
        return programme;
    }

    @Override
    public String toString() {
        return programme.toString() ;
    }

}
