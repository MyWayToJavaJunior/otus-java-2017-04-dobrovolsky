package ru.otus.dobrovolsky.sort;

import java.util.concurrent.atomic.AtomicInteger;

public class Sorter<T extends Comparable<T>> implements Runnable {
    private final T[] values;
    private final int left;
    private final int right;
    private final AtomicInteger threadCount;
    private MultithreadedSorter multithreadedSorter;

    Sorter(T[] values, int left, int right, AtomicInteger threadCount, MultithreadedSorter
            multithreadedSorter) {
        this.values = values;
        this.left = left;
        this.right = right;
        this.threadCount = threadCount;
        this.multithreadedSorter = multithreadedSorter;
    }

    @Override
    public void run() {
        sort(left, right);
        synchronized (threadCount) {
            if (threadCount.getAndDecrement() == 1)
                threadCount.notify();
        }
    }

    //quick sort
    private void sort(int start, int end) {
        if (start < end) {
            int storeIndex = partitionSort(start, end);
            if (threadCount.get() >= multithreadedSorter.getNumThreads()) {
                sort(start, storeIndex - 1);
                sort(storeIndex + 1, end);
            } else {
                threadCount.getAndAdd(2);
                multithreadedSorter.getPool().execute(new Sorter<>(values, start, storeIndex - 1, threadCount, multithreadedSorter));
                multithreadedSorter.getPool().execute(new Sorter<>(values, storeIndex + 1, end,  threadCount, multithreadedSorter));
            }
        }
    }

    private int partitionSort(int start, int end) {
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
