package ru.otus.dobrovolsky.reflect.parser.parserToJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MapParser implements Parser {
    @Override
    public JSONAware parse(Object object, Field field) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Object obj = null;
        StringBuilder str = new StringBuilder();
        try {
            obj = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Object entry : ((Map) obj).entrySet()) {
            Object key = ((Map.Entry) entry).getKey();
            Object value = ((Map.Entry) entry).getValue();
            str.append("\"").append(key.toString()).append("\"").append(":");
            str.append("\"").append(value.toString()).append("\"");
            jsonArray.add(str);
            str = new StringBuilder();
        }
        jsonObject.put(field.getName(), jsonArray);
        return jsonObject;
    }
}
