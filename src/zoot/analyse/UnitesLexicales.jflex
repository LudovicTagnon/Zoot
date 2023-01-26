package zoot.analyse ;

import java_cup.runtime.*;
import zoot.exceptions.AnalyseLexicaleException;
      
%%
   
%class AnalyseurLexical
%public

%line
%column
    
%type Symbol
%eofval{
        return symbol(CodesLexicaux.EOF) ;
%eofval}

%cup

%{

  private StringBuilder chaine ;

  private Symbol symbol(int type) {
	return new Symbol(type, yyline, yycolumn) ;
  }

  private Symbol symbol(int type, Object value) {
	return new Symbol(type, yyline, yycolumn, value) ;
  }
%}

csteE = [0-9]+
finDeLigne = \r|\n
espace = {finDeLigne}  | [ \t\f]
variable = [a-zA-Z0-9]+ //Vérifier si la regex correspond à l'enoncé

%%
"//".*                          { /* DO NOTHING */ }

"booleen"                       { return symbol(CodesLexicaux.BOOLEEN); }

"entier"                        { return symbol(CodesLexicaux.ENTIER); }

"variables"                     { return symbol(CodesLexicaux.VARIABLES); }

//faire variable

"debut"                { return symbol(CodesLexicaux.DEBUT); }
"fin"              	   { return symbol(CodesLexicaux.FIN); }

"ecrire"               { return symbol(CodesLexicaux.ECRIRE); }

";"                    { return symbol(CodesLexicaux.POINTVIRGULE); }

{variable}             { return symbol(CodesLexicaux.ID, yytext()); }

{csteE}      	       { return symbol(CodesLexicaux.CSTENTIERE, yytext()); }

{espace}               { }
.                      { throw new AnalyseLexicaleException(yyline, yycolumn, yytext()) ; }

