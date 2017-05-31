package ru.otus.dobrovolsky.reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserUtils {
    private static final List<String> classList = new ArrayList<>(Arrays.asList("java.lang.Integer", "java.lang.Short", "java.lang.Byte", "java.lang.String",
            "java.lang.Character", "java.lang.Long", "java.lang.Boolean", "java.lang.Float", "java.lang.Double", "java.lang.Object"));

    private ParserUtils() {
    }

    public static boolean checkObjectSimplicity(Object object) {
        String fieldType = object.getClass().getName();
        return object.getClass().isPrimitive() || classList.contains(fieldType);
    }
}
