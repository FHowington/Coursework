
/**
 * Created by forbes on 5/24/17.
 */

public class DLB<T>{
    private Nodelet firstNode;
    public DLB (char c,T code){
        firstNode=new Nodelet(c,code);
    }

    // This is the only Nodelet that the DLB maintains a link to
    //Nodelet firstNode = new Nodelet('$', null);

    /*
    Adding occurs by moving down the tree and seeing whether each character in the string to be added is present
    // within the trie, in sequence. If a character is not found within the DLB, it is added on the correct level.
    When the end of the string is found, a '$' is added to the next level to mark the end of a word.
    */
    public boolean add(StringBuilder s, T nodeCode) {
        Nodelet currentNode = firstNode;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean foundNode = false;
            while (true) {
                if (currentNode.val == c) {
                    foundNode = true;
                    break;
                } else if (currentNode.rightSib == null) {
                    break;
                } else
                    currentNode = currentNode.rightSib;
            }
            if (!foundNode) {
                currentNode.rightSib = new Nodelet(c, nodeCode);
                currentNode = currentNode.rightSib;
            }
            if (currentNode.child == null) {
                if (s.length() > i + 1) {
                    currentNode.child = new Nodelet(s.charAt(i + 1), nodeCode);
                }
                currentNode = currentNode.child;
            } else {
                currentNode = currentNode.child;
            }
        }
        return true;
    }

    public T searchPrefix(StringBuilder s) {
        T val = null;
        Nodelet currentNode = firstNode;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            while (true) {
                if (currentNode.val == c) {
                    val=currentNode.code;
                    if(currentNode.child != null)
                        currentNode = currentNode.child;
                    else if(s.length() > i +1)
                        return null;
                    break;
                } else if (currentNode.rightSib == null) {
                    return null;
                } else {
                    currentNode = currentNode.rightSib;
                }
            }
        }
        return val;
    }

    private class Nodelet {
        char val;
        T code;
        Nodelet child;
        Nodelet rightSib;

        private Nodelet(char c, T code) {
            val = c;
            this.code = code;
        }
    }
}
