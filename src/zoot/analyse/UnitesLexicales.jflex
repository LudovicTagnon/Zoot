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

csteE = [0-9]+ //TODO:voir pour gerer les moins avec -?
csteB = vrai|faux
finDeLigne = \r|\n
espace = {finDeLigne}  | [ \t\f]
variable = [a-zA-Z][a-zA-z0-9]* //Vérifier si la regex correspond à l'enoncé

%%
"//".*                          { /* DO NOTHING */ }

"booleen"                       { return symbol(CodesLexicaux.BOOLEEN); }

"entier"                        { return symbol(CodesLexicaux.ENTIER); }

"variables"                     { return symbol(CodesLexicaux.VARIABLES); }

"fonctions"                     { return symbol(CodesLexicaux.FONCTIONS); }

"retourne"                        { return symbol(CodesLexicaux.RETOURNE); }

"("            { return symbol(CodesLexicaux.PARENTHESEOUVRANTE); }

")"            { return symbol(CodesLexicaux.PARENTHESEFERMANTE); }

"debut"                { return symbol(CodesLexicaux.DEBUT); }
"fin"              	   { return symbol(CodesLexicaux.FIN); }

"ecrire"               { return symbol(CodesLexicaux.ECRIRE); }

"="                    { return symbol(CodesLexicaux.EGALE); }

";"                    { return symbol(CodesLexicaux.POINTVIRGULE); }

","                    { return symbol(CodesLexicaux.VIRGULE); }

"si"                    { return symbol(CodesLexicaux.SI); }

"alors"                 { return symbol(CodesLexicaux.ALORS); }

"finsi"                 { return symbol(CodesLexicaux.FINSI); }

"repeter"               { return symbol(CodesLexicaux.REPETER); }

"jusqua"               { return symbol(CodesLexicaux.JUSQUA); }

"finrepeter"            { return symbol(CodesLexicaux.FINREPETER); }

{csteE}      	       { return symbol(CodesLexicaux.CSTENTIERE, yytext()); }

{csteB}                { return symbol(CodesLexicaux.CSTBOOLEENNE, yytext()); }

{variable}             { return symbol(CodesLexicaux.ID, yytext()); }

{espace}               { }
.                      { throw new AnalyseLexicaleException(yyline, yycolumn, yytext()) ; }

