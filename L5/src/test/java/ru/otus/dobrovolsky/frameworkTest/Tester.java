package ru.otus.dobrovolsky.frameworkTest;

import org.junit.Test;
import ru.otus.dobrovolsky.framework.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;

public class Tester {

    @Test
    public void testWithArrayOfClassesAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionHelper.doReflectionWithArrayOfClasses(new Class<?>[] {TestClassFirst.class, TestClassSecond.class});

        int beforeListSize = ReflectionHelper.getListAnnotatedWith(ru.otus.dobrovolsky.framework.annotation.Before.class);
        int testListSize = ReflectionHelper.getListAnnotatedWith(ru.otus.dobrovolsky.framework.annotation.Test.class);
        int afterListSize = ReflectionHelper.getListAnnotatedWith(ru.otus.dobrovolsky.framework.annotation.After.class);

        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Before annotated methods: 6", beforeListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Test annotated methods: 6", testListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @After annotated methods: 6", afterListSize == 6);
    }

    @Test
    public void testWithPackageNameAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String packageName = "ru.otus.dobrovolsky.frameworkTest";

        ReflectionHelper.doReflectionWithPackage(packageName);

        int beforeListSize = ReflectionHelper.getListAnnotatedWith(ru.otus.dobrovolsky.framework.annotation.Before.class);
        int testListSize = ReflectionHelper.getListAnnotatedWith(ru.otus.dobrovolsky.framework.annotation.Test.class);
        int afterListSize = ReflectionHelper.getListAnnotatedWith(ru.otus.dobrovolsky.framework.annotation.After.class);

        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Before annotated methods: 6", beforeListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @Test annotated methods: 6", testListSize == 6);
        ru.otus.dobrovolsky.framework.Assert.assertTrue("The correct number of @After annotated methods: 6", afterListSize == 6);
    }

}
