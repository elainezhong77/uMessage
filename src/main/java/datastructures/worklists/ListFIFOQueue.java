package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private Node<E> head;
    private Node<E> end;
    private int size;

    private class Node<E> {
        private E data;
        private Node next;

        private Node(E value) {
            data = value;
            next = null;
        }
    }

    public ListFIFOQueue() {
        head = null;
        end = null;
        size = 0;
    }

    @Override
    public void add(E work) {
        Node n = new Node(work);
        if (end == null) {
            head = n;
            end = n;
        } else {
            end.next = n;
            end = n;
        }
        size++;
    }

    @Override
    public E peek() {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        return head.data;
    }

    @Override
    public E next() {
        if (hasWork() == false) {
            throw new NoSuchElementException();
        }
        E ret = head.data;
        if (size == 1) {
            head = null;
            end = null;
        } else {
            head = head.next;
        }
        size--;
        return ret;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        end = null;
        size = 0;
    }
}
