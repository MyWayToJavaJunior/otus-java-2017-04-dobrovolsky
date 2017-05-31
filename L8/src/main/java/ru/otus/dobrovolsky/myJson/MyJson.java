package ru.otus.dobrovolsky.myJson;

import ru.otus.dobrovolsky.reflect.ReflectionHelperWithJSON;
import ru.otus.dobrovolsky.reflect.ReflectionHelperWithStrings;

public class MyJson {

    public static String toMyJsonString(Object object) {
        String string = ReflectionHelperWithStrings.fieldMarshaller(object);
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{").append(string).append("}");

            return stringBuilder.toString();
        }
        return null;
    }

    public static String toMyJson(Object object) {
        String string = ReflectionHelperWithJSON.fieldMarshaller(object);

        return string;
    }
}
