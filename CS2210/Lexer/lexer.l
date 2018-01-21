%{
#include <stdio.h>
int yyline = 1, yycolumn = 1, yyval = 0;

#define LIMIT1 500
#define LIMIT2 4096

#define ANDnum 1
#define ASSGNnum 2
#define DECLARATIONnum 3
#define DOTnum 4
#define ENDDECLARATIONSnum 5
#define EQUALnum 6
#define GTnum 7
#define IDnum 8
#define INTnum 9
#define LBRACnum 10
#define LPARENnum 11
#define METHODnum 12
#define NEnum 13
#define ORnum 14
#define PROGRAMnum 15
#define RBRACnum 16
#define RPARENnum 17
#define SEMInum 18
#define VALnum 19
#define WHILEnum 20
#define CLASSnum 21
#define COMMAnum 22
#define DIVIDEnum 23
#define ELSEnum 24
#define EQnum 25
#define GEnum 26
#define ICONSTnum 27
#define IFnum 28
#define LBRACEnum 29
#define LEnum 30
#define LTnum 31
#define MINUSnum 32
#define NOTnum 33
#define PLUSnum 34
#define RBRACEnum 35
#define RETURNnum 36
#define SCONSTnum 37
#define TIMESnum 38
#define VOIDnum 39
#define EOFnum 40

char *ST;
int locs[LIMIT1];
int numberAdded = 0;
%}



COMMENT "/*"((\*+[^/*])|([^*]))*\**"*/"
AND "&&"
ASSGN ":="
DECLARATION "declarations"
DOT "."
ENDDECLARATIONS "enddeclarations"
EQUAL "="
GT ">"
INT "int"
LBRAC "["
LPAREN "("
METHOD "method"
NE "!="
OR "||"
PROGRAM "program"
RBRAC "]"
RPAREN ")"
SEMI ";"
VAL "val"
WHILE "while"
CLASS "class"
COMMA ","
DIVIDE "/"
ELSE "else"
EQ "=="
GE ">="
ICONST [0-9]+
IF "if"
LBRACE "{"
LE "<="
LT "<"
MINUS "-"
NOT "!"
PLUS "+"
RBRACE "}"
RETURN "return"
TIMES "*"
VOID "void"
ID [a-zA-Z][a-zA-Z0-9]*
NEWLINE \n
SPACE " "
TAB \t


%%

{COMMENT}	{
		return 0;
		}

{NEWLINE}	{
		yyline++;
		yycolumn=0;
		}

{SPACE}		{
		yycolumn+=1;
		}

{TAB}		{
		yycolumn+=6;
		}

{AND}		{
		yycolumn+=yyleng;
		return ANDnum;
		}

{ASSGN}		{
		yycolumn+=yyleng;
		return ASSGNnum;
		}

{DECLARATION}	{
		yycolumn+=yyleng;
		return DECLARATIONnum;
		}

{DOT}		{
		yycolumn+=yyleng;
		return DOTnum;
		}

{ENDDECLARATIONS}	{
			yycolumn+=yyleng;
			return ENDDECLARATIONSnum;
			}

{EQUAL}		{
		yycolumn+=yyleng;
		return EQUALnum;
		}

{GT}		{
		yycolumn+=yyleng;
		return GTnum;
		}

{INT}		{
		yycolumn+=yyleng;
		return INTnum;
		}

{LBRAC}		{
		yycolumn+=yyleng;
		return LBRACnum;
		}

{LPAREN}	{
		yycolumn+=yyleng;
		return LPARENnum;
		}

{METHOD}	{
		yycolumn+=yyleng;
		return METHODnum;
		}

{NE}		{
		yycolumn+=yyleng;
		return NEnum;
		}

{OR}		{
		yycolumn+=yyleng;
		return ORnum;
		}

{PROGRAM}	{
		yycolumn+=yyleng;
		return PROGRAMnum;
		}

{RBRAC}		{
		yycolumn+=yyleng;
		return RBRACnum;
		}

{RPAREN}	{
		yycolumn+=yyleng;
		return RPARENnum;
		}

{SEMI}		{
		yycolumn+=yyleng;
		return SEMInum;
		}

{VAL}		{
		yycolumn+=yyleng;
		return VALnum;
		}

{WHILE}		{
		yycolumn+=yyleng;
		return WHILEnum;
		}

{CLASS}		{
		yycolumn+=yyleng;
		return CLASSnum;
		}

{COMMA}		{
		yycolumn+=yyleng;
		return COMMAnum;
		}

{DIVIDE}	{
		yycolumn+=yyleng;
		return DIVIDEnum;
		}

{ELSE}		{
		yycolumn+=yyleng;
		return ELSEnum;
		}

{EQ}		{
		yycolumn+=yyleng;
		return EQnum;
		}

{GE}		{
		yycolumn+=yyleng;
		return GEnum;
		}

{ICONST}	{
		yycolumn+=yyleng;
		yyval = atoi(yytext);
		return ICONSTnum;
		}

{IF}		{
		yycolumn+=yyleng;
		return IFnum;
		}

{LBRACE}	{
		yycolumn+=yyleng;
		return LBRACEnum;
		}

{LE}		{
		yycolumn+=yyleng;
		return LEnum;
		}

{LT}		{
		yycolumn+=yyleng;
		return LTnum;
		}

{MINUS}		{
		yycolumn+=yyleng;
		return MINUSnum;
		}

{NOT}		{
		yycolumn+=yyleng;
		return NOTnum;
		}

{PLUS}		{
		yycolumn+=yyleng;
		return PLUSnum;
		}

{RBRACE}	{
		yycolumn+=yyleng;
		return RBRACEnum;
		}

{RETURN}	{
		yycolumn+=yyleng;
		return RETURNnum;
		}

{TIMES}		{
		yycolumn+=yyleng;
		return TIMESnum;
		}

{VOID}		{
		yycolumn+=yyleng;
		return VOIDnum;
		}

<<EOF>>		{
		yycolumn+=yyleng;
		return EOFnum;
		}

{ID}		{
		int i;
		for(i=0;i<numberAdded;i++){
			if(strcmp(yytext,ST + locs[i]) == 0){
				yyval = locs[i];
				yycolumn+=yyleng;
				return IDnum;
			}
		}
		if(numberAdded > 0){
			strcpy(ST + locs[numberAdded-1] + strlen(ST + locs[numberAdded-1]) + 1,yytext);
			locs[numberAdded] = locs[numberAdded-1] + strlen(ST + locs[numberAdded-1]) + 1;
		}
		else{
			strcpy(ST,yytext);
			locs[numberAdded] = 0;
		}
		numberAdded++;
		yycolumn+=yyleng;
		yyval = locs[numberAdded-1];
		return IDnum;
		}

%%

int main(int argc, char** argv){
	if ( argc > 0 )
	    yyin = (FILE*)(fopen( argv[1], "r" ));
	else
	    yyin = stdin;
	int i;
	for(i=0;i<LIMIT1;i++)
		locs[i] = -1;
	
	ST = malloc(sizeof(char)*LIMIT2);

	while(1){
		int temp = yylex();
		if(temp == EOFnum){
			printf("EOF reached");
			break;
		}
		else{
			switch(temp){
				case 0:
				printf("Comment. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case ANDnum:
				printf("And. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case ASSGNnum:
				printf("Assignment. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case DECLARATIONnum:
				printf("Declaration. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case DOTnum:
				printf("Dot. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case ENDDECLARATIONSnum:
				printf("End Declaration. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case EQUALnum:
				printf("Equal. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case GTnum:
				printf("Greater than. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case IDnum:
				printf("Identifier. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				printf("Located at %d, Its %s\n",yyval,ST + yyval);
				break;

				case INTnum:
				printf("Int. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case LBRACnum:
				printf("L Bracket. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case LPARENnum:
				printf("L Parenthesis. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case METHODnum:
				printf("Method. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case NEnum:
				printf("Not equal. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case ORnum:
				printf("Or. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case PROGRAMnum:
				printf("Program. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case RBRACnum:
				printf("R bracket. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case RPARENnum:
				printf("R parenthesis. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case SEMInum:
				printf("Semicolon. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case VALnum:
				printf("Val. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case WHILEnum:
				printf("While. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case CLASSnum:
				printf("Class. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case COMMAnum:
				printf("Comma. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case DIVIDEnum:
				printf("Divide. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case ELSEnum:
				printf("Else. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case EQnum:
				printf("Equal to. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case GEnum:
				printf("Greater or equal. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case ICONSTnum:
				printf("Integer constant. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				printf("Its value is %d\n",yyval);
				break;

				case IFnum:
				printf("If. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case LBRACEnum:
				printf("Left brace. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case LEnum:
				printf("Less or equal. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case LTnum:
				printf("Less than. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case MINUSnum:
				printf("Minus. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case NOTnum:
				printf("Not. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case PLUSnum:
				printf("Plus. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case RBRACEnum:
				printf("Right Brace. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case RETURNnum:
				printf("Return. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case TIMESnum:
				printf("Times. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;

				case VOIDnum:
				printf("Void. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
				break;
			}
		}
	}
	return 0;
}
