package ru.otus.dobrovolsky.reflect.parser.parserToString;

import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class ArrayToStringParser implements Parser {
    private static String parseThisArr(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = Array.getLength(object);
        for (int i = 0; i < length; i++) {
            if ((object.getClass().getComponentType().getName().contains("String"))
                    || (object.getClass().getComponentType().getName().contains("Character"))
                    || (object.getClass().getComponentType().getName().contains("char"))) {
                stringBuilder.append("\"").append(Array.get(object, i)).append("\",");
            } else {
                stringBuilder.append(Array.get(object, i)).append(",");
            }
        }
        stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());

        return stringBuilder.toString();
    }

    @Override
    public String parse(Object object, Field field) {
        String fieldType = field.getType().getComponentType().getName();

        try {
            Object val = field.get(object);
            String valName = field.getName();
            switch (fieldType) {
                case "java.lang.Integer":
                case "java.lang.Short":
                case "java.lang.Byte":
                case "java.lang.String":
                case "java.lang.Character":
                case "java.lang.Long":
                case "java.lang.Boolean":
                case "java.lang.Float":
                case "java.lang.Double":
                case "byte":
                case "short":
                case "int":
                case "long":
                case "float":
                case "double":
                case "boolean":
                case "char":
                    return "\"" + valName + "\"" + ":" + "[" + parseThisArr(val) + "]";
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
