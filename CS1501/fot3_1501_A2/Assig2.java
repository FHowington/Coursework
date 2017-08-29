// CS 1501 Summer 2017
// Main program for Assignment 2.  Your PHPArray class must work with this program as
// given, with no changes.  Your program output should be identical to that provided, with
// the possible exception of the raw hash table (but even this could easily match mine
// exactly).  See a LOT of comments below describing what you need in your PHPArray class
// so that it will work as specified.
import java.util.*;

public class Assig2
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
		
		// The Pair<V> class (which you must write) should be a public static inner class
		// -- this allows it to be nested within PHPArray but it can still be publicly 
		// accessed as we are doing below.  Pair<V> has two public instance variables:  
		// 		key of type String and
		// 		value of type V 
		// (i.e. an arbitrary parameterized type).  The idea of this type is
		// that it allows the user to extract the (key, value) pairs from the PHPArray
		// in a clearly defined way that still encapsulates the type within PHPArray.
		// The each() method should return the next (key, value) pair within a new Pair
		// object until the end of the list is reached -- at which time it should return
		// null.  The reset() method will re-initialize the iteration such that each()
		// will again go through the (key, value) pairs of the PHPArray.
		System.out.println("\tShowing contents using each() method:");
		PHPArray.Pair<Integer> curr;
		while ((curr = A.each()) != null)
		{
			System.out.println("Key: " + curr.key + " Value: " + curr.value);
		}
		System.out.println();
		
		// Return an ArrayList<String> of the keys, in order by the linked list
		System.out.println("The keys are:");
		ArrayList<String> kees = A.keys();
		for (String s: kees)
			System.out.print(s + " ");
		System.out.println("\n");
		
		// Return an ArrayList<V> of the values, in order by the linked list
		System.out.println("The values are:");
		ArrayList<Integer> vals = A.values();
		for (Integer x: vals)
			System.out.print(x + " ");
		System.out.println("\n");
		
		// The showTable() method is solely for the purpose of grading.  Using this we can
		// see if your table is set up correctly and hashing values.  Your showTable() output
		// may not exactly match mine (depending upon how exactly you implement your
		// linear probing table) but this method should show the indices and contents of
		// the data in your hash table.
		A.showTable();
		System.out.println();
		
		// Let's try some gets to see how they work.  Note again the possibility of the
		// int key by the user -- this should be converted into a String key to actually
		// access the data.
		System.out.println("\tLooking up some keys:");
		Integer val;
		val = A.get("Hashing");
		if (val != null)
			System.out.println("Key: Hashing has value " + val);
		val = A.get(2);
		if (val != null)
			System.out.println("Key: 2 has value " + val);
		val = A.get("Weasel");
		if (val == null)
			System.out.println("Key: Weasel is not found");
		System.out.println();
		
		// Accessing the PHPArray using a for loop.  Note that the items that used int
		// keys are shown as expected but the items that used String keys are not shown
		// since the keys do not match.  The loop iterates the correct number of times
		// since we are using the length() method to determine the number of iterations.
		// However, since we are using only ints for the keys most of the actual data in
		// the PHPArray is not found.
		System.out.println("\tIterating using traditional for loop");
		for (int i = 0; i < A.length(); i++)
		{
			System.out.println("A[" + i + "] = " + A.get(i));
		}
		System.out.println();
		
		// Unsetting (deleting) some items.  This should remove the data from both the
		// hash and the list and should have O(1) run-time -- make sure that it is O(1)
		// or you will lose points!  Also be sure to handle collisions -- see the author's
		// LinearProbingHashST class for one way of handling the hash part of the delete.
		// To facilitate grading, output a line indicating each key that you must rehash
		// during a delete -- see the sample output for an example.
		System.out.println("\tDeleting keys 'Hashing', 'Zany' and 2");
		A.unset("Hashing");
		A.unset("Zany");
		A.unset(2);
		A.reset();
		while ((curr = A.each()) != null)
		{
			System.out.println("Key: " + curr.key + " Value: " + curr.value);
		}
		System.out.println();
		if ((A.get("Hashing") == null))
			System.out.println("Hashing is not found");
		if ((A.get("Zany") == null))
			System.out.println("Zany is not found");
		if ((A.get(2) == null))
			System.out.println("2 is not found\n");
		
		A.showTable();
		System.out.println();
		
		// put() for an existing key should update the value for the key, but not change
		// the location in the linked list
		A.put("Wacky", new Integer(111));
		A.put("Craziness", new Integer(222));
		A.reset();
		System.out.println("\tDemonstrating put() to replace value for existing key");
		System.out.println("\tUpdating keys 'Wacky' and 'Craziness' with new values");
		while ((curr = A.each()) != null)
		{
			System.out.println("Key: " + curr.key + " Value: " + curr.value);
		}
		System.out.println();
		
		// The regular sort method should do the following:
		// 		- Sort the values using the Comparable interface (if the data is not
		//			Comparable you should throw an exception when this method is tried)
		//		- Assign new keys to the values starting at 0 and ending a length()-1
		//		- Have the linked access also be the sorted result (i.e. an iterator()
		//			should iterate in sorted order)
		// The sort should be efficient (O(NlgN)) but having some extra overhead
		// is ok.
		System.out.println("\tSorting the data");
		A.sort();
		A.reset();  // Set each() iteration variable back to beginning
		while ((curr = A.each()) != null)
		{
			System.out.println("Key: " + curr.key + " Value: " + curr.value);
		}
		System.out.println();
		
		// All items should show in this loop now due to the reassigning of the keys
		// during the sort.
		System.out.println("\tPrinting out via int indices:");
		for (int i = 0; i < A.length(); i++)
		{
			System.out.println("A[" + i + "] = " + A.get(i));
		}
		System.out.println();
		
		// Parameterized type should work with a different base type
		System.out.println("\tUsing a different base type");
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
		while ((currB = B.each()) != null)
		{
			System.out.println("Key: " + currB.key + " Value: " + currB.value);
		}
		System.out.println();
		
		// The asort() method will sort the values just like sort(), but instead of
		// reassigning the keys to ints starting at 0, this will keep the keys as they
		// were.  Be careful about doing this in an efficient way.
		System.out.println("\tSorting with preservation of keys");
		B.asort();
		B.reset();
		while ((currB = B.each()) != null)
		{
			System.out.println("Key: " + currB.key + " Value: " + currB.value);
		}
		System.out.println();
		System.out.println("Note that hash table order is still pseudo-random:");
		B.showTable();
		System.out.println();
		System.out.println("\tShowing values only:");
		showData(B);
		System.out.println();
		
		// Now make another PHPArray, this time of StringBuilder, which is mutable but
		// not Comparable
		PHPArray<StringBuilder> C = new PHPArray<StringBuilder>(4);
		B.reset();
		while ((currB = B.each()) != null)
		{
			C.put(currB.key, new StringBuilder(currB.value));
		}
		
		System.out.println("PHPArray of <String, StringBuilder>");
		PHPArray.Pair<StringBuilder> currC;
		while ((currC = C.each()) != null)
		{
			System.out.println("Key: " + currC.key + " Value: " + currC.value);
		}	
		System.out.println();
		System.out.println("Trying to sort PHPArray of StringBuilder");
		try
		{
			C.sort();
		}
		catch (ClassCastException e)
		{
			System.out.println("PHPArray values are not Comparable -- cannot be sorted");
		}
		System.out.println();
		
		System.out.println("\tUsing iterator manually:");
		Iterator<String> iter = B.iterator();
		while (iter.hasNext())
		{
			System.out.println("Next item is " + iter.next());
		}
		System.out.println();
		
		// First add some additional items to B, with the same value
		System.out.println("\tAdding some keys with the same values");
		B.put("Keaton", "Batman");
		B.put("Kilmer", "Batman");
		B.put("Bale", "Batman");
		System.out.println();
		B.reset();
		System.out.println("\tTesting the array_flip() method, transposing keys and values");
		System.out.println("\tOriginal data:");
		while ((currB = B.each()) != null)
		{
			System.out.println("Key: " + currB.key + " Value: " + currB.value);
		}
		System.out.println();
		testFlip(B);  // See comments below
		System.out.println("\tNon-flippable array");
		testFlip(A);
		
		// forEach method to perform an action using lambda expressions in Java.  The forEach
		// method is a default method in the Java Iterable interface and should work without
		// redefining as long as you define the Iterator for PHPArray correctly.  This is
		// actually really cool and there are a lot of different variations to these.  Look
		// up "Java lamba expressions" and "Java 8 Iterable" for more information.  This 
		// method is (somewhat) analogous to the PHP array_map() method.
		System.out.println("\tDemonstrating forEach construct");
		A.forEach((x) -> { int ans = x.intValue()*x.intValue(); System.out.println(x + "^2 = " + ans);});
		System.out.println();
		B.forEach((x) -> { if (x.indexOf("er") >= 0) System.out.println(x + " contains er");});	
		System.out.println();
		B.forEach((x) -> { if (x.indexOf("in") >= 0) System.out.println(x + " contains in");});	
		System.out.println();
		C.forEach((x) -> { x.append("-inconceivable!"); System.out.println(x);});
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