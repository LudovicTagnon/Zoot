package zoot;

import zoot.analyse.AnalyseurLexical;
import zoot.analyse.AnalyseurSyntaxique;
import zoot.arbre.ArbreAbstrait;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.CollectExcept;

import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Zoot {

    public Zoot(String nomFichier) {
        try {
            AnalyseurSyntaxique analyseur = new AnalyseurSyntaxique(new AnalyseurLexical(new FileReader(nomFichier)));
            ArbreAbstrait arbre = (ArbreAbstrait) analyseur.parse().value;

            arbre.verifier() ;
            Iterator<AnalyseException> it = CollectExcept.getInstance().getIterator();
            if (!it.hasNext()) { // Si il n'y a pas d'erreur
                System.out.println("COMPILATION OK");

                String nomSortie = nomFichier.replaceAll("[.]zoot", ".mips");
                PrintWriter flot = new PrintWriter(new BufferedWriter(new FileWriter(nomSortie)));
                flot.println(arbre.toMIPS());
                flot.close();
            } else { // Si il y a des erreurs
                System.err.println("Erreur sémantique :\n");
                while (it.hasNext()) {
                    AnalyseException e = it.next();
                    System.err.println(e.getMessage());
                }

            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Fichier " + nomFichier + " inexistant") ;
        }
        catch (AnalyseException ex) {
            System.err.println(ex.getMessage());
        }
        catch (Exception ex) {
            Logger.getLogger(Zoot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Nombre incorrect d'arguments") ;
            System.err.println("\tjava -jar zoot.jar <fichierSource.zoot>") ;
            System.exit(1) ;
        }
        new Zoot(args[0]) ;
    }
    
}
