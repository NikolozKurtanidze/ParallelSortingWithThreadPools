package pgdp.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {
    private ForkJoinPool fjp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    private Comparable[] helper;
    private Comparable[] array;
    private int lo, hi;

    public ParallelMergeSort(Comparable[] array) {
        this(array, 0, array.length - 1);
    }

    public ParallelMergeSort(Comparable[] array, int lo, int hi){
        this.helper = new Comparable[array.length];
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }
    public void merge(int low, int middle, int high){
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
        }    }

    @Override
    protected void compute() {
        if(lo < hi){
            final int middle = (lo + hi) / 2;
            fjp.invoke(new ParallelMergeSort(array, lo, middle));
            fjp.invoke(new ParallelMergeSort(array, middle + 1, hi));
            merge(lo, middle, hi);
        }
    }
}
