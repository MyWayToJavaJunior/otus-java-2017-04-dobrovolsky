package ru.otus.dobrovolsky.atmFramework.exception;

public class NoCassettesFoundException extends Exception {
    public NoCassettesFoundException() {
        super();
    }

    public NoCassettesFoundException(String message) {
        super(message);
    }
}
