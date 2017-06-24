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
Lab 9
*/

import java.io.*;
import java.util.*;
 
public class Lab9
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader infile = new BufferedReader(new FileReader(args[0]));
        HashMap<String,Integer> histogram = new HashMap<String,Integer>();
        String word;
        while ( infile.ready() )
        {
            word=infile.readLine();
            if(!histogram.containsKey(word))
                histogram.put(word,1);
            else
                histogram.put(word,(histogram.get(word)+1));
        }
        infile.close();
        printHistogram( histogram );
    } // END MAIN
 
    // YOU FILL IN THIS METHOD
    // NO PARTIAL CREDIT. YOU MUST PRINT THEM SORTED LIKE THE OUPTUT
    private static void printHistogram( HashMap<String,Integer> hm )
    {
        ArrayList<String> hmString = new ArrayList<>();
        for(String k:hm.keySet())
            hmString.add(k);
        Collections.sort(hmString);
        for(String k:hmString)
            System.out.println(k + "\t" + hm.get(k));
    }
} // END LAB9 CLASS
