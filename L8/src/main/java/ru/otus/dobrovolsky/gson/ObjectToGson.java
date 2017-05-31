package ru.otus.dobrovolsky.gson;

import com.google.gson.Gson;
import ru.otus.dobrovolsky.dummy.Dummy;

public class ObjectToGson {
    private String jsonString;

    public ObjectToGson() {
        Dummy dummy = Dummy.buildDummy();

        Gson gson = new Gson();
        jsonString = gson.toJson(dummy);
    }

    public void print() {
        System.out.println(jsonString);
    }
}
