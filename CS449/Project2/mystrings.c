#include <stdio.h>
#include <string.h>

// Forbes Turley
// CS 449
// University of Pittsburgh
// Project 2
int main(int argc, char *argv[]){
    FILE *media;
    int atoi(char *str);
    int length=500;
    char string[length];
    if(argc < 2){
        printf("Error. No file specified.");
        return 1;
    }
    else if(argc == 2){
        int size;
        char c;
        int string_length;
        media = fopen(argv[1],"rb");
        if(!media){
            printf("Invalid file specified.");
            return 1;
        }
        fseek(media, 0, SEEK_SET);
        string_length=0;

        while(!feof(media)){
        fread(&c,sizeof(c),1,media);
            if(c >=32 && c<= 126){
                string[string_length++]=c;                    
            }
            else{
                if(string_length>=4){
                    string[string_length]='\0';
                    printf("%s\n",string);

                }
                    // Reset string length, and be sure to clear to char array
                    string_length=0;
                    strncpy(string,"",sizeof(string));
            }
        }
        // If there is any string remaining at the end of the file, it must also be output
        if(string_length>=4){
            string[string_length]='\0';
            printf("%s\n",string);
        }
        fclose(media);
    }
    return 1;
}

