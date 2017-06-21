package ru.otus.dobrovolsky.cache;

public interface IMyCacheEngine<K, V> {

    void put(K key, Element<V> element);

    Element<V> get(K key);

    int getHitCount();

    int getMissCount();
}
