package ru.otus.dobrovolsky.reflect.parser.parserToString;


import ru.otus.dobrovolsky.myJson.MyJson;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;

public class ObjectToStringParser implements Parser {
    @Override
    public String parse(Object object, Field field) {
        String fieldType = field.getType().getName();

        try {
            Object val = field.get(object);
            switch (fieldType) {
                case "java.lang.Integer":
                case "java.lang.Short":
                case "java.lang.Byte":
                case "java.lang.Character":
                case "java.lang.Long":
                case "java.lang.Boolean":
                case "java.lang.Float":
                case "java.lang.Double":
                case "java.util.List":
                case "java.util.Set":
                case "java.util.Map":
                    return "\"" + field.getName() + "\"" + ":" + val;
                case "java.lang.String":
                    return "\"" + field.getName() + "\"" + ":" + "\"" + val + "\"";
                default:
                    return "\"" + field.getName() + "\"" + ":" + MyJson.toMyJsonString(val);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
