package ru.otus.dobrovolsky.dbService;

import java.sql.SQLException;

public class DBException extends SQLException {
    DBException(Throwable throwable) {
        super(throwable);
    }
}
