package ru.otus.dobrovolsky.frameworkTest;

import org.junit.Before;
import org.junit.Test;
import ru.otus.dobrovolsky.framework.Assert;
import ru.otus.dobrovolsky.framework.ReflectionHelper;
import ru.otus.dobrovolsky.framework.annotation.After;

import java.lang.reflect.InvocationTargetException;

public class Tester {

    private Class<?>[] classesArray = {TestClassFirst.class, TestClassSecond.class};

    @Test
    public void testWithArrayOfClassesAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionHelper.doReflectionWithArrayOfClasses(classesArray);

        int beforeListSize = ReflectionHelper.getListAnnotatedWith(Before.class);
        int testListSize = ReflectionHelper.getListAnnotatedWith(Test.class);
        int afterListSize = ReflectionHelper.getListAnnotatedWith(After.class);

        Assert.assertTrue("The correct number of @Before annotated methods: 6", beforeListSize == 6);
        Assert.assertTrue("The correct number of @Test annotated methods: 6", testListSize == 6);
        Assert.assertTrue("The correct number of @After annotated methods: 6", afterListSize == 6);
    }

    @Test
    public void testWithPackageNameAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String packageName = "ru.otus.dobrovolsky.frameworkTest";

        ReflectionHelper.doReflectionWithPackage(packageName);

        int beforeListSize = ReflectionHelper.getListAnnotatedWith(Before.class);
        int testListSize = ReflectionHelper.getListAnnotatedWith(Test.class);
        int afterListSize = ReflectionHelper.getListAnnotatedWith(After.class);

        Assert.assertTrue("The correct number of @Before annotated methods: 6", beforeListSize == 6);
        Assert.assertTrue("The correct number of @Test annotated methods: 6", testListSize == 6);
        Assert.assertTrue("The correct number of @After annotated methods: 6", afterListSize == 6);
    }

}
