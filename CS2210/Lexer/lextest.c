#include <stdio.h>
#include <stdlib.h>
#include "lex.yy.c"

#define ANDnum 1
#define LIMIT1 500
#define LIMIT2 4096
#define EOFnum 40

int main(int argc, char** argv){
    //Determining whether we are reading from a file or reading from the standard input
	if ( argc > 0 )
	    yyin = (FILE*)(fopen( argv[1], "r" ));
	else
	    yyin = stdin;
	int i;
	for(i=0;i<LIMIT1;i++)
		locs[i] = -1;
	
	ST = malloc(sizeof(char)*LIMIT2);
    	int error=0;
	printf("Line\tColumnn\tToken\t\tIndex_in_String\n");


	while(1){
        if(error==1)
            break;
		int temp = yylex();
		if(temp == EOFnum){
			printf("%d\t%d\tEOFnum\n",yyline,(int)yycolumn);
			break;
		}
		else{
			switch(temp){
                //This giant switch statement accounts for all possible tokens. Case 0 is when a comment is input.
                //In this case, we simply ignore it. In all other cases, we output information about what the 
                //token is.
				case 0:
				break;

				case ANDnum:
				printf("%d\t%d\tANDnum\n",yyline,(int)yycolumn);
				break;

				case ASSGNnum:
				printf("%d\t%d\tASSGNnum\n",yyline,(int)yycolumn);
				break;

				case DECLARATIONnum:
				printf("%d\t%d\tDECLARATIONnum\n",yyline,(int)yycolumn);
				break;

				case DOTnum:
				printf("%d\t%d\tDOTnum\n",yyline,(int)yycolumn);
				break;

				case ENDDECLARATIONSnum:
				printf("%d\t%d\tENDDECLARATIONnum\n",yyline,(int)yycolumn);
				break;

				case EQUALnum:
				printf("%d\t%d\tEQUALnum\n",yyline,(int)yycolumn);
				break;

				case GTnum:
				printf("%d\t%d\tGTnum\n",yyline,(int)yycolumn);
				break;

				case IDnum:
				printf("%d\t%d\tIDnum\t\t%d\n",yyline,(int)yycolumn,yyval);
				break;

				case SCONSTnum:
				printf("%d\t%d\tSCONSTnum\t%d\n",yyline,(int)yycolumn,yyval);
				break;

				case INTnum:
				printf("%d\t%d\tINTnum\n",yyline,(int)yycolumn);
				break;

				case LBRACnum:
				printf("%d\t%d\tLBRACnum\n",yyline,(int)yycolumn);
				break;

				case LPARENnum:
				printf("%d\t%d\tLPARENnum\n",yyline,(int)yycolumn);
				break;

				case METHODnum:
				printf("%d\t%d\tMETHODnum\n",yyline,(int)yycolumn);
				break;

				case NEnum:
				printf("%d\t%d\tNEnum\n",yyline,(int)yycolumn);
				break;

				case ORnum:
				printf("%d\t%d\tORnum\n",yyline,(int)yycolumn);
				break;

				case PROGRAMnum:
				printf("%d\t%d\tPROGRAMnum\n",yyline,(int)yycolumn);
				break;

				case RBRACnum:
				printf("%d\t%d\tRBRACnum\n",yyline,(int)yycolumn);
				break;

				case RPARENnum:
				printf("%d\t%d\tRPARENnum\n",yyline,(int)yycolumn);
				break;

				case SEMInum:
				printf("%d\t%d\tSEMInum\n",yyline,(int)yycolumn);
				break;

				case VALnum:
				printf("%d\t%d\tVALnum\n",yyline,(int)yycolumn);
				break;

				case WHILEnum:
				printf("%d\t%d\tWHILEnum\n",yyline,(int)yycolumn);
				break;

				case CLASSnum:
				printf("%d\t%d\tCLASSnum\n",yyline,(int)yycolumn);
				break;

				case COMMAnum:
				printf("%d\t%d\tCOMMAnum\n",yyline,(int)yycolumn);
				break;

				case DIVIDEnum:
				printf("%d\t%d\tDIVIDEnum\n",yyline,(int)yycolumn);
				break;

				case ELSEnum:
				printf("%d\t%d\tELSEnum\n",yyline,(int)yycolumn);
				break;

				case EQnum:
				printf("%d\t%d\tEQnum\n",yyline,(int)yycolumn);
				break;

				case GEnum:
				printf("%d\t%d\tGEnum\n",yyline,(int)yycolumn);
				break;

				case ICONSTnum:
				printf("%d\t%d\tICONSTnum\n",yyline,(int)yycolumn);
				break;

				case IFnum:
				printf("%d\t%d\tIFnum\n",yyline,(int)yycolumn);
				break;

				case LBRACEnum:
				printf("%d\t%d\tLBRACEnum\n",yyline,(int)yycolumn);
				break;

				case LEnum:
				printf("%d\t%d\tLEnum\n",yyline,(int)yycolumn);
				break;

				case LTnum:
				printf("%d\t%d\tLTnum\n",yyline,(int)yycolumn);
				break;

				case MINUSnum:
				printf("%d\t%d\tMINUSnum\n",yyline,(int)yycolumn);
				break;

				case NOTnum:
				printf("%d\t%d\tNOTnum\n",yyline,(int)yycolumn);
				break;

				case PLUSnum:
				printf("%d\t%d\tPLUSnum\n",yyline,(int)yycolumn);
				break;

				case RBRACEnum:
				printf("%d\t%d\tRBRACEnum\n",yyline,(int)yycolumn);
				break;

				case RETURNnum:
				printf("%d\t%d\tRETURNnum\n",yyline,(int)yycolumn);
				break;

				case TIMESnum:
				printf("%d\t%d\tTIMESnum\n",yyline,(int)yycolumn);
				break;

				case VOIDnum:
				printf("%d\t%d\tVOIDnum\n",yyline,(int)yycolumn);
				break;
                
                case BADNAME:
                printf("ERROR: Malformed indentifier '%s', at line %d column %d\n",yytext,yyline,(int)yycolumn);
                break;

                case BADCOMMENT:
                printf("ERROR: Unmatched comment, at line %d column %d\n",yyline,(int)yycolumn);
                break;

                case BADSTRING:
                printf("ERROR: Unmatched string constant, at line %d column %d\n",yyline,(int)yycolumn);
                break;

                case BADSYMBOL:
                printf("ERROR: Undefined symbol %s, at line %d column %d\n",yytext,yyline,(int)yycolumn);
                break;
                

			}
		}
	}
	return 0;
}
