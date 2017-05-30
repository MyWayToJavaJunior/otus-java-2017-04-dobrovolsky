package ru.otus.dobrovolsky.reflect.parser;

import java.lang.reflect.Field;

public class PrimitiveParser implements Parser {
    @Override
    public String parse(Object object, Field field) {
        String fieldType = field.getType().getName();
        Object val;

        try {
            switch (fieldType) {
                case "byte":
                    val = field.getByte(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "short":
                    val = field.getShort(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "int":
                    val = field.getInt(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "long":
                    val = field.getLong(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "float":
                    val = field.getFloat(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "double":
                    val = field.getDouble(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "boolean":
                    val = field.getBoolean(object);
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "char":
                    val = field.getChar(object);
                    return "\"" + field.getName() + "\"" + ":" + "\"" + val + "\"";
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
