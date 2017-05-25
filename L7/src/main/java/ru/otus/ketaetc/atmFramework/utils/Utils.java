package ru.otus.ketaetc.atmFramework.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String prepareStatementRow(int cash) {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + ": RUB " + cash;
    }
}
