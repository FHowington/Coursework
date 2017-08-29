import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class DLBTest {
    public static void main(String args[]) throws IOException{
        DLB dictionary = new DLB();
        Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
        String st;

        while (fileScan.hasNext())
        {
            st = fileScan.nextLine();
            dictionary.add(st);
        }


        System.out.println("Expect 3 for hive: " + dictionary.searchPrefix(new StringBuilder("hive")));
        System.out.println("Expect 2 for hives: " + dictionary.searchPrefix(new StringBuilder("hives")));
        System.out.println("Expect 1 for hiv: " + dictionary.searchPrefix(new StringBuilder("hiv")));
        System.out.println();
        System.out.println("Test 1: removing hive:");
        dictionary.remove("hive");
        System.out.println("Expect 1 for hive: " + dictionary.searchPrefix(new StringBuilder("hive")));
        System.out.println("Expect 2 for hives: " + dictionary.searchPrefix(new StringBuilder("hives")));
        System.out.println();
        System.out.println("Expect 2 for zoologists: " + dictionary.searchPrefix(new StringBuilder("zoologists")));
        System.out.println("Expect 3 for zoologist: " + dictionary.searchPrefix(new StringBuilder("zoologist")));
        System.out.println();
        System.out.println("Test 2 removing zoologists:");

        dictionary.remove("zoologists");
        System.out.println("Expect 0 for zoologists: " + dictionary.searchPrefix(new StringBuilder("zoologists")));
        System.out.println("Expect 2 for zoologist: " + dictionary.searchPrefix(new StringBuilder("zoologist")));
        System.out.println();
        System.out.println("Test 3:");
        System.out.println("Expect 1  for abett: " + dictionary.searchPrefix(new StringBuilder("abett")));
        System.out.println("Expect 3 for abet: " + dictionary.searchPrefix(new StringBuilder("abet")));
        System.out.println("Expect 2 for abets: " + dictionary.searchPrefix(new StringBuilder("abets")));
        System.out.println("Expect 2 for abetting: " + dictionary.searchPrefix(new StringBuilder("abetting")));
        System.out.println("Expect 2 for abetted: " + dictionary.searchPrefix(new StringBuilder("abetted")));
        System.out.println();
        System.out.println("Removed abet, abets, abetting, abetted");
        dictionary.remove("abet");
        dictionary.remove("abets");
        dictionary.remove("abetting");
        dictionary.remove("abetted");
        System.out.println("Expect 0  for abett: " + dictionary.searchPrefix(new StringBuilder("abett")));
        System.out.println("Expect 1 for abe: " + dictionary.searchPrefix(new StringBuilder("abe")));
        System.out.println("Expect 0 for abets: " + dictionary.searchPrefix(new StringBuilder("abets")));
        System.out.println("Expect 0 for abetting: " + dictionary.searchPrefix(new StringBuilder("abetting")));





















    }
}
