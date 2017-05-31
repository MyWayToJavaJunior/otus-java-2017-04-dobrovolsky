package ru.otus.dobrovolsky.reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ketaetc on 31.05.17.
 */
public class ParserUtils {
    private static final List<String> classList = new ArrayList<>(Arrays.asList("java.lang.Integer", "java.lang.Short", "java.lang.Byte", "java.lang.String",
            "java.lang.Character", "java.lang.Long", "java.lang.Boolean", "java.lang.Float", "java.lang.Double", "java.lang.Object"));

    private ParserUtils() {
    }

    public static boolean checkObjectSimplicity(Object object) {
        String fieldType = object.getClass().getName();
        if (object.getClass().isPrimitive() || classList.contains(fieldType)) {
            return true;
        }
        return false;
    }
}
