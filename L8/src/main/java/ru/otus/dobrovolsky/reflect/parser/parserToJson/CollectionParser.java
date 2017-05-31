package ru.otus.dobrovolsky.reflect.parser.parserToJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.reflect.ParserUtils;
import ru.otus.dobrovolsky.reflect.ReflectionHelperWithJSON;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class CollectionParser implements Parser {
    @Override
    public JSONAware parse(Object object, Field field) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Object val = null;
        try {
            val = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Object obj : (Collection) val) {
            if (ParserUtils.checkObjectSimplicity(obj)) {
                jsonArray.add(obj);
            } else {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    jsonArray.add(ReflectionHelperWithJSON.parseFieldValue(obj, f));
                }
            }
        }
        jsonObject.put(field.getName(), jsonArray);
        return jsonObject;
    }

}
