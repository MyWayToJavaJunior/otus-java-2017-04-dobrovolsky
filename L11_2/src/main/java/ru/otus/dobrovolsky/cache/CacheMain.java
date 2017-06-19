package ru.otus.dobrovolsky.cache;

public class CacheMain {


    public static void main(String[] args) throws InterruptedException {
        new CacheMain().eternalCacheExample();
        //new CacheMain().lifeCacheExample();
    }

    private void eternalCacheExample() {
        int size = 5;
        IMyCacheEngine<Integer, String> cache = new MyCacheEngineImpl<>(size, 0, 0, true);

        for (int i = 0; i < size * 2; i++) {
            cache.put(new Element<>(i, "String: " + i));
        }

        for (int i = 0; i < size * 2; i++) {
            Element<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void lifeCacheExample() throws InterruptedException {
        int size = 5;
        IMyCacheEngine<Integer, String> cache = new MyCacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new Element<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            Element<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            Element<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

}
