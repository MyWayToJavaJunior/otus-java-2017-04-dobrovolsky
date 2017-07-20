package ru.otus.dobrovolsky.sort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadedSorter {
    private final int NUM_THREADS = 4;
    private ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

    public <T extends Comparable<T>> void sort(T[] input) {
        final AtomicInteger threadCount = new AtomicInteger(1);
        pool.execute(new Sorter<>(input, 0, input.length - 1, threadCount, this));
        try {
            synchronized (threadCount) {
                threadCount.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        pool.shutdown();
    }

    int getNumThreads() {
        return NUM_THREADS;
    }

    ExecutorService getPool() {
        return pool;
    }
}
