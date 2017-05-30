package ru.otus.dobrovolsky.reflect.parser;

import java.lang.reflect.Field;

public interface Parser {
    Object parse(Object object, Field field);
}
