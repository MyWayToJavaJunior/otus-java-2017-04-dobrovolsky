package ru.otus.dobrovolsky.reflect.parser.parserToString;


import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MapToStringParser implements Parser {
    private static String parseThisMap(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Object, Object> map = (Map<Object, Object>) object;
        if (map != null) {
            stringBuilder.append("{");
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                stringBuilder.append("\"").append(key).append("\"").append(":");
                stringBuilder.append("\"").append(value).append("\"").append(",");
            }
            stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());
            stringBuilder.append("}");
        }

        return stringBuilder.toString();
    }

    @Override
    public String parse(Object object, Field field) {
        String fieldType = field.getType().getName();

        try {
            Object val = field.get(object);
            switch (fieldType) {
                case "java.util.Map":
                    return "\"" + field.getName() + "\"" + ":" + parseThisMap(val);
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
