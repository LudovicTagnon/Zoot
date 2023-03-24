package zoot.exceptions;

import zoot.arbre.declarations.Entree;

public class DoubleDeclarationException extends AnalyseException{
    public DoubleDeclarationException(Entree e) {
        super("Erreur ligne "+e.getNoLigne()+": Variable "+e.getNom()+" déclarée plusieurs fois !");
    }
}
