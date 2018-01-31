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
#define EOFnum 0
#define BADNAME 41
#define BADCOMMENT 42
#define BADSTRING 43
#define BADSYMBOL 44

char *ST;
int locs[LIMIT1];
int numberAdded = 0;
%}
COMMENT "/*"((\*+[^/*])|([^*]))*\**"*/"
BADCOMMENT "/*"((\*+[^/*])|([^*]))*\**""
STRING \'(\\.|[^"\\\n()\'])*\'
BADSTRING \'(\\.|[^"\\\n()\'])*(\))*(\;)*
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
BADNAME [0-9][a-zA-Z0-9]*
NEWLINE \n
SPACE " "
TAB \t
BADSYMBOL [^a-zA-Z0-9]
ENDFILE "<<EOF>>"

%%

{ENDFILE}	{
		return EOFnum;
		}

{COMMENT}	{
		/*Ensure that we keep track of comment length appropriately*/
		int i;
		for(i=1;i<strlen(yytext);i++)
			if(yytext[i] == '\n')
				yyline++;
				yycolumn=1;
		}


{NEWLINE}	{
		/*Keeping track of whitespace*/
		yyline++;
		yycolumn=1;
		}

{SPACE}		{
		yycolumn+=1;
		}

{TAB}		{
		yycolumn+=6;
		}

{AND}		{
		/*For all tokens, add their length to the yycolumn*/
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

{STRING}	{
		/*Search through string table to detemrine if an instance of the string already exists
		If so, output the index. Otherwise, place string in string table and output its index*/
		int i;
		int j;
		char temp[strlen(yytext)];
		yycolumn+=(yyleng);
		for(i=1;i<strlen(yytext)-2;i++){
			if(yytext[i] == '\\' && yytext[i+1] == 't'){
				for(j=0;j<i;j++)
					temp[j]=yytext[j];
				temp[i] = 9;
				for(j=i+1;j<strlen(yytext)-1;j++){
					temp[j]=yytext[j+1];
				}
			temp[strlen(yytext)-1] = '\0';
			strcpy(yytext,temp);
			yyleng -=1;
			}
		}

		for(i=1;i<strlen(yytext)-2;i++){
			if(yytext[i] == '\\' && yytext[i+1] == 'n'){
				for(j=0;j<i;j++)
					temp[j]=yytext[j];
				temp[i] = 10;
				for(j=i+1;j<strlen(yytext)-1;j++){
					temp[j]=yytext[j+1];
				}
			temp[strlen(yytext)-1] = '\0';
			strcpy(yytext,temp);
			yyleng -=1;
			}
		}

		for(i=1;i<strlen(yytext)-2;i++){
			if(yytext[i] == '\\' && yytext[i+1] == '\\'){
				for(j=0;j<i;j++)
					temp[j]=yytext[j];
				temp[i] = '\\';
				for(j=i+1;j<strlen(yytext)-1;j++){
					temp[j]=yytext[j+1];
				}
			temp[strlen(yytext)-1] = '\0';
			strcpy(yytext,temp);
			yyleng -=1;
			}
		}

		for(i=1;i<strlen(yytext)-2;i++){
			if(yytext[i] == '\\' && yytext[i+1] == '\''){
				for(j=0;j<i;j++)
					temp[j]=yytext[j];
				temp[i] = '\'';
				for(j=i+1;j<strlen(yytext)-1;j++){
					temp[j]=yytext[j+1];
				}
			temp[strlen(yytext)-1] = '\0';
			strcpy(yytext,temp);
			yyleng -=1;
			}
		}
		
		temp[strlen(yytext)-1];
		strncpy(temp,yytext+1,strlen(yytext)-2);
		temp[strlen(yytext)-2] = '\0';

		for(i=0;i<numberAdded;i++){
			if(strcmp(temp,ST + locs[i]) == 0){
				yyval = locs[i];
				yycolumn+=yyleng;
				return SCONSTnum;
			}
		}
		if(numberAdded > 0){
			strcpy(ST + locs[numberAdded-1] + strlen(ST + locs[numberAdded-1]) + 1,temp);
			locs[numberAdded] = locs[numberAdded-1] + strlen(ST + locs[numberAdded-1]) + 1;
		}
		else{
			strcpy(ST,temp);
			locs[numberAdded] = 0;
		}
		numberAdded++;
		yyval = locs[numberAdded-1];
		return SCONSTnum;
		}

{ID}		{
		/*Same procedure for IDs as strings, except we do not have to check for escape sequences*/
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
		yyval = locs[numberAdded-1];
		yycolumn+=yyleng;
		return IDnum;
		}

{BADNAME}	{
		yyval = yycolumn;
		yycolumn+=yyleng;
		return BADNAME;
		}

{BADCOMMENT}	{
		yyval = yycolumn;
		yycolumn+=yyleng;
		return BADCOMMENT;
		}

{BADSTRING}	{
		yyval = yycolumn;
		yycolumn+=yyleng;
		return BADSTRING;
		}

{BADSYMBOL}	{
		yyval = yycolumn;
		yycolumn+=yyleng;
		return BADSYMBOL;
		}



%%
