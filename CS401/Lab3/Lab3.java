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
Lab 3
*/

/* Lab3.java  Resizes an array every time it fills up while reading a large file. */
 
import java.io.*;
import java.util.*;
 
public class Lab3
{
    static final int INITIAL_CAPACITY = 10;
 
    public static void main (String[] args) throws Exception
    {
                // ALWAYS TEST FIRST TO VERIFY USER PUT REQUIRED INPUT FILE NAME ON THE COMMAND LINE
        if (args.length < 1 )
        {
            System.out.println("\nusage: C:\\> java Lab3 <input filename>\n\n"); // i.e. C:\> java Lab3 dictionary.txt
            System.exit(0);
        }
 
        String[] wordList = new String[INITIAL_CAPACITY];
        int wordCount = 0;
        BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
        int numOfUpsizes=0;
        while ( infile.ready() ) // i.e. while there are more lines of text in the file
        {
                String word = infile.readLine();
                 
                if ( wordCount == wordList.length )  // an array's .length() is its CAPACITY, not count
                {
                    wordList = upSizeArr( wordList );
                    System.out.format( "after resize#%d  size=%d,  count=%d\n",++numOfUpsizes,wordList.length,wordCount );
                }
                wordList[wordCount++] = word; // Now wordList has enough capacity
        } //END WHILE INFILE READY
        infile.close();
        System.out.format( "after file closed: size=%d,  count=%d\n",wordList.length,wordCount );
 
        wordList = trimArr( wordList, wordCount );
        System.out.format( "after final trim: size=%d,  count=%d\n",wordList.length,wordCount );
    } // END main
 
    private static String[] upSizeArr( String[] fullArr )
    {
        String[] temp=new String[fullArr.length*2];
        for(int i=0;i<fullArr.length;i++)
        temp[i]=fullArr[i];
 
        return temp;
    }
 
    private static String[] trimArr( String[] arr, int count )
    {
        if(count<arr.length){
            String[] temp=new String[count];
            for(int i=0;i<count;i++)
                temp[i]=arr[i];
            return temp;
        }
        return arr;
     
    }
 
 
} // END CLASS LAB#3
