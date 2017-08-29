/*
 * CS 449 Project 1
 * Forbes Turley
 * Summer 2017
 */
#include <stdio.h>
#include <string.h>
int main(int argc, char *argv[]){
    FILE *media;
    struct tag{
        /*Struct should be contiguous in memory.*/
        char tagid[3];
        char title[30];
        char artist[30];
        char album[30];
        char year[4];
        char comment[28];
        char sep;
        unsigned char track;
        char genre;
    } tag;
    int atoi(char *str);
    char eval[50];
    
    /*No file name specified*/
    if(argc < 2){
        printf("Error. No file specified.");
    }
    else if(argc == 2){

        media = fopen(argv[1],"r+b");
        if(media == NULL){
            printf("Invalid file.");
            return 0;
        }

        /*ID3 tag is 128 bits, so if it is present, it will be located in the last 128 bits*/
        fseek(media, -128, SEEK_END);
        fread(&tag,sizeof(tag),1,media);
        if(tag.tagid[0] != 'T' || tag.tagid[1] != 'A' || tag.tagid[2] != 'G'){
            printf("No ID3 Tag found");
        }
        else{
            /*
             * Loading each element of ID3 tag into array one char bigger to allow for buffer between adjacent elements
             * Buffer of each item is manually defined by setting last char of each array to null
             */

            char tagid[31];
            char title[31];
            char artist[31];
            char album[31];
            char year[5];
            char comment[29];
            char sep;
            unsigned char track[2];
            char genre;

            strncpy(title,tag.title,sizeof(tag.title));
            title[30]='\0';
            strncpy(artist,tag.artist,sizeof(tag.artist));
            artist[30]='\0';
            strncpy(album,tag.album,sizeof(tag.album));
            album[30]='\0';
            strncpy(year,tag.year,sizeof(tag.year));
            year[4]='\0';
            strncpy(comment,tag.comment,sizeof(tag.comment));
            comment[28]='\0';
            track[0]=tag.track;
            track[1]='\0';

            printf("\nTitle: %s\nArtist: %s\nAlbum: %s\nYear: %s\nComment: %s\nTrack: %d\n",title,artist,album,year,comment,tag.track);
        }
    }
    else{
        /*
         * If ID3 tag is not found, load default values into each item in the tag struct.
         * This is done so that when the struct is written into the file, it will have
         * non-initialized fields set to null
         */

        media = fopen(argv[1],"r+");
        fseek(media, -128, SEEK_END);
        fread(&tag,sizeof(tag),1,media);
        fseek(media, -128, SEEK_END);
        if(tag.tagid[0] != 'T' || tag.tagid[1] != 'A' || tag.tagid[2] != 'G'){

            /*Must seek to end of file so that tag is appended to end instead of overwriting data*/
            fseek(media, 0, SEEK_END);
            strncpy(tag.title,"",sizeof(tag.title));
            strncpy(tag.artist,"",sizeof(tag.artist));
            strncpy(tag.album,"",sizeof(tag.album));
            strncpy(tag.year,"",sizeof(tag.year));
            strncpy(tag.comment,"",sizeof(tag.comment));
            tag.sep = '\0';
            tag.track='\0';
            tag.genre='\0';
            tag.tagid[0]='T';
            tag.tagid[1]='A';
            tag.tagid[2]='G';
        }

        int i;

        /*
         * Items in the args are read in two at a time. If the first item in a pair is not found to be a
         * valid field title, the program outputs that there was an invalid field but continues to parse
         * remaining elements in args
         */

        for(i=2;i<argc;i++){
            strncpy(eval,"-title",sizeof(eval));
            if(!strncmp(eval,argv[i],sizeof(eval))){
                strncpy(tag.title,argv[++i],sizeof(tag.title));
                continue;
            }
            strncpy(eval,"-artist",sizeof(eval));
            if(!strncmp(eval,argv[i],sizeof(eval))){
                strncpy(tag.artist,argv[++i],sizeof(tag.artist));
                continue;
            }
            strncpy(eval,"-album",sizeof(eval));
            if(!strncmp(eval,argv[i],sizeof(eval))){
                strncpy(tag.album,argv[++i],sizeof(tag.album));
                continue;
            }
            strncpy(eval,"-year",sizeof(eval));
            if(!strncmp(eval,argv[i],sizeof(eval))){
                strncpy(tag.year,argv[++i],sizeof(tag.year));
                continue;
            }
            strncpy(eval,"-comment",sizeof(eval));
            if(!strncmp(eval,argv[i],sizeof(eval))){
                strncpy(tag.comment,argv[++i],sizeof(tag.comment));
                continue;
            }
            strncpy(eval,"-track",sizeof(eval));
            if(!strncmp(eval,argv[i],sizeof(eval))){
                tag.track=atoi(argv[++i]);
                continue;
            }
            else{
                printf("Invalid tag specified.");
                i++;
            }
        }
        /*
         * Last step is to write into the file. 128 bits written at once. If tag already exists, old tag
         * is overwritten. If no tag exists, 128 bits are appended.
         */
        fwrite(&tag,1,sizeof(tag),media);
        fclose(media);
    }
    return (0);
}

