#include <stdio.h>
#include <stdlib.h>

#define ANDnum 1
#define LIMIT1 500
#define LIMIT2 4096
#define EOFnum 40
int yylex();

int main(int argc, char** argv) {
    while(1){

        int temp = yylex();
        if(temp == EOFnum){
            printf("EOF reached\n");
            break;
        }
        else
            printf("Result is %d\n",temp);
    }
    return 0;
}  
