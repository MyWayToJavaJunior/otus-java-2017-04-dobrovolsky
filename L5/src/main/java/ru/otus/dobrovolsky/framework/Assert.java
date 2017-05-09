package ru.otus.dobrovolsky.framework;

import ru.otus.dobrovolsky.framework.exception.MyAssertionError;

public class Assert {

    private Assert() {
    }

    public static void fail() {
        throw new MyAssertionError("Test failed with MyAssertionError!");
    }

    public static void fail(String message) {
        throw new MyAssertionError(message);
    }

    public static void assertTrue(String message, boolean expr) {
        if (!expr) {
            fail(message);
        }
    }

    public static void assertTrue(boolean expr) {
        assertTrue(null, expr);
    }

    public static void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

    public static void assertNotNull(Object object) {
        assertNotNull(null, object != null);
    }
}
