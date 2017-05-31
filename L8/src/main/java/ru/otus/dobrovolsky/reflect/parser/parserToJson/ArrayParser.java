package ru.otus.dobrovolsky.reflect.parser.parserToJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.reflect.ParserUtils;
import ru.otus.dobrovolsky.reflect.ReflectionHelperWithJSON;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class ArrayParser implements Parser {
    @Override
    public JSONAware parse(Object object, Field field) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Object val = null;
        try {
            val = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String componentsType = val.getClass().getComponentType().getName();
        for (int i = 0; i < Array.getLength(val); i++) {
            if (ParserUtils.checkObjectSimplicity(Array.get(val, i))) {
                if ((componentsType.contains("Character")) || (componentsType.contains("char"))
                        || (componentsType.contains("String")) || (componentsType.contains("[C"))) {
                    jsonArray.add(Array.get(val, i).toString());
                } else {
                    jsonArray.add(Array.get(val, i));
                }
            } else {
                Field[] fields = Array.get(val, i).getClass().getFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    jsonArray.add(ReflectionHelperWithJSON.parseFieldValue(Array.get(val, i), f));
                }
            }
        }
        jsonObject.put(field.getName(), jsonArray);
        return jsonObject;
    }
}
