package ru.otus.dobrovolsky.myJSON.reflect;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ReflectionHelper {
    private ReflectionHelper() throws Exception {
        throw new Exception("Do not instantiate");
    }

    public static Object[] parseArray(Object object) {
        int len = Array.getLength(object);
        Object[] objects = new Object[len];
        for (int i = 0; i < len; i++) {
            objects[i] = Array.get(object, i);
        }
        return objects;
    }

    public static boolean checkSimplicity(Object object) {
        Class clazz = object.getClass();
        return clazz.isPrimitive()
                || clazz.equals(Character.class)
                || clazz.equals(Byte.class)
                || clazz.equals(Integer.class)
                || clazz.equals(Long.class)
                || clazz.equals(Double.class)
                || clazz.equals(Float.class)
                || clazz.equals(Short.class)
                || clazz.equals(String.class)
                || clazz.equals(Boolean.class)
                || clazz.equals(Object.class);
    }

    public static boolean checkComplexClass(Object object) {
        return object != null && !checkSimplicity(object) && !(object instanceof Collection || object instanceof Map || object.getClass().isArray());
    }
}
