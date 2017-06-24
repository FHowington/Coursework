package cs445.a1;

import java.lang.reflect.Array;
import java.util.Arrays;


@SuppressWarnings("unchecked")
public class Set<E> implements SetInterface<E>{
    private E[] contents;
    private static final int DEFAULT_CAPACITY = 100;
    private int size=0;

    public Set(){
        this(DEFAULT_CAPACITY);
    }

    public Set(int initialSize){
        contents=(E[]) new Object[initialSize];
    }

    public int getCurrentSize(){
        return size;
    }

    public boolean isEmpty(){
        return size<1;
    }

    private boolean isFull(){
        return contents.length-size<1;
    }


    //Should setFullException be utilized? It causes an error when add is used in Profile if present. Why?
    public boolean add(E newEntry) throws IllegalArgumentException {
        boolean result=true;
        if(newEntry==null) {
            throw new IllegalArgumentException("Error adding entry: Entry must not be null value");
        }

        for(int i=0; i<contents.length;i++)
            if(newEntry.equals(contents[i]))
                result=false;

        if(isFull() && result)
            contents = Arrays.copyOf(contents, contents.length * 2);

        if(result)
            contents[size++] = newEntry;

        return result;
    }

    public boolean remove(E entry) throws IllegalArgumentException{
        boolean result=false;
        int index=0;
        if(entry==null) {
            throw new IllegalArgumentException("Error removing entry: Entry must not be null value");
        }
        while((index < size) && !result) {
            if (contents[index].equals(entry)) {
                result = true;
                contents[index] = contents[size-1];
                contents[size-1] = null;
                size--;
            }
            index ++;
        }
        return result;
    }

    public E remove(){
        E result;
        if(size==0)
            result=null;
        else{
            result=contents[size-1];
            contents[size-1]=null;
            size--;
        }
        return result;
    }



    public void clear(){
        if(size!=0) {
            contents = (E[]) new Object[DEFAULT_CAPACITY];
            size=0;
        }
    }

    public boolean contains(E entry) throws IllegalArgumentException{
        boolean result=false;
        int index=0;
        if(entry == null)
            throw new IllegalArgumentException("Error searching for value with contains(): Cannot search for null");

        while((index < size) && !result) {
            if (contents[index].equals(entry)) {
                result = true;
            }
            index++;
        }
        return result;
    }


    public E[] toArray() {
        E[] result=(E[])new Object[size];
        for(int ii=0;ii<size;ii++)
            result[ii]=contents[ii];
        return result;
    }

}
