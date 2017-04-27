package ru.otus.dobrovolsky.util;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ketaetc on 27.04.17.
 */
public class Util {
    private static int youngGenCollNum = 0;
    private static int oldGenCollNum = 0;

    private static long youngGenCollTime = 0l;
    private static long oldGenCollTime = 0l;

    private String gcName;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
    LocalDateTime startTime;

    private static Util instance = new Util();

    private Util() {
    }

    /**
     * return instance of an Util
     *
     * @return instance of an Util
     */
    public static Util getInstance() {
        return instance;
    }

    /**
     * creates ArrayList instance and stats GC monitoring
     *
     * @param size
     * @throws InterruptedException
     */
    public void doWorkWithArrayList(int size) throws InterruptedException {
        startGCMon();

        List<Object> arrList = new ArrayList<>();
        int removedObjs = 0;

        int i = 0;
        while (true) {
            arrList.add(new Object());

            if ((i % 5 == 0) && (i > 0)) {
                arrList.remove(arrList.size() - 1);
                removedObjs++;
            }

            if ((i % 100000 == 0) && (i > 0)) {
                System.out.println();
                System.out.println("Added " + i + " objects to ArrayList");
                System.out.println("Removed " + removedObjs + " objects from ArrayList");
                System.out.println("Current ArrayList size: " + arrList.size());
                System.out.println();

                if ((getYoungGenCollNum() > 0) || (getOldGenCollNum() > 0)) {
                    System.out.println("Current Young GC time:    " + calcTime(getYoungGenCollTime()));
                    System.out.println("Current Young GC collections:    " + getYoungGenCollNum());
                    System.out.println("Current Old GC time:    " + calcTime(getOldGenCollTime()));
                    System.out.println("Current Old GC collections:    " + getOldGenCollNum());
                }
            }
            i++;
        }
    }

    private NotificationListener listener = getNotificationListener();

    /**
     * get startTime
     *
     * @return startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * method allow to start GC monitor
     */
    private void startGCMon() {
        startTime = LocalDateTime.now();
        startTime.format(formatter);

        System.out.println("Started at: " + startTime);
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            emitter.addNotificationListener(listener, null, null);

            System.out.println(gcbean.getName());
        }
    }

    private NotificationListener getNotificationListener() {
        return (notification, handback) -> {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                long duration = info.getGcInfo().getDuration();
                String gctype = info.getGcAction();

                gcName = info.getGcName();

                if (gctype.contains("minor")) {
                    youngGenCollNum++;
                    youngGenCollTime += duration;
                } else if (gctype.contains("major")) {
                    oldGenCollNum++;
                    oldGenCollTime += duration;
                }
            }
        };
    }

    /**
     * stops GC monitor
     */
    public void stopGCMon() {
        for (GarbageCollectorMXBean gcbean : java.lang.management.ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ((NotificationEmitter) gcbean).removeNotificationListener(listener);
            } catch (Exception e) {
            }
        }
    }

    /**
     * makes string from long ms
     *
     * @param ms
     * @return string from long ms in format: hours + ":" + minutes + ":" + seconds + " " + ms + " ms"
     */
    public String calcTime(long ms) {
        long seconds = ms / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return hours + "h:" + minutes + "m:" + seconds + "s " + "{ " + ms + " ms }";
    }

    /**
     * get youngGenCollNum
     *
     * @return youngGenCollNum
     */
    public int getYoungGenCollNum() {
        return youngGenCollNum;
    }

    /**
     * get oldGenCollNum
     *
     * @return oldGenCollNum
     */
    public int getOldGenCollNum() {
        return oldGenCollNum;
    }

    /**
     * get youngGenCollTime
     *
     * @return
     */
    public long getYoungGenCollTime() {
        return youngGenCollTime;
    }

    /**
     * get oldGenCollTime
     *
     * @return oldGenCollTime
     */
    public long getOldGenCollTime() {
        return oldGenCollTime;
    }

    /**
     * return current GC name
     *
     * @return current GC name
     */
    public String getGcName() {
        return gcName;
    }

    /**
     * prints final GC monitor info
     */
    public void printInfo() {
        System.out.println("GC name:    " + getGcName());
        System.out.println("Total GC time:  " + (getYoungGenCollTime() + getOldGenCollTime()) + " ms");
        System.out.println("Total GC collections:  " + (getYoungGenCollNum() + getOldGenCollNum()));
        System.out.println();
        System.out.println("Total Young GC time:    " + calcTime(getYoungGenCollTime()));
        System.out.println("Total Young GC collections:    " + getYoungGenCollNum());
        System.out.println("Total Old GC time:    " + calcTime(getOldGenCollTime()));
        System.out.println("Total Old GC collections:    " + getOldGenCollNum());
    }
}
