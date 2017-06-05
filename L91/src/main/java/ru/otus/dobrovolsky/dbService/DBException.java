package ru.otus.dobrovolsky.dbService;

import java.sql.SQLException;

public class DBException extends SQLException {
    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable throwable) {
        super(throwable);
    }
}
