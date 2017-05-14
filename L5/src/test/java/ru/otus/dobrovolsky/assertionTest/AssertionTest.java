package ru.otus.dobrovolsky.assertionTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.framework.Assert;
import ru.otus.dobrovolsky.framework.exception.MyAssertionError;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssertionTest {
    @Test(expected = MyAssertionError.class)
    public void afailTest() {
        Assert.fail();
    }

    @Test(expected = MyAssertionError.class)
    public void bfailTestWMessage() {
        Assert.fail("Fails while testing ru.otus.dobrovolsky.framework.Assert.fail realisation");
    }

    @Test
    public void cAssertTrueTest() {
        Assert.assertTrue(true);
    }

    @Test(expected = MyAssertionError.class)
    public void dAssertTrueTestWrong() {
        Assert.assertTrue(false);
    }

    @Test
    public void fAssertTrueTestWMessage() {
        Assert.assertTrue("1 == 1", true);
    }

    @Test(expected = MyAssertionError.class)
    public void gAssertTrueTestWMessageWrong() {
        Assert.assertTrue("1 == 0", false);
    }

    @Test
    public void hAssertNotNullTest() {
        Assert.assertNotNull(0);
    }

    @Test
    public void iAssertNotNullTestWMessage() {
        Assert.assertNotNull("with new Object", new Object());
    }

    @Test(expected = MyAssertionError.class)
    public void jAssertNotNullTestWMessageWrong() {
        Assert.assertNotNull("with null ref", null);
    }
}
