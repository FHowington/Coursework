/*
 * CS 449 Lab 2
 * Forbes Turley
 * Summer 2017
 */
#include <stdio.h>
int main(){
printf("Please enter the weight you'd like to convert: ");
float weight;
scanf("%f",&weight);
printf("\nHere is the weight on other planets:\n\n");
printf("Mercury\t%.0f lbs\n",.38*weight);
printf("Venus\t%.0f lbs\n",.91*weight);
printf("Mars\t%.0f lbs\n",.38*weight);
printf("Jupiter\t%.0f lbs\n",2.54*weight);
printf("Saturn\t%.0f lbs\n",1.08*weight);
printf("Uranus\t%.0f lbs\n",.91*weight);
printf("Neptune\t%.0f lbs\n",1.19*weight);
return 0;
}
