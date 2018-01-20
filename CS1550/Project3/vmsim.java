/*
CS 1550 Project 3
Forbes Turley
University of Pittsburgh
Fall 2017
 */

// This program simulates 4 algorithms for demand paging: nru, rand, opt, and clock

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class vmsim {
    //These are the data structures used to hold the pages. The hashes are used by
    //rand and opt, while the linked nodes are used by nru and clock

    private static HashMap<Long, LinkedList<Long>> finalAccess = new HashMap<>();
    private static ArrayList<Node> accesses = new ArrayList<>();
    private static int memaccess = 0;
    private static int pageFaults = 0;
    private static int writes = 0;
    private static Node first = null;
    private static Node last = null;

    public static void main(String[] args) {
        int refresh = 0;
        String tracefile;
        int numframes;
        String algorithm;

        // Either 5 or 7 command line arguments may be used. 7 is for nru, 5 for the other algorithms
        if (args.length < 5) {
            System.out.println("Invalid command line arguments.");
            return;
        }
        if (args.length == 7) {
            numframes = Integer.parseInt(args[1]);
            tracefile = args[6];
            refresh = Integer.parseInt(args[5]);
            algorithm = args[3];
        } else if (args.length == 5) {
            numframes = Integer.parseInt(args[1]);
            tracefile = args[4];
            algorithm = args[3];
        } else {
            System.out.println("Invalid command line arguments");
            return;
        }

        //Regardless of the algorithm used, the first step is to load the entire set op page operations
        //into memory, so that they may be accessed in rapid sequence by the algorithm of choice
        load(tracefile);

        //The algorithm corresponding to the selection is now called
        if (algorithm.equals("opt"))
            opt(numframes);
        if (algorithm.equals("rand"))
            rand(numframes);

        if (algorithm.equals("clock"))
            clock(numframes);

        if (algorithm.equals("nru"))
            nru(numframes,refresh);

        //The results from the algorithm are printed
        System.out.println();
        System.out.println("Number of frames: " + numframes);
        System.out.println("Total memory accesses: " + memaccess);
        System.out.println("Total page faults: " + pageFaults);
        System.out.println("Total writes to disc: " + writes);

    }

    //This method loads the entirety of the page operations into a linked list
    private static void load(String filename) {
        BufferedReader fr;
        File file = new File(filename);
        try {
            fr = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }
        String input;
        long i = 0;
        try {
            while ((input = fr.readLine()) != null) {
                //Note that the shift right by 12 bits occurs since the pages are 4kb in size
                //Therefore, any memory operation with the same upper 20 bits will correspond to the same page
                //So we may simply eliminate the lower 12 bits and compare them to rapidly determine
                //If the two operations are on the same page in the page table
                Long address = Long.parseLong(input.substring(0, 8), 16);
                if (finalAccess.containsKey(address >> 12))
                    finalAccess.get(address >> 12).add(i);
                else {
                    finalAccess.put(address >> 12, new LinkedList<>(Collections.singletonList(i)));
                }
                accesses.add(new Node(address, input.charAt(9)));
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    //This is the method for the optimal paging algorithm
    private static void opt(int frames) {
        HashMap<Long, Boolean> table = new HashMap<>();
        ListIterator<Node> accesslist = accesses.listIterator();

        //This loop executes once for every memory operation
        while (accesslist.hasNext()) {
            memaccess++;
            Node next = accesslist.next();
            Long nextOp = next.index;
            finalAccess.get(nextOp >> 12).removeFirst();

            //If the table already contains the key, mark it as a hit, and update dirty accordingly
            if (table.containsKey(nextOp >> 12)) {
                System.out.println("Page hit on address 0x" + Long.toHexString(nextOp));
                if (next.operation == 'W')
                    table.replace(nextOp >> 12, true);
            } else if (table.size() < frames) {
                //If the table has not reached maximum capacity yet, simply grow the table
                pageFaults++;
                if (next.operation == 'W')
                    table.put(nextOp >> 12, true);
                else
                    table.put(nextOp >> 12, false);
                System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", no eviction");

            } else {
                //If we have must evict an element, do so as follows:
                //For each page, determine the time at which it will next be needed, if at all
                //This information is in the finalAccess HashMap, which contains a list of times at which
                //Each page is needed
                //Note that the linkedlists that contain this information are updated on every iteration so that the
                //first element in the linkedlist contains the very next time at which a specified page is needed
                pageFaults++;
                long max = 0;
                Long maxIndex = new Long(0);

                for (Long n : table.keySet()) {
                    if (finalAccess.get(n).isEmpty()) {
                        maxIndex = n;
                        break;
                    }
                    if (finalAccess.get(n).peekFirst() > max) {
                        max = finalAccess.get(n).peekFirst();
                        maxIndex = n;
                    }
                }

                if (table.get(maxIndex)) {
                    System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-dirty");
                    writes++;
                }
                else{
                    System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-clean");
                }

                //Update the table by removing the farthest away page and adding the new one
                table.remove(maxIndex);

                if (next.operation == 'W')
                    table.put(nextOp >> 12, true);
                else
                    table.put(nextOp >> 12, false);
            }
        }
    }

    //This is the method for random selection for demand paging
    private static void rand(int frames) {
        LinkedHashMap<Long, Boolean> table = new LinkedHashMap<>();
        ListIterator<Node> accesslist = accesses.listIterator();

        //Same as above. Repeat this loop once for every instruction
        while (accesslist.hasNext()) {
            memaccess++;
            Node next = accesslist.next();
            Long nextOp = next.index;

            if (table.containsKey(nextOp >> 12)) {
                System.out.println("Page hit on address 0x" + Long.toHexString(nextOp));
                if (next.operation == 'W')
                    table.replace(nextOp >> 12, true);
            } else if (table.size() < frames) {
                pageFaults++;
                if (next.operation == 'W')
                    table.put(nextOp >> 12, true);
                else
                    table.put(nextOp >> 12, false);
                System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", no eviction");

            } else {
                pageFaults++;
                Long maxIndex;

                //Randomly select a page to be removed
                int random = ThreadLocalRandom.current().nextInt(0, frames);
                Iterator<Long> list = table.keySet().iterator();

                //To actually remove the desired page we must iterate through the table
                for (int i = 0; i < random; i++) {
                    list.next();
                }

                maxIndex = list.next();

                //Remove the page
                if (table.get(maxIndex)) {
                    System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-dirty");
                    writes++;
                }
                else{
                    System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-clean");

                }

                table.remove(maxIndex);

                //Update new page accordingly
                if (next.operation == 'W')
                    table.put(nextOp >> 12, true);
                else
                    table.put(nextOp >> 12, false);

            }
        }
    }

    //This method implements the clock demand paging algorithm, in which we maintain an ordering in which pages
    //are referenced
    private static void clock(int frames) {
        ListIterator<Node> accesslist = accesses.listIterator();
        int numberInList = 0;

        //Again, this loop repeats once for every memory operation
        while (accesslist.hasNext()) {
            memaccess++;
            Node next = accesslist.next();
            Long nextOp = next.index;

            //Adding this head of the linked list
            if (first == null) {
                System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", no eviction");
                numberInList++;
                pageFaults++;
                first = new Node(nextOp >> 12, next.operation);

                if (first.operation == 'W')
                    first.dirty = true;
                else
                    first.dirty = false;

                first.referenced = true;
                first.nextNode = first;
                last = first;

            //Detecting a hit
            } else if (containsKey(nextOp >> 12, first)) {
                System.out.println("Page hit at address 0x" + Long.toHexString(nextOp));
                if (next.operation == 'W')
                    replace(nextOp >> 12, first, true);

            //Increasing the size of the table until it is the correct size
            } else if (numberInList < frames) {
                pageFaults++;
                numberInList++;
                if (next.operation == 'W') {
                    Node n = new Node(nextOp >> 12, next.operation);
                    last.nextNode = n;
                    n.nextNode = first;
                    n.dirty = true;
                    n.referenced = true;
                    last = n;
                } else {
                    Node n = new Node(nextOp >> 12, next.operation);
                    last.nextNode = n;
                    n.nextNode = first;
                    n.dirty = false;
                    n.referenced = true;
                    last = n;
                }
                System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", no eviction");
            //Below is the eviction strategy
            } else {
                pageFaults++;
                boolean evicted = false;
                Node current = first;
                while (!evicted) {
                    //As soon as we find an element that is unreferenced, we replace it with the new page
                    if (!current.referenced) {
                        evicted = true;
                        if (current.dirty) {
                            System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-dirty");
                            writes++;
                        }
                        else{
                            System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-clean");

                        }
                        current.index = nextOp >> 12;
                        current.referenced = true;
                        if (next.operation == 'W')
                            current.dirty = true;
                        else
                            current.dirty = false;
                        first = current.nextNode;
                        last = current;
                    //If the element was referenced, we move it to the end of the queue and marked it as unreferenced
                    //We then move the head to the next element
                    } else {
                        current.referenced = false;
                        current = current.nextNode;
                    }
                }
            }
        }
    }

    //Below is the nru algorithm for demand paging, which approximates the best page to remove using the dirty and
    //referenced bits
    private static void nru(int frames, int refresh) {
        ListIterator<Node> accesslist = accesses.listIterator();
        int numberInList = 0;
        int counter = 0;
        while (accesslist.hasNext()) {
            counter++;
            if (counter == refresh) {
                refresh(first);
                counter = 0;
            }

            memaccess++;
            Node next = accesslist.next();
            Long nextOp = next.index;

            //Initializing the linkedlist with the first element
            if (first == null) {
                System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", no eviction");

                numberInList++;
                pageFaults++;
                first = new Node(nextOp >> 12, next.operation);

                if (first.operation == 'W')
                    first.dirty = true;
                else
                    first.dirty = false;

                first.referenced = true;
                last = first;

                //Detecting a page hit
            } else if (containsKey(nextOp >> 12, first)) {
                System.out.println("Page hit at address 0x" + Long.toHexString(nextOp));
                if (next.operation == 'W')
                    replace(nextOp >> 12, first, true);
                //Growing the linked list until it is the appropriate size
            } else if (numberInList < frames) {
                pageFaults++;
                numberInList++;
                if (next.operation == 'W') {
                    Node n = new Node(nextOp >> 12, next.operation);
                    last.nextNode = n;
                    n.dirty = true;
                    n.referenced = true;
                    last = n;
                } else {
                    Node n = new Node(nextOp >> 12, next.operation);
                    last.nextNode = n;
                    n.dirty = false;
                    n.referenced = true;
                    last = n;
                }
                System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", no eviction");
                //Actual page replacement algorithm, which prioritizes replacements based on referenced and dirty bits
            } else {
                int repetition = 0;
                pageFaults++;
                Node current = first;
                while (true) {
                    //Full table is scanned until value of lowest priority level can be evicted
                    if (repetition == 0) {
                        if (!current.referenced && !current.dirty) {
                            current.index = nextOp >> 12;
                            current.referenced = true;
                            System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-clean");
                            if (next.operation == 'W')
                                current.dirty = true;
                            else
                                current.dirty = false;
                            break;

                        }
                    }

                    if (repetition == 1) {
                        if (!current.referenced && current.dirty) {
                            System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-dirty");
                            writes++;
                            current.index = nextOp >> 12;
                            current.referenced = true;
                            if (next.operation == 'W')
                                current.dirty = true;
                            else
                                current.dirty = false;
                            break;
                        }
                    }

                    if (repetition == 2) {
                        if (current.referenced && !current.dirty) {
                            System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-clean");
                            current.index = nextOp >> 12;
                            current.referenced = true;
                            if (next.operation == 'W')
                                current.dirty = true;
                            else
                                current.dirty = false;
                            break;
                        }
                    }

                    if (repetition == 3) {
                        if (current.referenced && current.dirty) {
                            System.out.println("Page fault at address 0x" + Long.toHexString(nextOp) + ", evict-dirty");
                            writes++;
                            current.index = nextOp >> 12;
                            current.referenced = true;
                            if (next.operation == 'W')
                                current.dirty = true;
                            else
                                current.dirty = false;
                            break;
                        }
                    }

                    //If we have reached the end, now scan for elements of the next priority level
                    if (current.nextNode == null) {
                        current = first;
                        repetition++;
                    } else
                        current = current.nextNode;
                }
            }
        }
    }

    //Helper method for nru and clock, which determines if the table contains a given key
    //Determines this by iterating through the table until the key is found or it reaches the end of the table
    private static boolean containsKey(Long nextOp, Node n) {
        if (n.index.equals(nextOp)) {
            n.referenced = true;
            return true;
        }
        while (true) {
            if(n.nextNode == null)
                return false;
            n = n.nextNode;
            if (n == first)
                return false;
            if (n.index.equals(nextOp)) {
                n.referenced = true;
                return true;
            }
        }
    }

    //Helper method for nru, which sets all of the referenced bits to 0 (false)
    private static void refresh(Node n) {
        if(n == null)
            return;
        n.referenced = false;
        while (true) {
            n.referenced = false;
            if (n.nextNode == null)
                return;
            n=n.nextNode;
        }
    }

    //Helper method for nru and clock, which updates the dirty bit
    private static void replace(Long nextOp, Node n, boolean val) {
        if (n.index.equals(nextOp)) {
            n.dirty = val;
            return;
        }
        while (true) {
            n = n.nextNode;
            if (n.index.equals(nextOp)) {
                n.dirty = val;
                return;
            }
        }
    }

    //Inner node class, used for generating linkedlist used in nru and clock, and contains referenced/ dirty
    //bits, as well as the page number and indicates which operation is occurring at a particular time for use in
    //the initial loading of memory operations
    static class Node {
        Long index;
        char operation;
        boolean dirty;
        boolean referenced;
        Node nextNode;

        private Node(Long i, char j) {
            this.index = i;
            this.operation = j;
        }
    }
}
