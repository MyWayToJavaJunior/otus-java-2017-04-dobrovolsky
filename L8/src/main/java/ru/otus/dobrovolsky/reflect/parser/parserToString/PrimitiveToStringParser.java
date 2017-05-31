package ru.otus.dobrovolsky.reflect.parser.parserToString;

import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;

public class PrimitiveToStringParser implements Parser {
    @Override
    public String parse(Object object, Field field) {
        String fieldType = field.getType().getName();
        Object val;

        try {
            switch (fieldType) {
                case "byte":
                    val = field.getByte(object);
                    return retString(field, val);
                case "short":
                    val = field.getShort(object);
                    return retString(field, val);
                case "int":
                    val = field.getInt(object);
                    return retString(field, val);
                case "long":
                    val = field.getLong(object);
                    return retString(field, val);
                case "float":
                    val = field.getFloat(object);
                    return retString(field, val);
                case "double":
                    val = field.getDouble(object);
                    return retString(field, val);
                case "boolean":
                    val = field.getBoolean(object);
                    return retString(field, val);
                case "char":
                    val = field.getChar(object);
                    return retString(field, val);
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String retString(Field field, Object value) {
        if (field.getType().getName().contains("char")) {
            return "\"" + field.getName() + "\"" + ":" + "\"" + value + "\"";
        } else {
            return "\"" + field.getName() + "\"" + ":" + value;
        }
    }
}
