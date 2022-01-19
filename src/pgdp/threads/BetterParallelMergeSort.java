package pgdp.threads;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BetterParallelMergeSort extends RecursiveAction {
    private final int THRESHOLD;
    private final ForkJoinPool fjp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    private Comparable[] helper;
    private Comparable[] array;
    private final int lo, hi;


    public BetterParallelMergeSort(Comparable[] array) {
        this(array, 0, array.length - 1);
    }
    public BetterParallelMergeSort(Comparable[] array, int lo, int hi){
        THRESHOLD = 12_500;
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
        if(hi - lo < THRESHOLD){
            MergeSort.mergesort(array, new Comparable[array.length], lo, hi);
        }
        else {
            final int middle = (lo + hi) / 2;
            fjp.invoke(new BetterParallelMergeSort(array, lo, middle));
            fjp.invoke(new BetterParallelMergeSort(array, middle + 1, hi));
            merge(lo, middle, hi);
        }
    }
}
