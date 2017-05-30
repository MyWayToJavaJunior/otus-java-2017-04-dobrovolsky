package ru.otus.dobrovolsky.reflect;

import ru.otus.dobrovolsky.reflect.parser.ParserFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static String fieldMarshaller(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                stringBuilder.append(parseFieldValue(object, field)).append(",");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());
            return stringBuilder.toString();
        }
        return null;
    }

    private static String parseFieldValue(Object object, Field field) {
        if (field.getType().isPrimitive()) {
            return ParserFactory.getPrimitiveParser().parse(object, field);
        }
        if (field.getType().isArray()) {
            return ParserFactory.getArrayParser().parse(object, field);
        }
        Object obj = null;
        try {
            obj = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (obj instanceof Collection) {
            return ParserFactory.getCollectionParser().parse(object, field);
        }
        if (obj instanceof Map) {
            return ParserFactory.getMapParser().parse(object, field);
        }
        return ParserFactory.getObjectParser().parse(object, field);
    }
}