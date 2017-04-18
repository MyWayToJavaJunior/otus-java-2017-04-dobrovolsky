package ru.otus.dobrovolsky;

import ru.otus.dobrovolsky.collections.MyArrayList;
import ru.otus.dobrovolsky.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ketaetc on 18.04.17.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        List<Integer> myArrayList = new MyArrayList<>();
        List<Integer> standardArrayList = new ArrayList<>();

        Util.start(myArrayList);
        Util.start(standardArrayList);
    }


}
