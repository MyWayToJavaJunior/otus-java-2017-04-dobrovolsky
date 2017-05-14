package ru.otus.dobrovolsky.tests.dummies;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.framework.annotation.After;
import ru.otus.dobrovolsky.framework.annotation.Before;
import ru.otus.dobrovolsky.framework.annotation.Test;

@SuppressWarnings("unused")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestClassFirst {

    @Before
    public static void aBeforeAnnotationTestMethodFirst() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Before annotation: first method");
    }

    @Before
    public static void bBeforeAnnotationTestMethodSecond() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Before annotation: second method");
    }

    @Before
    public static void cBeforeAnnotationTestMethodThird() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Before annotation: third method");
    }

    @Test
    public static void dTestAnnotationTestMethodFirst() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Test annotation: first method");
    }

    @Test
    public static void eTestAnnotationTestMethodSecond() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Test annotation: second method");
    }

    @Test
    public static void fTestAnnotationTestMethodThird() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.Test annotation: third method");
    }

    @After
    public static void gAfterAnnotationTestMethodFirst() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.After annotation: first method");
    }

    @After
    public static void hAfterAnnotationTestMethodSecond() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.After annotation: second method");
    }

    @After
    public static void iAfterAnnotationTestMethodThird() {
        System.out.println("Method executed while testing ru.otus.dobrovolsky.framework.annotation.After annotation: third method");
    }
}