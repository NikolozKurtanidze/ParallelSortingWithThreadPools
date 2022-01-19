package pgdp.threads;

public class MergeSort {
    public static void mergesort(final Comparable[] array) {
        final Comparable[] helper = new Comparable[array.length];
        mergesort(array, helper, 0, array.length - 1);
    }

    public static void mergesort(final Comparable[] array, final Comparable[] helper, final int low, final int high) {
        if (low < high) {
            final int middle = (low + high) / 2;
            mergesort(array, helper, low, middle);
            mergesort(array, helper, middle + 1, high);
            merge(array, helper, low, middle, high);
        }
    }

    public static void merge(final Comparable[] array, final Comparable[] helper, final int low, final int middle, final int high) {
        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }

        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;

        while (helperLeft <= middle && helperRight <= high) {
            if (helper[helperLeft].compareTo(helper[helperRight]) <= 0) {
                array[current] = helper[helperLeft++];
            } else {
                array[current] = helper[helperRight++];
            }
            current++;
        }

        while (helperLeft <= middle) {
            array[current++] = helper[helperLeft++];
        }
    }
}
