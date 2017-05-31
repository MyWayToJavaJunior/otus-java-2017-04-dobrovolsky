package ru.otus.dobrovolsky.reflect.parser.parserToJson;

import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.reflect.parser.Parser;

import java.lang.reflect.Field;

public class PrimitiveParser implements Parser {
    @Override
    public JSONObject parse(Object object, Field field) {
        JSONObject jsonObject = new JSONObject();
        try {
            if ((field.getType().getName().contains("Character")) || (field.getType().getName().contains("char"))
                    || (field.getType().getName().contains("String"))) {
                jsonObject.put(field.getName(), field.get(object).toString());
            } else {
                jsonObject.put(field.getName(), field.get(object));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
