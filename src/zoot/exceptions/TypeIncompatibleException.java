package zoot.exceptions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;

public class TypeIncompatibleException extends AnalyseException{
    public TypeIncompatibleException(Idf idf, Expression exp, int ligne) {
        super("Erreur ligne "+ligne+": Affectation non authorisée: " + idf.getNom() + " = " + exp.getNom() + " | Variables de type différent");
    }
}
