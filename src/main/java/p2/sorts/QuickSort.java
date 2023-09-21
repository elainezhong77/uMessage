package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, 0, array.length - 1, comparator);
    }

    private static <E> int partition(E[] array, int start, int end, Comparator<E> comparator) {
        E pivot = array[end];
        int i = start, j = end;
        while (i < j) {
            while (i < j && comparator.compare(array[i], pivot) <= 0) {
                i++;
            }
            array[j] = array[i];
            while (i < j && comparator.compare(array[j], pivot) >= 0) {
                j--;
            }
            array[i] = array[j];
        }
        array[i] = pivot;
        return i;
    }

    private static <E> void quickSort(E[] array, int start, int end, Comparator comparator) {
        if (start >= end) {
            return;
        }
        int pivotIndex = partition(array, start, end, comparator);

        quickSort(array, start, pivotIndex - 1, comparator);
        quickSort(array, pivotIndex + 1, end, comparator);
    }
}
