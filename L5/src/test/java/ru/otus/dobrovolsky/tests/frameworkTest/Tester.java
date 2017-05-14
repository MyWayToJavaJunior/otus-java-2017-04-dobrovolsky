package ru.otus.dobrovolsky.tests.frameworkTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.tests.dummies.TestClassFirst;
import ru.otus.dobrovolsky.tests.dummies.TestClassSecond;

import java.lang.reflect.InvocationTargetException;

import static ru.otus.dobrovolsky.framework.Assert.assertTrue;
import static ru.otus.dobrovolsky.framework.helpers.AnnotationHelper.getAnnotatedMethodsMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tester {

    @Test
    public void aTestWithArrayOfClassesAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] arrayOfClasses = new Class<?>[]{TestClassFirst.class, TestClassSecond.class};

        assertTrue("The correct number of @Before annotated methods: 6", getAnnotatedMethodsMap(arrayOfClasses).get("Before") == 6);
        assertTrue("The correct number of @Test annotated methods: 6", getAnnotatedMethodsMap(arrayOfClasses).get("Test") == 6);
        assertTrue("The correct number of @After annotated methods: 6", getAnnotatedMethodsMap(arrayOfClasses).get("After") == 6);
    }

    @Test
    public void bTestWithPackageNameAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String packageName = "ru.otus.dobrovolsky.tests";
        Character[] pcgNameArr = packageName.chars().mapToObj(c -> (char) c).toArray(Character[]::new);

        assertTrue("The correct number of @Before annotated methods: 6", getAnnotatedMethodsMap(pcgNameArr).get("Before") == 6);
        assertTrue("The correct number of @Test annotated methods: 6", getAnnotatedMethodsMap(pcgNameArr).get("Test") == 6);
        assertTrue("The correct number of @After annotated methods: 6", getAnnotatedMethodsMap(pcgNameArr).get("After") == 6);
        assertTrue("The correct number of org.junit.@Test annotated methods: 11", getAnnotatedMethodsMap(pcgNameArr).get("jUnit.Test") == 11);
    }
}
