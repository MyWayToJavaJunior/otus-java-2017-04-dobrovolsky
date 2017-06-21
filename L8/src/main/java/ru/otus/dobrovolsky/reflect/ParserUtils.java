package ru.otus.dobrovolsky.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserUtils {
//    private static final List<String> classList = new ArrayList<>(Arrays.asList("java.lang.Integer", "java.lang.Short", "java.lang.Byte", "java.lang.String",
//            "java.lang.Character", "java.lang.Long", "java.lang.Boolean", "java.lang.Float", "java.lang.Double", "java.lang.Object"));

    private static final List<Class<?>> classList = new ArrayList<>(Arrays.asList(Integer.class, Short.class, Byte.class, String.class,
            Character.class, Long.class, Boolean.class, Float.class, Double.class));

    private ParserUtils() {
    }

    public static<T> boolean checkObjectSimplicity(Field field) {
        Class<?> fieldType = field.getClass();
        return field.getType().isPrimitive() || classList.contains(fieldType);
    }
}
