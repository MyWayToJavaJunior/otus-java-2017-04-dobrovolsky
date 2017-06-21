package ru.otus.dobrovolsky.cache;

@SuppressWarnings("unused")
public class Element<V> {
    private final V value;


    public Element(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }
}
