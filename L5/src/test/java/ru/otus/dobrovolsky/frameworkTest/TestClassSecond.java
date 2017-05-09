package ru.otus.dobrovolsky.frameworkTest;

import ru.otus.dobrovolsky.framework.annotation.After;
import ru.otus.dobrovolsky.framework.annotation.Before;
import ru.otus.dobrovolsky.framework.annotation.Test;

@SuppressWarnings("unused")
public class TestClassSecond {

    @Before
    public static void beforeAnnotationTestMethodFirst() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Before annotation: first method");
    }

    @Before
    public static void beforeAnnotationTestMethodSecond() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Before annotation: second method");
    }

    @Before
    public static void beforeAnnotationTestMethodThird() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Before annotation: third method");
    }

    @Test
    public static void testAnnotationTestMethodFirst() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Test annotation: first method");
    }

    @Test
    public static void testAnnotationTestMethodSecond() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Test annotation: second method");
    }

    @Test
    public static void testAnnotationTestMethodThird() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Test annotation: third method");
    }

    @After
    public static void afterAnnotationTestMethodFirst() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.After annotation: first method");
    }

    @After
    public static void afterAnnotationTestMethodSecond() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.After annotation: second method");
    }

    @After
    public static void afterAnnotationTestMethodThird() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.After annotation: third method");
    }
}
