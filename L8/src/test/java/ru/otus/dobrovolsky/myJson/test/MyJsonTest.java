package ru.otus.dobrovolsky.myJson.test;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.dummy.Dummy;
import ru.otus.dobrovolsky.myJson.MyJson;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyJsonTest {

    @Test
    public void aTest() {
        Dummy dummy = Dummy.buildDummy();
        Gson gson = new Gson();
        Assert.assertEquals("My implementation of json string created with Reflection,\n" +
                "no json-simple should be equal to gson string", gson.toJson(dummy), MyJson.toMyJsonString(dummy));
    }

    @Test
    public void bTest() {
        Dummy dummy = Dummy.buildDummy();
        Gson gson = new Gson();
        Assert.assertEquals("My implementation of json string created with Reflection, no json-simple should be equal to\n" +
                "my implementation of json string created with Reflection and json-simple", MyJson.toMyJson(dummy), MyJson.toMyJsonString(dummy));
    }

    @Test
    public void cTest() {
        Dummy dummy = Dummy.buildDummy();
        Gson gson = new Gson();
        Assert.assertEquals("My implementation of json string created with Reflection and json-simple\n" +
                "should be equal to gson string", gson.toJson(dummy), MyJson.toMyJson(dummy));
    }
}
