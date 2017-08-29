// CS 1501 Summer 2017
// Test program for DictInterface.  Use this program to see how the DictInterface
// works and also to test your DLB implementation of the DictInterface (for Part B
// of Assignment 1).  See the posted output file for the correct output.
// As written the program uses MyDictionary, so it will work and show you the
// output using the MyDictionary class.  To test your DBL class substitute a 
// DLB object as indicated below.  Note that since MyDictionary and DLB should
// both implement DictInterface no other changes are necessary for the program
// to work.

import java.io.*;
import java.util.*;
public class DictTest
{
	public static void main(String [] args) throws IOException
	{
		Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
		String st;
		StringBuilder sb;
		DictInterface D = new DLB();
	        // Change MyDictionary() above to DLB() to test your DLB

		while (fileScan.hasNext())
		{
			st = fileScan.nextLine();
			D.add(st);
		}

		String [] tests = {"abc", "abe", "abet", "abx", "ace", "acid", "hives",
						   "iodi", "iodine", "iodiner", "inval", "zoo", "zool", "zoologist", 
						   "zoologists", "zurich"};
		for (int i = 0; i < tests.length; i++)
		{
			sb = new StringBuilder(tests[i]);
			int ans = D.searchPrefix(sb);
			System.out.print(sb + " is ");
			switch (ans)
			{
				case 0: System.out.println("not found");
					break;
				case 1: System.out.println("a prefix");
					break;
				case 2: System.out.println("a word");
					break;
				case 3: System.out.println("a word and prefix");
			}
		}
	}
}

