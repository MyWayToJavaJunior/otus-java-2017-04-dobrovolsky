package ru.otus.dobrovolsky.myJson;

import ru.otus.dobrovolsky.reflect.ReflectionHelper;
import ru.otus.dobrovolsky.reflect.ReflectionHelperWithJSON;
import ru.otus.dobrovolsky.reflect.ReflectionHelperWithStrings;

public class MyJson {
    private static ReflectionHelper reflectionHelper;

    public static String toMyJsonString(Object object) {
        reflectionHelper = new ReflectionHelperWithStrings();
        String string = reflectionHelper.fieldMarshaller(object);
        if (string != null) {
            return "{" + string + "}";
        }
        return null;
    }

    public static String toMyJson(Object object) {
        reflectionHelper = new ReflectionHelperWithJSON();
        return reflectionHelper.fieldMarshaller(object);
    }
}
