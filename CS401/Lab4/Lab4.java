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
Lab 4
*/
/* Lab4.java  Reads two files into two arrays then checks both arrays for dupes */
 
import java.io.*;
import java.util.*;
 
public class Lab4
{
    static final int INITIAL_CAPACITY = 10;
    static final int NOT_FOUND = -1; // indexOfFirstDupe returns this value if no dupes found
    public static void main (String[] args) throws Exception
    {
        // ALWAYS TEST FIRST TO VERIFY USER PUT REQUIRED INPUT FILE NAME ON THE COMMAND LINE
        if (args.length < 1 )
        {
            System.out.println("\nusage: C:\\> java Lab4 <numbers file> <words filename>\n\n"); // i.e. C:\> java Lab4 10000ints.txt 172822words.txt
            System.exit(0);
        }
 
        String[] wordList = new String[INITIAL_CAPACITY];
        int[] intList  =  new int[INITIAL_CAPACITY];
        int wordCount = 0, intCount=0;
 
        Scanner intFile = new Scanner( new File(args[0]) );
        BufferedReader wordFile = new BufferedReader( new FileReader(args[1]) );
 
        // P R O C E S S   I N T   F I L E
        while ( intFile.hasNextInt() ) // i.e. while there are more ints in the file
        {   if ( intCount == intList.length )
            intList = upSizeArr( intList );
            intList[intCount++] = intFile.nextInt();
        } //END WHILE intFile
        intFile.close();
        System.out.format( "%s loaded into intList array. size=%d, count=%d\n",args[0],intList.length,intCount );
        int dupeIndex = indexOfFirstDupe( intList, intCount );
        if ( dupeIndex == NOT_FOUND )
            System.out.format("No duplicate values found in intList\n");
        else
            System.out.format("First duplicate value in intList found at index %d\n",dupeIndex);
 
 
        // P R O C E S S   S T R I N G    F I L E
        while ( wordFile.ready() ) // i.e. while there is another line (word) in the file
        {   if ( wordCount == wordList.length )
            wordList = upSizeArr( wordList );
            wordList[wordCount++] = wordFile.readLine();
        } //END WHILE wordFile
        wordFile.close();
        System.out.format( "%s loaded into word array. size=%d, count=%d\n",args[1],wordList.length,wordCount );
        dupeIndex = indexOfFirstDupe( wordList, wordCount );
        if ( dupeIndex == NOT_FOUND )
            System.out.format("No duplicate values found in wordList\n");
        else
            System.out.format("First duplicate value in wordList found at index %d\n",dupeIndex);
 
    } // END MAIN
 
    //##################################################################################################
    // FYI. Methods that don't say private are by default, private.
 
    // copy/adapt your working code from lab3 || project3
    static String[] upSizeArr( String[] fullArr )
    {
        String[] temp = new String[fullArr.length*2];
        for(int i=0;i<fullArr.length;i++)
            temp[i]=fullArr[i];
        return temp; // just to make it compile
 
    }
    // copy/adapt your working code from lab3 || project3
    static int[] upSizeArr( int[] fullArr )
    {
        int[] temp = new int[fullArr.length*2];
        for(int i=0;i<fullArr.length;i++)
            temp[i]=fullArr[i];
        return temp;
        /* Y O U R  C O D E   H E R E */
    }
 
    // use Arrays.sort() before scanning for dupe
    static int indexOfFirstDupe( int[] arr, int count )
    {
        Arrays.sort(arr,0,count);
        for(int i=1;i<count;i++)
            if(arr[i]==arr[i-1])
                return i;
        return NOT_FOUND; // just to make it compile
    }
 
    // use Array.sort() before scanning for dupe
    static int indexOfFirstDupe( String[] arr, int count )
    {
        Arrays.sort(arr,0,count);
        for(int i=1;i<count;i++)
            if(arr[i].equals(arr[i-1]))
                return i;
        return NOT_FOUND; // just to make it compile
    }
 
} // END CLASS LAB#4
