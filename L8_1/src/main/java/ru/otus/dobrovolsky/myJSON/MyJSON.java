package ru.otus.dobrovolsky.myJSON;

import org.json.simple.JSONAware;
import ru.otus.dobrovolsky.myJSON.objectParser.ObjectParserFactory;
import ru.otus.dobrovolsky.myJSON.reflect.ReflectionHelper;

public class MyJSON {
    private MyJSON() throws Exception {
        throw new Exception("Do not instantiate!");
    }

    public static JSONAware toJson(Object object) {
        JSONAware jsonAware;

        if (ReflectionHelper.checkComplexClass(object)) {
            jsonAware = ObjectParserFactory.getObjectParser(object).parse(object);
        } else {
            jsonAware = ObjectParserFactory.getObjectParser(object).parse(object);
        }
        return jsonAware;
    }
}
