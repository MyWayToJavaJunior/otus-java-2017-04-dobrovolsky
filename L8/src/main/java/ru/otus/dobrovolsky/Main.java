package ru.otus.dobrovolsky;

import ru.otus.dobrovolsky.gson.ObjectToGson;
import ru.otus.dobrovolsky.jsonSimple.ObjectToJSONSimple;

/**
 * Created by ketaetc on 29.05.17.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("+++JSON");

        ObjectToGson otg = new ObjectToGson();
        ObjectToJSONSimple otjs = new ObjectToJSONSimple();

        System.out.println("---JSON");

    }
}
