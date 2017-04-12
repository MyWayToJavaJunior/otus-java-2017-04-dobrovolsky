package ru.otus.dobrovolsky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by ketaetc on 11.04.2017.
 */
public class SizeOf {
    private final int count = 100000;

    private static SizeOf instance = new SizeOf();

    private SizeOf() {
    }

    public static SizeOf getInstance() {
        return instance;
    }

    private int getCount() {
        return count;
    }

    private static final Runtime runtime = Runtime.getRuntime();

    public <T> void calcMem(Supplier<T> supplier) throws Exception {

        runGC();
        usedMemory();
        Object[] objs = new Object[instance.getCount()];

        long memBefore = 0;
        for (int i = -1; i < instance.getCount(); i++) {
            Object object = null;

            object = supplier.get();

            if (i >= 0) {
                objs[i] = object;
            } else {
                object = null;
                runGC();
                memBefore = usedMemory();
            }
        }

        runGC();
        long memAfter = usedMemory();

        final int size = Math.round(((float) (memAfter - memBefore)) / instance.getCount());
        System.out.println("object of " + objs[0].getClass() + " size " + size + " bytes");
        for (int i = 0; i < instance.getCount(); i++) {
            objs[i] = null;
        }
        objs = null;
    }

    private static void runGC() throws Exception {
        for (int r = 0; r < 4; ++r) {
            runGCSingle();
        }
    }

    private static void runGCSingle() throws Exception {
        long usedMem1 = usedMemory();
        long usedMem2 = Long.MAX_VALUE;

        for (int i = 0; (usedMem1 < usedMem2) && (i < instance.getCount()); i++) {
            runtime.runFinalization();
            runtime.gc();
            Thread.yield();

            usedMem2 = usedMem1;
            usedMem1 = usedMemory();
        }
    }

    public void doCalculations() throws Exception {
        this.calcMem(String::new);
        this.calcMem(() -> new String(""));
        this.calcMem(() -> new String(new byte[0]));
        this.calcMem(() -> new String("11111111111111111111111111"));
        this.calcMem(Object::new);
        this.calcMem(ArrayList<Integer>::new);
        this.calcMem(HashMap<Integer, String>::new);
        this.calcMem(() -> new byte[0]);
        this.calcMem(() -> new byte[20]);
        this.calcMem(() -> new byte[128]);
        this.calcMem(() -> new char[0]);
        this.calcMem(() -> new char[20]);
        this.calcMem(() -> new char[128]);
        this.calcMem(() -> new int[0]);
        this.calcMem(() -> new int[20]);
        this.calcMem(() -> new int[128]);
        this.calcMem(DummyClass::new);
    }

    private static long usedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
