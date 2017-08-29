import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by forbes on 5/20/17.
 */

public class Anagram {
    static DictInterface dictionary;
    static HashSet<String> solutions;

    public static void main(String args[]) {


        //If the command line arg 2 is dlb, use DLB as the dictionary type. Otherwise use MyDictionary
        if(args[2].equals("dlb"))
            dictionary = new DLB();
        else
            dictionary = new MyDictionary();


        loadDictionary(dictionary);
        try {
            PrintWriter writer = new PrintWriter(args[1], "UTF-8");
            try {
                String s;
                BufferedReader infile = new BufferedReader(new FileReader(args[0]));

                // Reads in each line from the input file, and runs tryString on them. Outputs results to file
                // then proceeds to work on next line of file until empty. Note that this means results are printed
                // to file while it is still working if multiple lines are in input text file.
                while ((s = infile.readLine()) != null) {
                    solutions = new HashSet<>();

                    tryString(new StringBuilder(), new StringBuilder(s.replace(" ", "")), new StringBuilder());

                    int length = s.replace(" ","").length();

                    Iterator<String> iter;
                    writer.println("Here are the results for " + s + ":");
                    ArrayList<String> solArray = new ArrayList<>(solutions);
                    Collections.sort(solArray);

                    while (!solArray.isEmpty()) {
                        boolean printedLength = false;
                        iter = solArray.iterator();
                        String y;
                        while (iter.hasNext()) {
                            y = iter.next();
                            if (y.length() == length) {
                                if(!printedLength) {
                                    writer.println((length - s.replace(" ","").length() + 1) + " word solutions:");
                                    printedLength = true;
                                }
                                writer.println(y);
                                iter.remove();
                            }
                        }
                        length++;
                    }

                    writer.println();
                }
            } catch (Exception e) {
            }
            writer.close();
        } catch (IOException e) {
        }
    }


    // Adds the entire dictionary to either the DLB or MyDictionary
    public static void loadDictionary(DictInterface dictionary) {
        try {
            String s;
            BufferedReader infile = new BufferedReader(new FileReader("dictionary.txt"));
            while ((s = infile.readLine()) != null) {
                dictionary.add(s);
            }

        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }

    // This method recursively builds string out of the input and tests them using the searchPrefix method
    public static void tryString(StringBuilder s, StringBuilder input, StringBuilder result) {
        if (input.length() == 0) {
            solutions.add(result.toString());
        } else {

            for (int i = 0; i < input.length(); i++) {

                // Tries creating each string made from adding a character to the current string
                s.append(input.charAt(i));
                input.deleteCharAt(i);

                // If the new string is a prefix, call tryString again with this string
                if (dictionary.searchPrefix(s) == 1 && input.length() > 0) {
                    tryString(s, input, result);

                    // If the current string is all of the characters available and it is a full word or prefix,
                    // add the current string to the result strings and recurse. Then restore the result to original state.
                } else if ((dictionary.searchPrefix(s) == 2 || dictionary.searchPrefix(s) == 3) && input.length() == 0) {
                    result.append(s);
                    tryString(s, input, result);
                    result.delete(result.length() - s.length(), result.length());

                    // If the string is a full word only, appent it to the result string with a space and cal
                    // tryString again. Then restore the result string
                } else if (dictionary.searchPrefix(s) == 2 && input.length() > 0) {
                    result.append(s + " ");
                    tryString(new StringBuilder(), input, result);
                    result.delete(result.length() - s.length() - 1, result.length());

                    // If the string is a full word and a prefix and characters remain, call trystring both with the
                    // current string added to the result string and without it added. This allow for the current string
                    // to be attemted as both a prefix and a full word
                } else if (dictionary.searchPrefix(s) == 3 && input.length() > 0) {
                    tryString(s, input, result);
                    tryString(new StringBuilder(), input, result.append(s + " "));
                    result.delete(result.length() - s.length() - 1, result.length());
                }

                // Restore the input list of characters and the current string to the state they were in prior
                // to the concatentation that took place at the top of this method.
                input.insert(i, s.charAt(s.length() - 1));
                s.deleteCharAt(s.length() - 1);
            }
        }
    }
}