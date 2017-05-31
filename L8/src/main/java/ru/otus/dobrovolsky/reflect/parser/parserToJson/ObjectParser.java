package ru.otus.dobrovolsky.reflect.parser.parserToJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.reflect.ParserUtils;
import ru.otus.dobrovolsky.reflect.ReflectionHelperWithJSON;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;

@SuppressWarnings("unchecked")
public class ObjectParser implements Parser {
    @Override
    public JSONObject parse(Object object, Field field) {
        JSONObject jsonObject = new JSONObject();
        Object val = null;
        try {
            val = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();

        JSONAware jsonAware = null;
        if (ParserUtils.checkObjectSimplicity(field)) {
            jsonArray.add(field);
        } else {
            assert val != null;
            Field[] fields = val.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    jsonAware = ReflectionHelperWithJSON.parseFieldValue(val, f);
                }
                jsonObject.put(field.getName(), jsonAware);
                return jsonObject;
            }
        }
        jsonObject.put(field.getName(), jsonArray);
        return jsonObject;
    }
}
