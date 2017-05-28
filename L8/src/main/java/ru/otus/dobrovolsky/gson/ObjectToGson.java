package ru.otus.dobrovolsky.gson;

import com.google.gson.Gson;
import ru.otus.dobrovolsky.dummy.Dummy;

/**
 * Created by ketaetc on 29.05.17.
 */
public class ObjectToGson {

    public ObjectToGson() {
        Dummy dummy = Dummy.buildDummy();
        System.out.println(dummy);

        Gson gson = new Gson();
        String json = gson.toJson(dummy);
        System.out.println(json);
    }
}
