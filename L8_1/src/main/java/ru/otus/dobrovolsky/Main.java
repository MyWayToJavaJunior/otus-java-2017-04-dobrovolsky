package ru.otus.dobrovolsky;

import com.google.gson.Gson;
import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import ru.otus.dobrovolsky.dummy.Dummy;
import ru.otus.dobrovolsky.myJSON.MyJSON;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        String gsonString;
        String myJSONString;

        List<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(400);
        myJSONString = MyJSON.toJson(list).toJSONString();
        gsonString = gson.toJson(list);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("List jsons equals: " + myJSONString.equals(gsonString));

        Map<String, Integer> map = new HashMap<>();
        map.put("row1", 1);
        map.put("row2", 2);
        map.put("row3", 3);
        map.put("row4", 4);
        myJSONString = MyJSON.toJson(map).toJSONString();
        gsonString = gson.toJson(map);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("Map jsons equals: " + myJSONString.equals(gsonString));

        Set<String> set = new HashSet<>();
        set.add("row1");
        set.add("row2");
        set.add("row3");
        set.add("row4");
        myJSONString = MyJSON.toJson(set).toJSONString();
        gsonString = gson.toJson(set);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("Set jsons equals: " + myJSONString.equals(gsonString));

        String[] testStringArray = new String[]{"FIRST TEST STRING", "SECOND TEST STRING"};
        myJSONString = MyJSON.toJson(testStringArray).toJSONString();
        gsonString = gson.toJson(testStringArray);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("String jsons equals: " + myJSONString.equals(gsonString));

        Object[] data = new Object[]{1, null, "test", 0.5, 0.1, false, true};
        myJSONString = MyJSON.toJson(data).toJSONString();
        gsonString = gson.toJson(data);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("Object array jsons equals: " + myJSONString.equals(gsonString));

        int[] arr = new int[2];
        arr[0] = 11;
        arr[1] = 22;
        myJSONString = MyJSON.toJson(arr).toJSONString();
        gsonString = gson.toJson(arr);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("int array jsons equals: " + myJSONString.equals(gsonString));

        boolean[] arrB = new boolean[2];
        arrB[0] = true;
        arrB[1] = false;
        myJSONString = MyJSON.toJson(arrB).toJSONString();
        gsonString = gson.toJson(arrB);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("boolean array jsons equals: " + myJSONString.equals(gsonString));

        Dummy dummy = Dummy.buildDummy();
        myJSONString = MyJSON.toJson(dummy).toJSONString();
        JSONParser jsonParser = new JSONParser();
        JSONAware parsedJsonObject = (JSONAware) jsonParser.parse(myJSONString);
        myJSONString = MyJSON.toJson(parsedJsonObject).toJSONString();
        gsonString = gson.toJson(parsedJsonObject);
        System.out.println(myJSONString);
        System.out.println(gsonString);
        System.out.println("dummy class jsons from parsed dummy objects from json string equals: " + myJSONString.equals(gsonString));
    }
}
