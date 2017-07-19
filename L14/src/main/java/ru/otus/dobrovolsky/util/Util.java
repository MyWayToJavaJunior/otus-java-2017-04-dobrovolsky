package ru.otus.dobrovolsky.util;

import java.util.Arrays;
import java.util.Random;

public class Util {
    public static int[] createIntArray(int size, String seed, int bound) {
        Random random = new Random(seed.hashCode());

        int[] intArr = new int[size];
        for (int i = 0; i < size; i++) {
            intArr[i] = random.nextInt(bound);
        }

        return intArr;
    }

    public static Integer[] createIntegerArray(int size, String seed, int bound) {
        Random random = new Random(seed.hashCode());

        Integer[] intArr = new Integer[size];
        for (int i = 0; i < size; i++) {
            intArr[i] = random.nextInt(bound);
        }

        return intArr;
    }

    public static String[] createStringArray(int size, String seed, int bound) {
        Random random = new Random(seed.hashCode());

        String[] stringArray = new String[size];
        for (int i = 0; i < size; i++) {
            stringArray[i] = Integer.toHexString(random.nextInt(bound));
        }

        return stringArray;
    }

    static <T> void printArray(T[] array) {
        System.out.println("Array " +  array.getClass().getComponentType().getSimpleName() + ":");
        Arrays.stream(array).forEach(num -> System.out.print(num + " "));
        System.out.println();
    }
}
