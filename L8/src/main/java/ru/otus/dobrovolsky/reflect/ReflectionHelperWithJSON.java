package ru.otus.dobrovolsky.reflect;

import org.json.simple.JSONAware;
import ru.otus.dobrovolsky.reflect.parser.parserFactory.ParserFactory;

import java.lang.reflect.Field;
import java.util.*;

public class ReflectionHelperWithJSON implements ReflectionHelper {

    @Override
    public String fieldMarshaller(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        JSONAware jsonAware;
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                jsonAware = parseFieldValue(object, field);
                if (field.getType().getName().contains("Map")) {
                    if (jsonAware != null) {
                        String str = jsonAware.toJSONString();
                        StringBuilder strInner = new StringBuilder();
                        strInner.append(str);
                        strInner.delete(0, 1).delete(strInner.toString().length() - 1, strInner.toString().length());
                        String temp = strInner.toString();
                        temp = temp.replace("[", "{");
                        temp = temp.replace("]", "}");
                        strInner = new StringBuilder(temp);
                        stringBuilder.append(strInner).append(",");
                    }
                } else {
                    if (jsonAware != null) {
                        stringBuilder.append(jsonAware.toJSONString().substring(1, jsonAware.toJSONString().length() - 1)).append(",");
                    }
                }
            }
        }
        if (stringBuilder.toString().length() > 0) {
            stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static JSONAware parseFieldValue(Object object, Field field) {
        String fieldType = field.getType().getName();
        List<String> classList = new ArrayList<>(Arrays.asList("java.lang.Integer", "java.lang.Short", "java.lang.Byte", "java.lang.String",
                "java.lang.Character", "java.lang.Long", "java.lang.Boolean", "java.lang.Float", "java.lang.Double"));
        if (field.getType().isPrimitive() || classList.contains(fieldType)) {
            return ParserFactory.getPrimitiveParser().parse(object, field);
        } else if (field.getType().isArray()) {
            return ParserFactory.getArrayParser().parse(object, field);
        } else {
            Object obj = null;
            try {
                obj = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (obj instanceof Collection) {
                return ParserFactory.getCollectionParser().parse(object, field);
            } else if (obj instanceof Map) {
                return ParserFactory.getMapParser().parse(object, field);
            } else {
                return ParserFactory.getObjectParser().parse(object, field);
            }
        }
    }
}