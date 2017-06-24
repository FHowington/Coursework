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
