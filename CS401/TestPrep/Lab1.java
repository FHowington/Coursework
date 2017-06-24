// Lab1.java     STARTER FILE
 
import java.io.*; // I/O
import java.util.*; // Scanner class
 
public class Lab1
{
    public static void main( String args[] ) throws Exception
    {
        final double MILES_PER_MARATHON = 26.21875; // i.e 26 miles 285 yards
 
        Scanner kbd = new Scanner (System.in);
        double aveMPH=0, aveMinsPerMile=0, aveSecsPerMile=0; // YOU CALCULATE THESE BASED ON ABOVE INPUTS
        System.out.print("Enter marathon time in hrs minutes seconds: "); // i.e. 3 49 37
        // DO NOT WRITE OR MODIFY ANYTHING ABOVE THIS LINE
 
 
        double hh=kbd.nextInt();
        double mm=kbd.nextInt();
        double ss=kbd.nextInt();
 
        // totalTime stores total number of seconds in marathon time
        double totalTime=hh*3600+mm*60+ss;
        double secondsPerMile=totalTime/MILES_PER_MARATHON;
 
        // Casts to int to remove remainder
        aveMinsPerMile=(int)secondsPerMile/60;
        aveSecsPerMile=secondsPerMile%60;
 
        // Calculates total hours as a decimal, and then calculates average MPH
        aveMPH=MILES_PER_MARATHON/(hh+ mm/60 + ss/3600);
 
 
 
        /*
            Y O U R  V A R I A B L E S  &  C O D E   G O   H E R E.   N O   O U T P U T   S T A T E M E N T S.
 
            1) define some variables to store the hh, mm, & ss the user types in
            2) use calls to kbd.nextInt() to save those hh mm ss values.
            3) define any needed variables to do calculations on the hh, mm & ss vars
            4) assign values into aveMPH, aveMinsPerMile & aveSecsPerMile
        */
 
        // DO NOT WRITE OR MODIFY ANYTHING BELOW THIS LINE. LET MY CODE PRINT THE VALUES YOU CALCULATED
        System.out.println();
        System.out.format("Average MPH was: %.2f mph\n", aveMPH  );
        System.out.format("Average mile split was %.0f mins %.1f seconds per mile", aveMinsPerMile, aveSecsPerMile );
        System.out.println();
 
    } // END MAIN METHOD
} // END LAB1 CLASS
