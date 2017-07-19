package ru.otus.dobrovolsky;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.sort.MultithreadQuicksort;
import ru.otus.dobrovolsky.util.Util;

import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParallelSortTest {
    private int size = 10_000;
    private String seed = "ru.otus.dobrovolsky";
    private int bound = 9_000;
    private Integer[] intArr1 = Util.createIntegerArray(size, seed, bound);
    private Integer[] intArr2 = Util.createIntegerArray(size, seed, bound);
    private String[] stringArr1 = Util.createStringArray(size, seed, bound);
    private String[] stringArr2 = Util.createStringArray(size, seed, bound);

    @Test
    public void aIntegerArraySortTest() {
        MultithreadQuicksort integerParallelSorter = new MultithreadQuicksort();
        integerParallelSorter.quicksort(intArr1);
        integerParallelSorter.shutdown();

        Arrays.parallelSort(intArr2);

        Assert.assertArrayEquals(intArr2, intArr1);
    }

    @Test
    public void bStringArraySortTest() {
        MultithreadQuicksort stringParallelSorter = new MultithreadQuicksort();
        stringParallelSorter.quicksort(stringArr1);
        stringParallelSorter.shutdown();

        Arrays.parallelSort(stringArr2);

        Assert.assertArrayEquals(stringArr2, stringArr1);
    }
}
