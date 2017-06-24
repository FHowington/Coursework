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
CS401 Project 10
*/

import java.io.*;
import java.util.*;
// DO NOT IMPORT JAVA.LANG;

public class Boggle // the swamp
{
    static int size;
    static ArrayList<String> dictionary = new ArrayList<>();
    static ArrayList<String> founds = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        String[][] swamp = loadSwamp( args[0]);
        Collections.sort(dictionary);
        String path="";
        // DECLARE ANY NEEDED VARIABLE THAT WILL BE PASSED INTO YOUR METHOD THAT FINDS/PRINTS PATHS OUT

        // CALL YOUR METHOD THAT FINDS/PRINTS PATHS OUT
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                solver(path,swamp,i,j);

        Collections.sort(founds);
        for(int i=0;i<founds.size();i++)
            System.out.println(founds.get(i));


    } // END MAIN

    // ###################################################

    // DO NOT MODIFY THIS METHOD
    // ----------------------------------------------------------------
    private static String[][] loadSwamp( String infileName) throws Exception
    {
        Scanner infile = new Scanner( new File(infileName) );
        size=infile.nextInt();
        String[][] swamp = new String[size][size];
        for(int r = 0; r < size ; r++)
            for(int c = 0; c < size; c++)
                swamp[r][c] = infile.next();

        infile.close();

        BufferedReader infile2 = new BufferedReader(new FileReader("dictionary.txt"));
        while ( infile2.ready() )
        {
            dictionary.add(infile2.readLine());
        }
        infile.close();

        return swamp;



    } // END LOAD SWAMP




    private static void solver(String path, String[][] maze, int row, int col) {
        path = path + maze[row][col];

        int start = 0;
        int end = dictionary.size() - 1;
        int mid;

        if(path.length()>2) {
            while (start <= end) {
                mid = (start + end) / 2;
                String answerSplit = dictionary.get(mid);

                if (answerSplit.equals(path)) {
                    if (!founds.contains(path))
                        founds.add(path);
                    break;
                } else if (path.compareTo(answerSplit) < 0) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }


        start = 0;
        end = dictionary.size() - 1;

        boolean found = false;

        while (start <= end) {
            mid = (start + end) / 2;
            String answerSplit = dictionary.get(mid);
            if (answerSplit.startsWith(path)) {
                found = true;
                break;
            } else if (path.compareTo(answerSplit) < 0) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }


        if (found) {
            int size2 = size - 1;

            if ((row > 0) && maze[row - 1][col].equals(maze[row - 1][col].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row - 1, col);
                maze[row][col] = maze[row][col].toLowerCase();
            }
            if ((row < size2) && maze[row + 1][col].equals(maze[row + 1][col].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row + 1, col);
                maze[row][col] = maze[row][col].toLowerCase();
            }

            if ((col > 0) && maze[row][col - 1].equals(maze[row][col - 1].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row, col - 1);
                maze[row][col] = maze[row][col].toLowerCase();
            }
            if ((col < size2) && maze[row][col + 1].equals(maze[row][col + 1].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row, col + 1);
                maze[row][col] = maze[row][col].toLowerCase();
            }
            if ((row > 0) && (col > 0) && maze[row - 1][col - 1].equals(maze[row - 1][col - 1].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row - 1, col - 1);
                maze[row][col] = maze[row][col].toLowerCase();
            }
            if ((row < size2) && (col < size2) && maze[row + 1][col + 1].equals(maze[row + 1][col + 1].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row + 1, col + 1);
                maze[row][col] = maze[row][col].toLowerCase();
            }
            if ((row > 0) && col < size2 && maze[row - 1][col + 1].equals(maze[row - 1][col + 1].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row - 1, col + 1);
                maze[row][col] = maze[row][col].toLowerCase();
            }
            if ((row < size2) && col > 0 && maze[row + 1][col - 1].equals(maze[row + 1][col - 1].toLowerCase())) {
                maze[row][col] = maze[row][col].toUpperCase();
                solver(path, maze, row + 1, col - 1);
                maze[row][col] = maze[row][col].toLowerCase();
            }
        }
    }

} // E N D   S W A M P    C L A S S
