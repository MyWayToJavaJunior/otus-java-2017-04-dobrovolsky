package ru.otus.dobrovolsky.benchmark;

import ru.otus.dobrovolsky.util.GCMonitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ketaetc on 27.04.17.
 */
public class Benchmark implements BenchmarkMBean {
    private int size = 0;

    GCMonitor gcMon = new GCMonitor();

    /**
     * runs benchmark
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        try {
            gcMon.doWorkWithArrayList(size);
        } catch (InterruptedException ie) {
        } catch (OutOfMemoryError ooe) {
            gcMon.printInfo();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
            LocalDateTime stopTime = LocalDateTime.now();
            stopTime.format(formatter);
            System.out.println("Stopped at: " + stopTime);
            Duration duration = Duration.between(gcMon.getStartTime().toLocalTime(), stopTime.toLocalTime());
            System.out.println("Time spent: " + duration.getSeconds() / 3600 + ":" + duration.getSeconds() / 60 + ":" + duration.getSeconds());
        } finally {
            gcMon.stopGCMon();
        }
    }

    /**
     * return size of ArrayList creating for benchmark
     *
     * @return size
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * set size of ArrayList creating for benchmark
     *
     * @param size
     */
    @Override
    public void setSize(int size) {
        this.size = size;
    }
}