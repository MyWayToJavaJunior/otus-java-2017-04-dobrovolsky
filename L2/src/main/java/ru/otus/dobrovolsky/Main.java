package ru.otus.dobrovolsky;

import java.lang.management.ManagementFactory;

/**
 * Created by ketaetc on 11.04.2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting pid:   " + ManagementFactory.getRuntimeMXBean().getName());

        SizeOf soInstance = SizeOf.getInstance();

        soInstance.doCalculations();
    }
}
