package ru.otus.dobrovolsky.framework.exception;

public class MyAssertionError extends Error{
    public MyAssertionError(String message) {
        super(message);
    }
}
