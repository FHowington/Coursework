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
