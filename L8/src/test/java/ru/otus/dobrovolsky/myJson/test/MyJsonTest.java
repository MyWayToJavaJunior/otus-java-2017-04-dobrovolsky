package ru.otus.dobrovolsky.myJson.test;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import ru.otus.dobrovolsky.dummy.Dummy;
import ru.otus.dobrovolsky.myJson.MyJson;

public class MyJsonTest {

    @Test
    public void aTest() {
        Dummy dummy = Dummy.buildDummy();
        Gson gson = new Gson();
        Assert.assertEquals("My implementation of json string should be equal to gson string", gson.toJson(dummy), MyJson.toMyJsonString(dummy));
    }
}
