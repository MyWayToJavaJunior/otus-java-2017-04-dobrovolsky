package ru.otus.dobrovolsky.assertionTest;

import org.junit.Test;
import ru.otus.dobrovolsky.framework.Assert;
import ru.otus.dobrovolsky.framework.exception.MyAssertionError;

public class AssertionTest {
    /**
     * fails
     */
    @Test(expected = MyAssertionError.class)
    public void failTest() {
        Assert.fail();
    }

    /**
     * fails
     */
    @Test(expected = MyAssertionError.class)
    public void failTestWMessage() {
        Assert.fail("Fails while testing ru.otus.dobrovolsky.framework.Assert.fail realisation");
    }

    @Test
    public void assertTrueTest() {
        Assert.assertTrue(true);
    }

    @Test(expected = MyAssertionError.class)
    public void assertTrueTestWrong() {
        Assert.assertTrue(false);
    }

    @Test
    public void assertTrueTestWMessage() {
        Assert.assertTrue("1 == 1", true);
    }

    @Test(expected = MyAssertionError.class)
    public void assertTrueTestWMessageWrong() {
        Assert.assertTrue("1 == 0", false);
    }

    @Test
    public void assertNotNullTest() {
        Assert.assertNotNull(0);
    }

    @Test
    public void assertNotNullTestWMessage() {
        Assert.assertNotNull("with new Object", new Object());
    }

    /**
     * fails with null ref
     */
    @Test(expected = MyAssertionError.class)
    public void assertNotNullTestWMessageWrong() {
        Assert.assertNotNull("with null ref", null);
    }
}
