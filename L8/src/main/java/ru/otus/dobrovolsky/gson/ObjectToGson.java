package ru.otus.dobrovolsky.gson;

import com.google.gson.Gson;
import ru.otus.dobrovolsky.dummy.Dummy;

public class ObjectToGson {

    public ObjectToGson() {
        Dummy dummy = Dummy.buildDummy();

        Gson gson = new Gson();
        String json = gson.toJson(dummy);
        System.out.println(json);
    }
}
