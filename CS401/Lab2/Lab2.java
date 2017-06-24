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
Lab 2
*/
 
import java.io.*; // BufferedReader
import java.util.*; // Scanner
 
public class Lab2
{
    public static void main (String args[]) throws Exception // i.e. the input file you put on cmd line is not in directory
    {
        // ALWAYS TEST FIRST TO VERIFY USER PUT REQUIRED INPUT FILE NAME ON THE COMMAND LINE
        if (args.length < 1 )
        {
            System.out.println("\nusage: C:\\> java Lab2 <input filename>\n\n"); // i.e. C:\> java Lab2 input.txt
            System.exit(0);
        }
        BufferedReader infile = new BufferedReader (new FileReader( args[0] )); // we read our text file line by line
        int lineNum=0;
        while( infile.ready() )
        {
            String line = toAlphaLowerCase(infile.readLine());
            if ( isPalindrome( line ) )
                System.out.format("<%s> IS palindrome.\n",line);
            else
                System.out.format("<%s> NOT palindrome.\n",line);
        }
    } // END MAIN
     
    // ******* MODIFY NOTHING ABOVE  THIS   LINE YOU FILL IN THE METHODS BELOW *******  
    // RETURNS A STRING WITH ALL NON ALPHABETIC CHARS REMOVED. ALL REMAINING ARE ALPHAS CONVERTED TO LOWER CASE
    // "Madam I'm Adam" returns "madamimadam" which is now ready for a simple palindromic test
    // To test whether a char is alpha i.e. letter of the alphabet 
    // read this ==> https://docs.oracle.com/javase/tutorial/i18n/text/charintro.html
    static String toAlphaLowerCase( String s )
    {
    // I implemented a recursive solution for fun. This is obviously an extremely inefficient way of 
    // solving the problem.
        String result=null;
        if(s.length() == 0)
            result= s;
        else if(Character.isLetter(s.charAt(0)))
            result= Character.toString(s.charAt(0))+ toAlphaLowerCase(s.substring(1,s.length()));
        else
            result=toAlphaLowerCase(s.substring(1,s.length()));
        return result.toLowerCase();
    }
    // RETURNs true if and only if the string passed in is a palindrome
    static boolean isPalindrome( String s )
    {
        boolean result=true;
        for(int ii=0;ii<(s.length()/2);ii++)
            if(s.charAt(ii)!=s.charAt(s.length()-(ii+1)))
                result=false;
        return result; // (just to make it compile) YOU CHANGE AS NEEDED 
    }
} // END LAB2 CLASS
