%{
#include <stdio.h>

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


%%

{COMMENT}	{
		printf("Comment\n");
		return 0;
		}

{AND}		{
		return ANDnum;
		}

{ASSGN}		{
		return ASSGNnum;
		}

{DECLARATION}	{
		return DECLARATIONnum;
		}

{DOT}		{
		return DOTnum;
		}

{ENDDECLARATIONS}	{
			return ENDDECLARATIONSnum;
			}

{EQUAL}		{
		return EQUALnum;
		}

{GT}		{
		return GTnum;
		}

{INT}		{
		return INTnum;
		}

{LBRAC}		{
		return LBRACnum;
		}

{LPAREN}	{
		return LPARENnum;
		}

{METHOD}	{
		return METHODnum;
		}

{NE}		{
		return NEnum;
		}

{OR}		{
		return ORnum;
		}

{PROGRAM}	{
		return PROGRAMnum;
		}

{RBRAC}		{
		return RBRACnum;
		}

{RPAREN}	{
		return RPARENnum;
		}

{SEMI}		{
		return SEMInum;
		}

{VAL}		{
		return VALnum;
		}

{WHILE}		{
		return WHILEnum;
		}

{CLASS}		{
		return CLASSnum;
		}

{COMMA}		{
		return COMMAnum;
		}

{DIVIDE}	{
		return DIVIDEnum;
		}

{ELSE}		{
		return ELSEnum;
		}

{EQ}		{
		return EQnum;
		}

{GE}		{
		return GEnum;
		}

{ICONST}	{
		return ICONSTnum;
		}

{IF}		{
		return IFnum;
		}

{LBRACE}	{
		return LBRACEnum;
		}

{LE}		{
		return LEnum;
		}

{LT}		{
		return LTnum;
		}

{MINUS}		{
		return MINUSnum;
		}

{NOT}		{
		return NOTnum;
		}

{PLUS}		{
		return PLUSnum;
		}

{RBRACE}	{
		return WHILEnum;
		}

{RETURN}	{
		return RETURNnum;
		}

{TIMES}		{
		return TIMESnum;
		}

{VOID}		{
		return VOIDnum;
		}

<<EOF>>		{
		return EOFnum;
		}

{ID}		{
		return IDnum;
		}

%%

int main(int argc, char** argv){
    if ( argc > 0 )
            yyin = (FILE*)(fopen( argv[1], "r" ));
    else
            yyin = stdin;

	while(1){
		int temp = yylex();
		if(temp == EOFnum)
			break;
		else
			printf("Result is %d\n",temp);
	}
	return 0;
}
