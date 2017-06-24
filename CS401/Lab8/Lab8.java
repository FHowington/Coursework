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
Lab 8
*/

import java.util.*;
import java.io.*;
 
public class Lab8
{
    public static void main( String[] args)
    {
        if ( args.length<1) { System.out.println("FATAL ERROR: Missing expression on command line\nexample: java Lab8 3+13/5-16*3\n"); System.exit(0); }
 
        // Stolen directly from stackoverflow with just a few mods :)
        String expr= args[0];  // i.e. somethinig like "4+5-12/3.5-5.4*3.14";
        System.out.println( "expr: " + expr );
        ArrayList<String> operatorList = new ArrayList<String>();
        ArrayList<String> operandList = new ArrayList<String>();
        // StringTokenizer is like an infile and calling .hasNext() that splits on + - / or *
        StringTokenizer st = new StringTokenizer( expr,"+-*/", true );
        while (st.hasMoreTokens())
        {
            String token = st.nextToken();
            if ("+-/*".contains(token))
                operatorList.add(token);
            else
                operandList.add(token);
        }
        System.out.println("Operators:" + operatorList);
        System.out.println("Operands:" + operandList);
 
        double result = evaluate( operatorList, operandList );
        System.out.println("The expression: " + expr + " evalutes to " + result + "\n");
    } // END MAIN
 
 
    // ............................................................................................
    // Y O U  W R I T E  T H I S  M E T H O D  (WHCIH YOU MAY TRANSPLANT INTO SIMPLE CALC)
    // ............................................................................................
 
    // TAKES THE LIST Of OPERATORS ANd OPERANDS RETURNS RESULT AS A DOUBLE
    static double evaluate( ArrayList<String> operatorList, ArrayList<String> operandList)
    {
        ArrayList<Double> operandListConv = new ArrayList<Double>();
        for(int i =0; i<operandList.size();i++)
            operandListConv.add(Double.parseDouble(operandList.get(i)));
 
        for(int i=0;i<operatorList.size();i++)
            if(operatorList.get(i).equals("*")) {
                operatorList.remove(i);
                Double result=operandListConv.get(i)*operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i,result);
                i--;
            }
            else if(operatorList.get(i).equals("/")) {
                operatorList.remove(i);
                Double result=operandListConv.get(i)/operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i,result);
                i--;
            }
 
        for(int i=0;i<operatorList.size();i++)
            if (operatorList.get(i).equals("+")) {
                operatorList.remove(i);
                Double result = operandListConv.get(i) + operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i, result);
                i--;
            } else if (operatorList.get(i).equals("-")) {
                operatorList.remove(i);
                Double result = operandListConv.get(i) - operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i, result);
                i--;
            }
        // STEP #1  SUGGEST YOU COPY/CONVERT THE OPERANDS LIST INTO A LIST OF DOUBLES
 
        //  NOW YOU HAVE AN ARRAYLIST OF STRINGS (OPERATORS) AND ANOTHER OF DOUBLES (OPERANDS)
 
        // FIRST PROCESS  ALL * and / operators FROM THE LIST
 
        // SECOND PROCESS  ALL + and - operators FROM THE LIST
 
        // return operands.get(0); // IT SHOULD BE THE ONLY THING LEFT IN OPERANDS
 
        return operandListConv.get(0);  // just to make it compile. you should return the .get(0) of operands list
    }
} // END LAB8
