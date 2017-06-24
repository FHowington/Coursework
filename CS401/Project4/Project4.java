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
Project 4
*/


/* Project4.java  InsertInOrder with bSearch optimization to compute insertion index */
 
import java.util.*;
import java.io.*;
 
public class Project4
{
    static final int INITIAL_CAPACITY = 5;
 
    public static void main( String args[] ) throws Exception
    {
        // ALWAYS TEST FIRST TO VERIFY USER PUT REQUIRED INPUT FILE NAME ON THE COMMAND LINE
        if (args.length < 1 )
        {
            System.out.println("\nusage: C:\\> java Project4 <input filename>\n\n"); // i.e. C:\> java Project4 P4input.txt
            System.exit(0);
        }
 
        // LOAD FILE INTO ARR USING INSERINORDER
 
        Scanner infile =  new Scanner( new File( args[0] ) );
        int[] arr = new int[INITIAL_CAPACITY];
        int count= 0;
        while (infile.hasNextInt())
        {
            if ( count==arr.length )
                    arr = upSizeArr(arr);
            insertInOrder( arr, count++, infile.nextInt() );
        }
        infile.close();
        arr=trimArr(arr,count); // Now count == .length
        System.out.println( "Sorted array of " + arr.length + " ints from " + args[0] + " after insertInOrder:" );
        printArray( arr );  // we trimmed it thus count == length so we don't bother to pass in count
 
    } // END MAIN
    // ############################################################################################################
 
    // USE AS IS - DO NOT MODIFY
    static void printArray( int[] arr  )
    {
        for( int i=0 ; i<arr.length ;++i )
            System.out.print(arr[i] + " " );
        System.out.println();
    }
 
    // USE AS IS - DO NOT MODIFY
    static int[] upSizeArr( int[] fullArr )
    {
        int[] upSizedArr = new int[ fullArr.length * 2 ];
        for ( int i=0; i<fullArr.length ; ++i )
            upSizedArr[i] = fullArr[i];
        return upSizedArr;
    }
 
    // USE AS IS - DO NOT MODIFY
    static int[] trimArr( int[] oldArr, int count )
    {
        int[] trimmedArr = new int[ count ];
        for ( int i=0; i<count ; ++i )
            trimmedArr[i] = oldArr[i];
        return trimmedArr;
    }
 
    // ############################################################################################################
    static void insertInOrder( int[] arr, int count, int key   )
    {
        int index=bSearch( arr, count, key ); 
        if(index<=0)
            index*=-1;
        for(int i=count;i>index;i--)
            arr[i]=arr[i-1];
        arr[index]=key;
         
 
    }
 
    // IF KEY EXISTS IN THE ARRAY RETURN indexWhereFound
    // ELSE FIGURE OUT THE indexWhereItBelongs
    // AND return -(indexWhereItBelongs+1);
    //
    static int bSearch(int[] a, int count, int key)
    {
        int start=0,mid,Index=0;
        boolean found=false;
 
        while(!found){
            mid=(int)(start/2.0+count/2.0);
            if(a[mid]==key){
                Index= mid;
                found=true;
            }
            else if(start>=count){
                Index= -1*mid;
                found=true;
            }
            else if(a[mid]<key)
                start=mid+1;
            else
                count=mid;
        }
        return Index;   
    }
 
} // END PROJECT4
