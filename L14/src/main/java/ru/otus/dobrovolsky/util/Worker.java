package ru.otus.dobrovolsky.util;

import ru.otus.dobrovolsky.sort.MultithreadedSorter;

import java.util.Arrays;

public class Worker {
    public static void doWork() {
        int size = 1_000;
        String seed = "ru.otus.dobrovolsky";
        int bound = 9_000;

        System.out.println("Sorting array of Integer with ru.otus.dobrovolsky.sort.MultithreadedSorter class");
        Integer[] intArr = Util.createIntegerArray(size, seed, bound);
        Util.printArray(intArr);
        MultithreadedSorter integerParallelSorter = new MultithreadedSorter();
        integerParallelSorter.sort(intArr);
        integerParallelSorter.shutdown();
        Util.printArray(intArr);
        System.out.println();

        System.out.println("Sorting array of Integer with Arrays.parallelSort");
        Integer[] intArr1 = Util.createIntegerArray(size, seed, bound);
        Util.printArray(intArr1);
        Arrays.parallelSort(intArr1);
        Util.printArray(intArr1);
        System.out.println();

        System.out.println("Sorting array of String with ru.otus.dobrovolsky.sort.MultithreadedSorter class");
        String[] stringArr = Util.createStringArray(size, seed, bound);
        Util.printArray(stringArr);
        MultithreadedSorter stringParallelSorter = new MultithreadedSorter();
        stringParallelSorter.sort(stringArr);
        stringParallelSorter.shutdown();
        Util.printArray(stringArr);
        System.out.println();

        System.out.println("Sorting array of String with Arrays.parallelSort");
        String[] stringArr1 = Util.createStringArray(size, seed, bound);
        Util.printArray(stringArr1);
        Arrays.parallelSort(stringArr1);
        Util.printArray(stringArr1);
    }
}
