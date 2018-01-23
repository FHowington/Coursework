#include <stdio.h>
#include <stdlib.h>
#include "lex.yy.c"

#define ANDnum 1
#define LIMIT1 500
#define LIMIT2 4096
#define EOFnum 40

int main(int argc, char** argv){
	if ( argc > 0 )
	    yyin = (FILE*)(fopen( argv[1], "r" ));
	else
	    yyin = stdin;
	int i;
	for(i=0;i<LIMIT1;i++)
		locs[i] = -1;
	
	ST = malloc(sizeof(char)*LIMIT2);
    int error=0;

	while(1){
        if(error==1)
            break;
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

				case SCONSTnum:
				printf("String. \tLine: %d, Column: %d\n",yyline,(int)yycolumn);
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
                
                case BADNAME:
                printf("ERROR: INVALID IDENTIFIER: %s Line: %d, Column: %d\n",yytext,yyline,(int)yycolumn);
                error=1;
                break;

                case BADCOMMENT:
                printf("ERROR: INVALID COMMENT: %s Line: %d, Column: %d\n",yytext,yyline,(int)yycolumn);
                error=1;
                break;

                case BADSTRING:
                printf("ERROR: INVALID STRING: %s Line: %d, Column: %d\n",yytext,yyline,(int)yycolumn);
                error=1;
                break;

			}
		}
	}
	return 0;
}
