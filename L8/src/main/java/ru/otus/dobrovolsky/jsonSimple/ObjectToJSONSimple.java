package ru.otus.dobrovolsky.jsonSimple;

import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.dummy.Dummy;

public class ObjectToJSONSimple {

    public ObjectToJSONSimple() {
        JSONObject obj = new JSONObject();
        Dummy dummy = Dummy.buildDummy();

        System.out.println("Json-simple");
        obj.put("dummy", dummy);
        String json = obj.toJSONString();
        System.out.println(json);
    }
}
