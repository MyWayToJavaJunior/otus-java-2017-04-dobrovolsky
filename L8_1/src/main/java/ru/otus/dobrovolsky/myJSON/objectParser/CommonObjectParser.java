package ru.otus.dobrovolsky.myJSON.objectParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.myJSON.reflect.ReflectionHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CommonObjectParser implements ObjectParser {
    @Override
    public JSONAware parse(Object object) {
        JSONAware jsonAware = null;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (ReflectionHelper.checkSimplicity(object)) {
            jsonArray.add(object);
            jsonAware = jsonArray;
        } else if (object.getClass().isArray()) {
            jsonArray.addAll(Arrays.asList(ReflectionHelper.parseArray(object)));
            jsonAware = jsonArray;
        } else {
            if (object instanceof Collection) {
                jsonArray.addAll((Collection) object);
                jsonAware = jsonArray;
            } else if (object instanceof Map) {
                for (Object entry : ((Map) object).entrySet()) {
                    jsonObject.put(((Map.Entry) entry).getKey(), ((Map.Entry) entry).getValue());
                }
                jsonAware = jsonObject;
            }
        }
        return jsonAware;
    }
}
