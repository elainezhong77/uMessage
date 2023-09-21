package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> comparator;

    public MinFourHeap(Comparator<E> c) {
        data = (E[])new Object[10];
        size = 0;
        comparator = c;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (size == data.length) {
            E[] newArray = (E[])new Object[2 * size];
            for (int i = 0; i < size; i++) {
                newArray[i] = data[i];
            }
            data = newArray;
        }
        data[size] = work;

        if (size == 0) {
            size++;
            return;
        }
        percolateUp(parentIndex(size), size);
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E top = data[0];

        data[0] = data[size - 1];
        size--;

        if (size > 0) {
            int index = firstChildIndex(0);
            percolateDown(0, minChildIndex(index));
        }
        return top;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = (E[]) new Object[10];
        size = 0;
        size = 0;
    }

    // Return parent's index of a given child
    private int parentIndex(int child) {
        return ((child - 1) / 4);
    }

    // Return nth child's index of a given parent
    private int firstChildIndex(int parent) {
        return 4 * parent + 1;
    }

    // Percolate the element up until it is less than parent
    private void percolateUp(int parent, int child) {
        while (child != 0 && comparator.compare(data[child], data[parent]) < 0) {
            E temp = data[child];
            data[child] = data[parent];
            data[parent] = temp;
            child = parent;
            parent = parentIndex(child);
        }
    }

    private void percolateDown(int parent, int child) {
        while (child >= 0 && comparator.compare(data[child], data[parent]) < 0) {
            E temp = data[child];
            data[child] = data[parent];
            data[parent] = temp;
            parent = child;
            int firstChild = firstChildIndex(parent);
            child = minChildIndex(firstChild);
        }
    }

    private int minChildIndex(int firstChild) {
        if (firstChild > size) {
            return -1;
        } else {
            int min = firstChild;
            for (int i = firstChild + 1; i < firstChild + 4; i++) {
                if (i < size && comparator.compare(data[i], data[min]) < 0) {
                    min = i;
                }
            }
            return min;
        }
    }
}
