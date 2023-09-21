package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);

        for (E data : array) {
            heap.add(data);
            if (heap.size() == k + 1) {
                heap.next();
            }
        }
        int i = 0;
        while (heap.hasWork()) {
            array[i++] = heap.next();
        }
        while (i != array.length) {
            array[i++] = null;
        }
    }
}
