package ru.otus.dobrovolsky.myJson;

import ru.otus.dobrovolsky.reflect.ReflectionHelper;

public class MyJson {

    public static String toMyJsonString(Object object) {
        String string = ReflectionHelper.fieldMarshaller(object);
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{").append(string).append("}");

            return stringBuilder.toString();
        }
        return null;
    }
}
