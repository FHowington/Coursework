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
CS401
Project 3
*/

/* Project3.java  Dynamic histogram */
 
import java.io.*;
import java.util.*;
 
public class Project3
{
    static final int INITIAL_CAPACITY = 10;
    public static void main (String[] args) throws Exception
    {
        // ALWAYS TEST FIRST TO VERIFY USER PUT REQUIRED INPUT FILE NAME ON THE COMMAND LINE
        if (args.length < 1 )
        {
            System.out.println("\nusage: C:\\> java Project3 <input filename>\n\n"); // i.e. C:\> java Project3 dictionary.txt
            System.exit(0);
        }
        int[] histogram = new int[0]; // histogram[i] == # of words of length n
 
        /* array of String to store the words from the dictionary. 
            We use BufferedReader (not Scanner). With each word read in, examine it's length and update word length frequency histogram accordingly.
        */
 
        String[] wordList = new String[INITIAL_CAPACITY];
        int wordCount = 0;
        BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
        while ( infile.ready() )
        {
            String word = infile.readLine();
 
            if((word.length()+1)>histogram.length){
                histogram=upSizeHisto(histogram,word.length()+1);
            }
 
            histogram[word.length()]++;
            wordCount++;
            if(wordCount>wordList.length-1)
                wordList=upSizeArr(wordList);
            wordList[wordCount]=word;
 
        } 
        infile.close();
 
        wordList = trimArr( wordList, wordCount );
        System.out.println( "After final trim: wordList length: " + wordList.length + " wordCount: " + wordCount );
 
        // PRINT WORD LENGTH FREQ HISTOGRAM
        for ( int i = 0; i < histogram.length ; i++ )
            System.out.format("words of length %2d  %d\n", i,histogram[i] );
 
    } // END main
 
    // YOU MUST CORRECTLY COPY THE STRING REFS FROM THE OLD ARR TO THE NEW ARR
    static String[] upSizeArr( String[] fullArr )
    {   
        String[] temp=new String[fullArr.length*2];
        for(int i=0;i<fullArr.length;i++)
        temp[i]=fullArr[i];
 
        return temp;
    }
    static String[] trimArr( String[] oldArr, int count )
    {
        if(count<oldArr.length){
            String[] temp=new String[count];
            for(int i=0;i<count;i++)
                temp[i]=oldArr[i];
            return temp;
        }
        return oldArr;
    }
 
    // YOU MUST CORRECTLY COPY THE COUNTS FROM OLD HISTO TO NEW HISTO
    static int[] upSizeHisto( int[] oldArr, int newLength )
    {
 
        int[] temp=new int[newLength];
        for(int i=0;i<oldArr.length;i++)
            temp[i]=oldArr[i];
        return temp;
    }
} // END CLASS PROJECT#3
