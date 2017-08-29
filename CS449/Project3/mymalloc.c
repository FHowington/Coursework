/*
 * CS 449 Project 3
 * Forbes Turley
 * Summer 2017
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

typedef struct node node;
typedef node *node_ptr;
struct node{
    node_ptr previous;
    node_ptr next;
    int size;
    int used;
};

static node_ptr first=0;
static node_ptr last;
static node_ptr place;
static int total=0;
void *fitter(int);

void *my_next_fit_malloc(int size){
    node_ptr new;

    if(!first){
        new = sbrk(0);
        first=new;
        last=new;
        place=new;
        sbrk(size+sizeof(node));
        new->size = size;
        new->previous=NULL;
        new->next=NULL;
        new->used=1;
        total+=(size+sizeof(node));
        #ifdef DEBUG
        printf("\nTotal is %x\n",total);
        #endif

        return new+1;
    }
    else{
        void *loc = fitter(size);
        if(loc == NULL){
            new=sbrk(0);
            sbrk(size+sizeof(node));
            new->previous=last;
            last->next=new;
            last=new;
            new->size=size;
            new->used=1;
            new->next=NULL;
            total+=(size+sizeof(node));
            #ifdef DEBUG
            printf("\nTotal is %x\n",total);
            #endif
            return new+1;
        }
        else{
            return loc;
        }
    }
}

void my_free(void *ptr){
    node_ptr current;
    current=ptr-sizeof(node);

    // This indicates that the previous node is also unused so we should combine
    // Make sure to account for corner case where both prev and next are empty

    if(current->next!=NULL && ((node_ptr)current->next)->used==0){
        node_ptr next=current->next;
        if(next==last){
            last=current;
        }
        if(next->next != NULL){
            current->next=next->next;
            ((node_ptr)next->next)->previous=current;
        }
        else
            current->next=NULL;
        current->size+=(next->size+sizeof(node));
    }

    if(current->previous!=NULL && ((node_ptr)current->previous)->used==0){
        node_ptr previous=current->previous;
        if(current==last){
            last=previous;
        }
        if(current->next!=NULL){
            previous->next=current->next;
            ((node_ptr)current->next)->previous=previous;
        }
        else
            previous->next=NULL;
        previous->size+=(current->size+sizeof(node));
        current=previous;
    }

    current->used=0;
    if(current==last){
        if(current->previous!=NULL){
            last=current->previous;
            ((node_ptr)current->previous)->next=NULL;
        }
        else{
            last=NULL;
            first=0;
        }
        total=total-(sizeof(node)+current->size);
        sbrk(-(current->size+sizeof(node)));
        #ifdef DEBUG
        printf("\nTotal is %x\n",total);
        #endif

    }
}

// This prints all elements of the linked list
void printll(){
    node_ptr current = first;
    while(current!=NULL){
        printf("\n\nPrevious: %d\nNext: %d\nMem. location: %d\nSize: %d\nUse state: %d\n",current->previous,current->next,current,current->size,current->used);
        current=current->next;
    }
}

// Checking to see if there is a spot available within the current heap size
// If there is space available, we need to make sure we change the size of the node demarcating spot to correct size
void *fitter(int size){
    node_ptr current;
    current=place->next;
    while(current!=NULL){
        
        if(current->used==0 && current->size>=size){
            // Goal here is to create a new node at the end of the allocated space, and correctly link it
            // This indicates that sufficient size actually exists for this split to occur
            if(current->size>size+sizeof(node)){
                node_ptr new;
                new=current+sizeof(node)+size;
                if(current->next!=NULL){
                    new->next=current->next;
                    ((node_ptr)new->next)->previous=new;
                }
                else
                    new->next=NULL;
                new->used=0;
                new->previous=current;
                new->size=(current->size)-sizeof(node)-size;
                current->size=size;
                current->next=new;
                current->used=1;
            }
            place=current;

            // Adding 1 to compensate for size of the node
            return current+1;
        }
        else if(current==place){
            current=NULL;
        }
        else if(current==last){
            if(last!=first){
                current=first;
            }
            else{
                current=NULL;
            }
        }
        else{
            current=current->next;
        }
    }
    return NULL;
}


