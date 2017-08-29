import javax.xml.soap.Node;
import java.util.Stack;

/**
 * Created by forbes on 5/24/17.
 */
public class DLB implements DictInterface {

    // This is the only Nodelet that the DLB maintains a link to
    Nodelet firstNode = new Nodelet('$');

    /*
    Adding occurs by moving down the tree and seeing whether each character in the string to be added is present
    // within the trie, in sequence. If a character is not found within the DLB, it is added on the correct level.
    When the end of the string is found, a '$' is added to the next level to mark the end of a word.
    */
    public boolean add(String s) {
        Nodelet currentNode = firstNode;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean foundNode = false;
            while (!foundNode) {
                if (currentNode.val == c) {
                    foundNode = true;
                    break;
                } else if (currentNode.rightSib == null) {
                    break;
                } else
                    currentNode = currentNode.rightSib;
            }
            if (!foundNode) {
                currentNode.rightSib = new Nodelet(c);
                currentNode = currentNode.rightSib;
            }
            if (currentNode.child == null) {
                if (s.length() > i + 1) {
                    currentNode.child = new Nodelet(s.charAt(i + 1));
                } else {
                    currentNode.child = new Nodelet('$');
                }
                currentNode = currentNode.child;
            } else {
                currentNode = currentNode.child;
                if (currentNode.val != '$' && s.length() == i + 1) {
                    while (currentNode.rightSib != null && currentNode.val != '$') {
                        currentNode = currentNode.rightSib;
                    }
                    if (currentNode.val != '$')
                        currentNode.rightSib = new Nodelet('$');
                }
            }
        }
        return true;
    }

    //
    public int searchPrefix(StringBuilder s) {
        int val = 0;
        Nodelet currentNode = firstNode;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            while (true) {
                if (currentNode.val == c) {
                    currentNode = currentNode.child;
                    break;
                } else if (currentNode.rightSib == null) {
                    return 0;
                } else {
                    currentNode = currentNode.rightSib;
                }
            }
        }

        //At this point, we know the child cannot possibly be null; if it was, this wouldn't be a word or a prefix.
        boolean foundNode = false;
        boolean isPrefix = false;
        boolean isWord = false;
        while (!foundNode) {
            if (currentNode.val == '$') {
                isWord = true;
            } else {
                isPrefix = true;
            }
            if (!isWord || !isPrefix)
                if (currentNode.rightSib != null) {
                    currentNode = currentNode.rightSib;
                } else {
                    foundNode = true;
                }
            else {
                foundNode = true;
            }
        }
        if (isWord)
            val += 2;
        if (isPrefix)
            val++;
        return val;
    }

    public int searchPrefix(StringBuilder s, int start, int end) {
        String temp = s.toString();
        return searchPrefix(new StringBuilder(temp.subSequence(start,end)));
    }

    private class Nodelet {
        char val;
        Nodelet child;
        Nodelet rightSib;

        private Nodelet(char c) {
            val = c;
        }
    }


    /*
    Determines if last level is a prefix (i.e, only a $ needs to be removed).
    If last level is not a prefix, determines lowest level that has 2+ elements in it, and removes element pointing to
    word to be removed. Note that remove requires input to be entire word to remove.
     */
    public boolean remove(String temp) {

        StringBuilder s = new StringBuilder(temp);
        if (searchPrefix(s) == 3) {
            Nodelet currentNode = firstNode;
            Nodelet previousNode = null;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                while (true) {
                    if (currentNode.val == c) {
                        previousNode = currentNode;
                        currentNode = currentNode.child;
                        break;
                    } else {
                        currentNode = currentNode.rightSib;
                    }
                }
            }

            boolean isFound = false;

            while (!isFound) {
                if (currentNode.val == '$') {
                    if (previousNode.child == currentNode) {
                        previousNode.child = currentNode.rightSib;
                        isFound = true;
                    } else if (currentNode.rightSib != null) {
                        previousNode.rightSib = currentNode.rightSib;
                        isFound = true;
                    } else {
                        previousNode.rightSib = null;
                        isFound = true;
                    }
                } else {
                    previousNode = currentNode;
                    currentNode = currentNode.rightSib;
                }
            }
        } else {

            //Goal here is to find the lowest level that contains two items.
            Nodelet currentNode = firstNode;
            Nodelet jumpNode = firstNode;
            char savedChar = '0';
            Nodelet savedNode = firstNode;
            Nodelet firstOnLevel = null;

            Stack<Nodelet> nodes = new Stack<>();


            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                    Boolean otherFound = false;
                    while (currentNode != null) {
                        if (currentNode.val == c) {
                            jumpNode = currentNode;
                            currentNode = currentNode.rightSib;
                        } else {
                            currentNode = currentNode.rightSib;
                            otherFound = true;
                        }
                    }
                    if(otherFound){
                        // Save previous jump node child as previous level, save character.
                        // This should save the jump node from the Previous jump, thereby preserving the level.
                        savedNode = firstOnLevel;
                        savedChar = c;
                    }
                    currentNode = jumpNode.child;
                    firstOnLevel = jumpNode;
            }

            if(savedNode.child.val == savedChar) {
                savedNode.child = savedNode.child.rightSib;
            }
            else {
                currentNode = savedNode.child;
                while (true) {
                    if(currentNode.rightSib.val == savedChar) {
                        currentNode.rightSib = currentNode.rightSib.rightSib;
                        break;
                    }
                    else
                        currentNode =currentNode.rightSib;
                }
            }

        }
        return true;
    }
}
