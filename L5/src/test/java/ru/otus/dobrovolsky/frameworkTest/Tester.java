package ru.otus.dobrovolsky.frameworkTest;

import org.junit.Test;
import ru.otus.dobrovolsky.framework.helpers.AnnotationHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static ru.otus.dobrovolsky.framework.Assert.assertTrue;
import static ru.otus.dobrovolsky.framework.helpers.AnnotationHelper.getAnnotatedMethodsCount;

public class Tester {

    @Test
    public void testWithArrayOfClassesAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] arrayOfClasses = new Class<?>[]{TestClassFirst.class, TestClassSecond.class};

        System.out.println("===========================================================");
        System.out.println("Starting using reflections on array of classes:");
        System.out.println(Arrays.toString(arrayOfClasses));
        System.out.println("-----------------------------------------------------------");

        AnnotationHelper.doAnnotationHelp(arrayOfClasses);

        assertTrue("The correct number of @Before annotated methods: 6", getAnnotatedMethodsCount(arrayOfClasses).get("Before") == 6);
        assertTrue("The correct number of @Test annotated methods: 6", getAnnotatedMethodsCount(arrayOfClasses).get("Test") == 6);
        assertTrue("The correct number of @After annotated methods: 6", getAnnotatedMethodsCount(arrayOfClasses).get("After") == 6);
    }

    @Test
    public void testWithPackageNameAsAParameter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String packageName = "ru.otus.dobrovolsky.frameworkTest";
        Character[] pcgNameArr = packageName.chars().mapToObj(c -> (char) c).toArray(Character[]::new);

        System.out.println("===========================================================");
        System.out.println("Starting using reflections on package " + packageName + ":");
        System.out.println("-----------------------------------------------------------");

        AnnotationHelper.doAnnotationHelp(pcgNameArr);

        assertTrue("The correct number of @Before annotated methods: 6", getAnnotatedMethodsCount(pcgNameArr).get("Before") == 6);
        assertTrue("The correct number of @Test annotated methods: 6", getAnnotatedMethodsCount(pcgNameArr).get("Test") == 6);
        assertTrue("The correct number of @After annotated methods: 6", getAnnotatedMethodsCount(pcgNameArr).get("After") == 6);
        assertTrue("The correct number of org.junit.@Test annotated methods: 2", getAnnotatedMethodsCount(pcgNameArr).get("jUnit.Test") == 2);
    }
}
