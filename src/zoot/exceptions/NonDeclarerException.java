package zoot.exceptions;

import zoot.arbre.declarations.Entree;

public class NonDeclarerException extends AnalyseException{

    public  NonDeclarerException(Entree e) {
        super("Erreur ligne "+e.getNoLigne()+": Variable "+e.getNom()+" non déclarée !");
    }
}
