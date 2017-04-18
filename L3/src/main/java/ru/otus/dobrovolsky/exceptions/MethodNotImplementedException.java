package ru.otus.dobrovolsky.exceptions;

/**
 * Created by ketaetc on 16.04.17.
 */
public class MethodNotImplementedException extends RuntimeException {
    public MethodNotImplementedException() {
        super("This method not implemented!");
    }
}
