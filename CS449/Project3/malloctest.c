/*
 * CS 449 Project 3
 * Forbes Turley
 * Summer 2017
 */
#include <stdio.h>
#include <string.h>
#include "mymalloc.h"
int main(){
    // Testing allocating a 3 regions, de-allocating the middle one
    // And then ensuring that the splitting of the region occurs properly
    void printll();
    void *x;
    x=my_next_fit_malloc(20);
    void *y;
    y=my_next_fit_malloc(100);
    void *z;
    z=my_next_fit_malloc(50);
    printll();
    printf("\nFreeing\n");
    my_free(y);
    printll();
    void *w;
    // This should cause there to be an allocated node of 34 bits, not in use
    w=my_next_fit_malloc(50);
    printf("\nReallocating space\n");
    printll();
    // Deallocating x then w should cause a single contiguous region to exist up to z
    my_free(x);
    my_free(w);
    printf("\nFreeing all but last, checking for coalescense\n");
    printll();

    // Checking to see what occurs when final spot is freed. We should cease to have any nodes
    my_free(z);
    printf("\nFreeing final node, seeing if all nodes dissappear\n");
    printll();
    z=my_next_fit_malloc(10);
    y=my_next_fit_malloc(10);
    printf("\nAllocating two nodes, then removing second and checking that deallocation occurs correctly\n");
    printll();
    my_free(y);
    printll();
    y=my_next_fit_malloc(10);
    my_free(z);
    my_free(y);
    printll();
    printf("Sbrk is %d\n",sbrk(0));
    return 0;
}
