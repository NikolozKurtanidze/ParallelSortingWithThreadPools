package pgdp.threads;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class BenchmarkApproaches {
    private static final int SIZE = 100_000;

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void benchSequential() {
        final Comparable[] array = Util.randomArrayWithSeed(SIZE, 0);
        MergeSort.mergesort(array);
    }

//    @Benchmark
//    @Fork(value = 1, warmups = 1)
//    @Warmup(iterations = 3)
//    @Measurement(iterations = 5)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @BenchmarkMode(Mode.AverageTime)
//    public void benchParallel() {
//        final Comparable[] array = Util.randomArrayWithSeed(SIZE, 0);
//        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
//        forkJoinPool.invoke(new ParallelMergeSort(array));
//    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void benchParallelSmart() {
        final Comparable[] array = Util.randomArrayWithSeed(SIZE, 0);
        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        forkJoinPool.invoke(new BetterParallelMergeSort(array));
    }
}
