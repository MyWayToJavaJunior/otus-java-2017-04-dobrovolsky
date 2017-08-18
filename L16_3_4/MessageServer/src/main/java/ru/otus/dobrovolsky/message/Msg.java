package ru.otus.dobrovolsky.message;

import ru.otus.dobrovolsky.message.server.Address;

import java.util.Map;

public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";
    private final Address from;
    private final String className;
    private Address to;
    private Map<String, Object> value;

    protected Msg(Class<?> clazz, Address from, Address to, Map<String, Object> value) {
        this.className = clazz.getName();
        this.to = to;
        this.from = from;
        this.value = value;
    }

    public Address getTo() {
        return to;
    }

    public Address getFrom() {
        return from;
    }

    public Map<String, Object> getValue() {
        return value;
    }
}
