package ru.otus.dobrovolsky;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import ru.otus.dobrovolsky.dummy.Dummy;
import ru.otus.dobrovolsky.myJson.MyJson;

public class Main {
    public static void main(String[] args) {

        Dummy dummy = Dummy.buildDummy();

        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(dummy.getClass().getName(), dummy);

        System.out.println("Json-simple");
        System.out.println(jsonObject.toJSONString());
        System.out.println("----------------------------------------");
        System.out.println("Gson:");
        System.out.println(gson.toJson(dummy));
        System.out.println("----------------------------------------");
        System.out.println("MyJson");
        System.out.println(MyJson.toMyJsonString(dummy));
        System.out.println("----------------------------------------");
        System.out.println("MyJson with json-simple");
        System.out.println(MyJson.toMyJson(dummy));
    }
}
