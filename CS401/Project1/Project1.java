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
Project 1
*/

// F16 CS 401 Speeding Ticket Project

 
import java.io.*;
import java.util.*;
 
public class Project1
{
    public static void main (String args[])
    {
        // Create a scanner to read from keyboard
        Scanner kbd = new Scanner (System.in);
 
        String firstName="N/A",lastName="N/A";
        int age=0, speedLimit=0, actualSpeed=0, mphOverLimit=0;
        int baseFine=0, underAgeFine=0, zoneFine=0, totalFine=0;
        // DO NOT ADD TO OR MODIFY ABOVE THIS LINE
        boolean constructionZone;
        System.out.print("Enter first name and last name: ");
        firstName=kbd.next();
        lastName=kbd.next();
        System.out.print("Your age: ");
        age=kbd.nextInt();
        System.out.print("The speed limit: ");
        speedLimit=kbd.nextInt();
        System.out.print("Driver's actual speed: ");
        actualSpeed=kbd.nextInt();
        mphOverLimit=actualSpeed-speedLimit;
        System.out.print("Did violation occur in a construction zone? (true/false): ");
 
        if(mphOverLimit<5)
            baseFine=0;
        else if(mphOverLimit<=20)
            baseFine=30*(mphOverLimit/5);
        else
            baseFine=50*(mphOverLimit/5);
 
        if(kbd.nextBoolean())
            zoneFine=baseFine;
        if(age<21 && mphOverLimit>20)
            underAgeFine+=300;
 
        totalFine=baseFine+zoneFine+underAgeFine;
        // your variables & code in here
 
 
 
 
        // DO NOT ADD TO OR MODIFY BELOW THIS LINE
        System.out.println();
        System.out.format( "name: %s, %s\n",lastName,firstName );
        System.out.format( "age: %d yrs.\n",age );
        System.out.format( "actual speed: %d mph.\n",actualSpeed );
        System.out.format( "speed limit: %d mph.\n",speedLimit );
        System.out.format( "mph over limit: %d mph.\n",mphOverLimit );
        System.out.format( "base fine: $%d\n",baseFine );
        System.out.format( "zone fine: $%d\n",zoneFine );
        System.out.format( "under age fine: $%d\n",underAgeFine );
        System.out.format( "total fine: $%d\n",totalFine );
    } // END MAIN
} // END PROJECT1 CLASS
