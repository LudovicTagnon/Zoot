package zoot.exceptions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;

public class TypeIncompatibleException extends AnalyseException{
    public TypeIncompatibleException(Idf idf, Expression exp, int ligne) {
        super("Erreur ligne "+ligne+": Affectation non authorisée: " + idf.getNom() + " = " + exp.getNom() + " | Variables de type différent");
    }

    public TypeIncompatibleException(Expression eg, Expression ed, int ligne) {
        super("Erreur ligne "+ligne+": " + eg.getNom() + " + " + ed.getNom() + " | Types incompatibles pour cette opéaration");
    }

    public TypeIncompatibleException(Expression e, int ligne) {
        super("Erreur ligne "+ligne+": " + e.getNom() + " | Types incompatibles pour cette opéaration");
    }
}
