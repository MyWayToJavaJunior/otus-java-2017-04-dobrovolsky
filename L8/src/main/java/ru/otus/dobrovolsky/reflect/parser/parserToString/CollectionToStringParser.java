package ru.otus.dobrovolsky.reflect.parser.parserToString;


import ru.otus.dobrovolsky.reflect.ReflectionHelperWithStrings;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class CollectionToStringParser implements Parser {
    private static String parseThisCollection(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : ((Collection<Object>) object)) {
            if ((o.getClass().getName().contains("String"))
                    || (o.getClass().getName().contains("Character"))
                    || (o.getClass().getName().contains("char"))) {
                stringBuilder.append("\"").append(o).append("\",");
            } else if (!((o.getClass().getName().contains("ru.otus"))
                    && (o.getClass().isPrimitive()))) {
                stringBuilder.append("{").append(ReflectionHelperWithStrings.fieldMarshaller(o)).append("}").append(",");
            } else {
                stringBuilder.append(o).append(",");
            }
        }
        stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());

        return stringBuilder.toString();
    }

    @Override
    public String parse(Object object, Field field) {
        String fieldType = field.getType().getName();

        try {
            Object val = field.get(object);
            switch (fieldType) {
                case "java.util.List":
                case "java.util.Set":
                    return "\"" + field.getName() + "\"" + ":" + "[" + parseThisCollection(val) + "]";
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
