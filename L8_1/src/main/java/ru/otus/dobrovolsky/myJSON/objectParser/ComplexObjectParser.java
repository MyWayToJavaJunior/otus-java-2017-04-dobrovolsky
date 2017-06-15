package ru.otus.dobrovolsky.myJSON.objectParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.myJSON.reflect.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ComplexObjectParser implements ObjectParser {
    @Override
    public JSONAware parse(Object object) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue;
            try {
                fieldValue = field.get(object);
                if (ReflectionHelper.checkSimplicity(field.get(object))) {
                    parseSimpleField(object, field, jsonObject);
                } else if (field.get(object) instanceof Collection) {
                    jsonArray.addAll((Collection) field.get(object));
                    jsonObject.put(fieldName, jsonArray);
                } else if (field.get(object) instanceof Map) {
                    for (Object entry : ((Map) field.get(object)).entrySet())
                        jsonObject.put(((Map.Entry) entry).getKey(), ((Map.Entry) entry).getValue());
                    jsonObject.put(fieldName, field.get(object));
                } else if (field.get(object).getClass().isArray()) {
                    int len = Array.getLength(field.get(object));
                    for (int i = 0; i < len; i++) {
                        jsonArray.add(Array.get(field.get(object), i));
                    }
                    jsonObject.put(field.getName(), jsonArray);
                } else {
                    jsonObject.put(fieldName, fieldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private void parseSimpleField(Object object, Field field, JSONObject jsonObject) {
        Class clazz = object.getClass();
        try {
            if ((clazz.equals(Character.class)) || (clazz.equals(String.class)) || (field.getType().getName().contains("char"))) {
                jsonObject.put(field.getName(), field.get(object).toString());
            } else {
                jsonObject.put(field.getName(), field.get(object));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
