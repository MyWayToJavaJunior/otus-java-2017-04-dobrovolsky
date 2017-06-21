package ru.otus.dobrovolsky.cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class MyCacheEngineImpl<K, V> implements IMyCacheEngine<K, V> {
    private final int maxElements;

    private final Map<K, SoftReference<Element<V>>> cache = new ConcurrentHashMap<>();

    private int hit = 0;
    private int miss = 0;

    public MyCacheEngineImpl(int maxElements) {
        this.maxElements = maxElements;
    }

    public void put(K key, Element<V> element) {
        if (cache.size() == maxElements) {
            K firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }

        cache.put(key, new SoftReference<>(element));
    }

    public Element<V> get(K key) {
        SoftReference<Element<V>> softReference = cache.get(key);

        Element<V> element = (softReference == null ? null : softReference.get());
        if (element != null) {
            hit++;
        } else {
            miss++;
        }
        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    public void printMap() {
        for (Map.Entry<K, SoftReference<Element<V>>> entry : cache.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue().get());
        }
    }

    public void clear() {
        hit = 0;
        miss = 0;
        cache.clear();
    }
}