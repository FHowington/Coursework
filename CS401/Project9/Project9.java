/**
Copyright (c) 2017, Forbes Turley
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the <organization> nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
University of Pittsburgh
CS401 Project 9
*/

import java.io.*;
import java.util.*;
// DO NOT IMPORT JAVA.LANG;

public class Project9 // the swamp
{
    public static void main(String[] args) throws Exception
	{
		int[] dropInPt = new int[2]; // row and col will be on the 2nd line of input file;
		int[][] swamp = loadSwamp( args[0], dropInPt );
		int row=dropInPt[0], col = dropInPt[1];
		String path="";

		// DECLARE ANY NEEDED VARIABLE THAT WILL BE PASSED INTO YOUR METHOD THAT FINDS/PRINTS PATHS OUT

		// CALL YOUR METHOD THAT FINDS/PRINTS PATHS OUT
		solver(path,swamp,row, col);

	} // END MAIN

	// ###################################################

	// DO NOT MODIFY THIS METHOD
   	// ----------------------------------------------------------------
	private static int[][] loadSwamp( String infileName, int[] dropInPt  ) throws Exception
	{
		Scanner infile = new Scanner( new File(infileName) );
		int rows=infile.nextInt();
		int cols = rows;  		// ASSUME A SQUARE GRID
		dropInPt[0]=infile.nextInt();  dropInPt[1]=infile.nextInt();
		int[][] swamp = new int[rows][cols];
		for(int r = 0; r < rows ; r++)
			for(int c = 0; c < cols; c++)
				swamp[r][c] = infile.nextInt();

		infile.close();
		return swamp;
	} // END LOAD SWAMP



  	// DO NOT MODIFY THIS METHOD - IT IS JUST FOr DEBUGGING.
	// ----------------------------------------------------------------
	private static void printSwamp(String label, int[][] swamp )
	{
		System.out.println( label );
		System.out.print("   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print( c + " " ) ;
		System.out.print( "\n   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print("- ");
		System.out.print( "\n");

		for(int r = 0; r < swamp.length; r++)
		{	System.out.print( r + "| ");
			for(int c = 0; c < swamp[r].length; c++)
				System.out.print( swamp[r][c] + " ");
			System.out.println("|");
		}
		System.out.print( "   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print("- ");
		System.out.print( "\n");
	} // END PRINT SWAMP METHOD
	private static void solver(String path, int[][] maze, int row, int col){
    	path = new String(path + "[" + row + "," + col + "]");
		if((row == 0 || row == maze.length-1) || (col == 0 || col == maze.length-1)) {
			System.out.println(path);
			return;
		}


		if((row>0) && maze[row-1][col] == 1) {
    		maze[row][col]=-1;
			solver(path, maze, row - 1, col);
			maze[row][col]=1;
		}
    	if(row < (maze.length-1) && maze[row+1][col]==1) {
			maze[row][col]=-1;
			solver(path, maze, row + 1, col);
			maze[row][col]=1;
		}
    	if((col>0) && maze[row][col-1]==1) {
			maze[row][col]=-1;
			solver(path, maze, row, col - 1);
			maze[row][col]=1;
		}
    	if((col < maze.length -1) && maze[row][col+1]==1) {
			maze[row][col]=-1;
			solver(path, maze, row, col + 1);
			maze[row][col]=1;
		}
		if((col > 0 && row > 0) && maze[row-1][col-1] == 1) {
			maze[row][col]=-1;
			solver(path, maze, row - 1, col-1);
			maze[row][col]=1;
		}
		if((row < maze.length-1 && col < maze.length-1) && maze[row+1][col+1]==1) {
			maze[row][col]=-1;
			solver(path, maze, row + 1, col+1);
			maze[row][col]=1;
		}
		if((row < maze.length-1 && col > 0) && maze[row+1][col-1]==1) {
			maze[row][col]=-1;
			solver(path, maze, row+1, col - 1);
			maze[row][col]=1;
		}
		if((row > 0 && col < maze.length -1) && maze[row-1][col+1]==1) {
			maze[row][col]=-1;
			solver(path, maze, row-1, col + 1);
			maze[row][col]=1;
		}
	}

} // E N D   S W A M P    C L A S S
