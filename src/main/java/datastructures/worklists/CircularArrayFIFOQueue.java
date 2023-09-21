package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int front;
    private int back;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        array = (E[])new Comparable[capacity];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        array[back] = work;
        back = (back + 1) % capacity();
        size++;
    }

    @Override
    public E peek() {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        return array[front];
    }

    @Override
    public E peek(int i) {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        else if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return array[(front + i) % array.length];
    }

    @Override
    public E next() {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        E x = array[front];
        front = (front + 1) % capacity();
        size--;
        return x;
    }

    @Override
    public void update(int i, E value) {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        else if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        array[front + i] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = (E[])new Comparable[capacity()];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int minSize = Math.min(this.size(), other.size());

        for (int i = 0; i < minSize; i++) {
            int flag = this.peek(i).compareTo(other.peek(i));
            if (flag != 0)
                return flag;
        }

        //The first minSize elements are matched, so return the result based on the sizes
        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            // Your code goes here
            return (this.compareTo(other) == 0);
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int hashCode = 0;
        for (int i = 0; i < size; i++) {
            // hashCode = 31 * hashCode + (peek(i) != null ? peek(i).hashCode() : 0);
            hashCode = 31 * hashCode + array[(front + i) % array.length].hashCode();
        }
        return hashCode;
    }
}
