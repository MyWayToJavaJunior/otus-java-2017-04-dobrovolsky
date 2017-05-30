package ru.otus.dobrovolsky;

import ru.otus.dobrovolsky.dummy.Dummy;
import ru.otus.dobrovolsky.gson.ObjectToGson;
import ru.otus.dobrovolsky.myJson.MyJson;

public class Main {
    public static void main(String[] args) {

        ObjectToGson otg = new ObjectToGson();
        System.out.println(MyJson.toMyJsonString(Dummy.buildDummy()));
    }
}
