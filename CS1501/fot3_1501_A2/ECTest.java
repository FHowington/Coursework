// CS 1501 Summer 2017
// Main program for Assignment 2.  Your PHPArray class must work with this program as
// given, with no changes.  Your program output should be identical to that provided, with
// the possible exception of the raw hash table (but even this could easily match mine
// exactly).  See a LOT of comments below describing what you need in your PHPArray class
// so that it will work as specified.
import java.util.*;

public class ECTest
{
	public static void main(String [] args)
	{
		// Declare the object.  Note that the parameterized type is for the data.
		// The key will always be String (or int, at least from user's view).  There are
		// many different ways to implement the data and methods of this class, and you 
		// may have to look up some Java ideas / constructs to see how they are done.  The
		// parameter in the constructor is the initial size of the hash table.
		PHPArray<Integer> A = new PHPArray<Integer>(15);
		
		// Put some data into the PHPArray.  This should hash the data using linear
		// probing and also maintain the data in a linked list, organized by the order
		// that the data was added.  The put() method should be O(1).
		System.out.println("\tAdding some data to the PHPArray");
		A.put("Zany", new Integer(20));
		A.put("Wacky", new Integer(100));
		A.put("Hilarious", new Integer(40));
		A.put("Fun", new Integer(50));
		// Note the int keys used below.  This is a convenience for the user.  You 
		// should still store the actual keys as Strings.
		A.put(0, new Integer(1));
		A.put(1, new Integer(2));
		A.put(2, new Integer(3));
		A.put("More", new Integer(75));
		A.put("Hashing", new Integer(10));
		A.put("Craziness", new Integer(80));
		A.put("Here", new Integer(30));
		A.put(5, new Integer(4));
		A.put(6, new Integer(5));
		A.put(7, new Integer(6));
		
		// See comments below for showData
		System.out.println("\tShowing contents using Iterable interface:");
		showData(A);
		System.out.println();

		System.out.println("\nThe total of all entries is " + A.array_sum());

		Iterator test1= A.reverse_iterator();

		System.out.println("\nReversing the order of values using an iterator");
		while (test1.hasNext())
			System.out.println(test1.next());

		System.out.println("\nReversing the order of values by getting a reversed array");
		ArrayList test2 = A.array_reverse();
		for (int i=0;i<test2.size();i++) {
			System.out.println(test2.get(i));
		}


		System.out.println();
		
		// put() for an existing key should update the value for the key, but not change
		// the location in the linked list
		A.put("Wacky", new Integer(111));
		A.put("Craziness", new Integer(222));
		A.reset();

		PHPArray<String> B = new PHPArray<String>(4);
		String [] actor = {"Elwes", "Patinkin", "Wright", "Shawn", "Sarandon",
							"Andre", "Guest", "Crystal"};
		String [] role = {"Westley", "Inigo", "Buttercup", "Vizzini", "Humperdinck",
							"Fezzik", "Rugen", "Max"};
		for (int i = 0; i < actor.length; i++)
		{
			B.put(actor[i], role[i]);
		}
		PHPArray.Pair<String> currB;

		
		// Now make another PHPArray, this time of StringBuilder, which is mutable but
		// not Comparable
		PHPArray<StringBuilder> C = new PHPArray<StringBuilder>(4);
		B.reset();
		while ((currB = B.each()) != null)
		{
			C.put(currB.key, new StringBuilder(currB.value));
		}
		
		PHPArray.Pair<StringBuilder> currC;



		System.out.println();
		C.forEach((x) -> { x.append("-inconceivable!"); System.out.println(x);});

		C.rsort();
		System.out.println();
		System.out.println("\nReversing the PHPArray\n");
		while ((currC = C.each()) != null)
		{
			System.out.println("Key: " + currC.key + " Value: " + currC.value);
		}
	}
	
	// This method is using the Iterable interface to allow easy iteration through
	// the values within the PHPArray.  See the required class header for PHPArray
	// that will allow for this and also see Iterable and Iterator in the Java API.
	// Iterators are also discussed in your CS 0445 text and I have some notes on them
	// in my CS 0445 Web site:  http://www.cs.pitt.edu/~ramirez/cs445/
	public static <V> void showData(PHPArray<V> A)
	{
		for (V X: A)
		{
			System.out.println("Next item is " + X);
		}
	}
	
	// Testing the array_flip() method.  This method will transpose the keys and values
	// of the original array in a new PHPArray.  Note that this is NOT a mutator.
	// Since the key for any PHPArray object must be a String, the array_flip() method
	// will only work if value of the original PHPArray is also a String.  If the value
	// of the original array is not a String, the array_flip() method should throw a 
	// ClassCastException.  If in the original array, multiple keys had the same value,
	// in the flipped array only the last original key will be preserved as a value 
	// (since the others will be replaced).
	public static <V> void testFlip(PHPArray<V> Ar)
	{
		try
		{
			PHPArray<String> Aflip = Ar.array_flip();
			PHPArray.Pair<String> currA;
			System.out.println("\tFlipped data:");
			while ((currA = Aflip.each()) != null)
			{
				System.out.println("Key: " + currA.key + " Value: " + currA.value);
			}
			System.out.println();
		}
		catch (ClassCastException e)
		{
			System.out.println(e.toString());
			System.out.println();
		}
	}	
}