package ru.otus.dobrovolsky.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Created by ketaetc on 19.04.17.
 */
public class Util {

    private static List<Integer> arrayList;
    private static List<Integer> arrayList2;

    public static <T> void start(List<T> list) throws Exception {
        arrayList = list.getClass().newInstance();
        arrayList2 = list.getClass().newInstance();

        String className = list.getClass().getName();
        doCalculations(className);
    }

    public static void doCalculations(String className) {
        System.out.println(className + " - Starting operations");

        calcTime(className + " - Adding objects", (o) -> {
            IntStream.iterate(0, i -> i + 1).limit(10000).forEach(arrayList::add);
        });

        calcTime(className + " - Getting objects", (o) -> {
            IntStream.iterate(0, i -> i + 1).limit(arrayList.size()).forEach(arrayList::get);
        });

        calcTime(className + " - Adding 1 more object", (o) -> {
            arrayList.add(new Random().nextInt());
        });

        calcTime(className + " - Getting objects 1 more time", (o) -> {
            IntStream.iterate(0, i -> i + 1).limit(arrayList.size()).forEach(arrayList::get);
        });

        calcTime(className + " - Adding list of values into", (o) -> {
            Collections.addAll(arrayList, 111, 222, 333, 555);
        });

        calcTime(className + " - Getting objects from instance again", (o) -> {
            IntStream.iterate(0, i -> i + 1).limit(arrayList.size()).forEach(arrayList::get);
        });

        calcTime(className + " - Adding objects to another instance", (o) -> {
            IntStream.iterate(0, i -> i + 1).limit(100).forEach(arrayList2::add);
        });

        calcTime(className + " - Copying instance to another instance", (o) -> {
            Collections.copy(arrayList, arrayList2);
        });

        calcTime(className + " - Sorting instance", (o) -> {
            Collections.sort(arrayList);
        });

        calcTime(className + " - Sorting instance with naturalOrder comparator", (o) -> {
            Collections.sort(arrayList, Comparator.naturalOrder());
        });

        calcTime(className + " - Sorting instance with reverseOrder comparator", (o) -> {
            Collections.sort(arrayList, Collections.reverseOrder());
        });
    }

    private static <T> void calcTime(String title, Consumer<T> consumer) {
        System.out.println(title);
        long start;
        long end;

        start = System.nanoTime();
        consumer.accept((T) arrayList);
        end = System.nanoTime();

        System.out.println("Spent time:   " + (end - start) / 1000  + " mcs");
        System.out.println("========================================================================================");
    }
}
