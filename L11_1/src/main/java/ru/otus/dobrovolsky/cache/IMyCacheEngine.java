package ru.otus.dobrovolsky.cache;

public interface IMyCacheEngine<K, V> {

    void put(Element<K, V> element);

    Element<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
