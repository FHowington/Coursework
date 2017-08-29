/*
 * Forbes Turley 7/28/17
 * CS 449 Assignment 4
 * University of Pittsburgh
 * This file demonstrates the functionality of the 
 * device /dev/pi and its readability
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
int main(int argc, char *argv[]){
// Checks to make sure correct number of command line args used
	if(argc != 3){
		printf("Error: Invalid number of arguments.\n");
		printf("Correct format is pi_digit FROM TO\n");
		return 0;
	}
// Determines how far to seek in the output
	int total_length = atoi(argv[2]);
	int skip_length = atoi(argv[1]);
	int output_length = total_length - skip_length + 2;
	if(total_length<skip_length){
		printf("Invalid arguments\n");
		return 0;
	}

// Ensures that valid starting index is used
	if(skip_length<0){
		printf("Cannot have negative starting index\n");
		return 0;
	}

	FILE *fd;
	char *temp;
	temp = malloc(total_length*sizeof(char));
	char *output;
	output = malloc(output_length*sizeof(char));

// Seeks to correct spot in pi, reads output
	fd = fopen("/dev/pi", "r");
	fseek(fd, skip_length, SEEK_SET);
	int x = fread(output, sizeof(char), output_length-1, fd);
	fclose(fd);
	output[output_length-1]='\0';
	printf("%s\n",output);
	return 0;
}







