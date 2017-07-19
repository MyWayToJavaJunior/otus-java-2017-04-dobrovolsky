package ru.otus.dobrovolsky.sort;

import java.util.concurrent.atomic.AtomicInteger;

public class QuicksortRunnable<T extends Comparable<T>> implements Runnable {
    private final T[] values;
    private final int left;
    private final int right;
    private final AtomicInteger threadCount;
    private MultithreadQuicksort multithreadQuicksort;

    QuicksortRunnable(T[] values, int left, int right, AtomicInteger threadCount, MultithreadQuicksort
            multithreadQuicksort) {
        this.values = values;
        this.left = left;
        this.right = right;
        this.threadCount = threadCount;
        this.multithreadQuicksort = multithreadQuicksort;
    }

    @Override
    public void run() {
        doSort(left, right);
        synchronized (threadCount) {
            if (threadCount.getAndDecrement() == 1)
                threadCount.notify();
        }
    }

    private void doSort(int start, int end) {
        if (start < end) {
            int storeIndex = doPartitionSort(start, end);
            if (threadCount.get() >= multithreadQuicksort.getNumThreads()) {
                doSort(start, storeIndex - 1);
                doSort(storeIndex + 1, end);
            } else {
                threadCount.getAndAdd(2);
                multithreadQuicksort.getPool().execute(new QuicksortRunnable<>(values, start, storeIndex - 1,
                        threadCount, multithreadQuicksort));
                multithreadQuicksort.getPool().execute(new QuicksortRunnable<>(values, storeIndex + 1, end,
                        threadCount, multithreadQuicksort));
            }
        }
    }

    private int doPartitionSort(int start, int end) {
        T pivotValue = values[end];
        int storeIndex = start;
        for (int i = start; i < end; i++) {
            if (values[i].compareTo(pivotValue) < 0) {
                swap(i, storeIndex);
                storeIndex++;
            }
        }
        swap(storeIndex, end);
        return storeIndex;
    }

    private void swap(int left, int right) {
        T temp = values[left];
        values[left] = values[right];
        values[right] = temp;
    }
}
