package zoot.exceptions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;

public class TypeIncompatibleException extends AnalyseException{
    public TypeIncompatibleException(Idf idf, Expression exp) {
        super("Affectation non authorisée: " + idf + " = " + exp + ". Variables de type différent");
    }
}
