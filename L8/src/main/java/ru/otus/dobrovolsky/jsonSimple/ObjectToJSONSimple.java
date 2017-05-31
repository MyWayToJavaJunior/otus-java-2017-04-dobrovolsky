package ru.otus.dobrovolsky.jsonSimple;

import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.dummy.Dummy;

public class ObjectToJSONSimple {
    private String jsonString;

    public ObjectToJSONSimple() {
        Dummy dummy = Dummy.buildDummy();

        JSONObject obj = new JSONObject();
        obj.put("dummy", dummy);

        jsonString = obj.toJSONString();
    }

    public void println() {
        System.out.println(jsonString);
    }
}
