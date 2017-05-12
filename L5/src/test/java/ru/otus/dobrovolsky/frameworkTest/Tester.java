package ru.otus.dobrovolsky.frameworkTest;

import org.junit.Test;
import ru.otus.dobrovolsky.framework.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Tester {

    HashMap<String, Integer> annotatedMethodsCounter = new HashMap<>(3);

    @Test
    public void testWithArrayOfClassesAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionHelper.doReflectionWithArrayOfClasses(new Class<?>[] {TestClassFirst.class, TestClassSecond.class}, annotatedMethodsCounter);

        int beforeListSize = annotatedMethodsCounter.get("beforeListSize");
        int testListSize = annotatedMethodsCounter.get("testListSize");
        int afterListSize = annotatedMethodsCounter.get("afterListSize");

        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Before annotated methods: 6", beforeListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Test annotated methods: 6", testListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @After annotated methods: 6", afterListSize == 6);
    }

    @Test
    public void testWithPackageNameAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String packageName = "ru.otus.dobrovolsky.frameworkTest";

        ReflectionHelper.doReflectionWithPackage(packageName, annotatedMethodsCounter);

        int beforeListSize = annotatedMethodsCounter.get("beforeListSize");
        int testListSize = annotatedMethodsCounter.get("testListSize");
        int afterListSize = annotatedMethodsCounter.get("afterListSize");

        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Before annotated methods: 6", beforeListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Test annotated methods: 6", testListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @After annotated methods: 6", afterListSize == 6);
    }

}
