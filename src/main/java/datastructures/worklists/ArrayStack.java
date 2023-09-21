package datastructures.worklists;

import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    E[] array;
    private int size;

    public ArrayStack() {
        array = (E[])new Object[10];
        size = 0;
    }

    @Override
    public void add(E work) {
        if (size == array.length) {
            E[] newArray = (E[])new Object[2 * size];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[size] = work;
        size++;
    }

    @Override
    public E peek() {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        return array[size - 1];
    }

    @Override
    public E next() {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        size--;
        return array[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = (E[])new Object[10];
        size = 0;
    }
}
