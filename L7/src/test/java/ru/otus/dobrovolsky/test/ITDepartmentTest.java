package ru.otus.dobrovolsky.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.atmFramework.atm.ATM;
import ru.otus.dobrovolsky.atmFramework.atm.State;
import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.MostlyEvenlyCashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.SimpleCashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.exception.*;
import ru.otus.dobrovolsky.atmFramework.itDepartment.ITDepartment;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ITDepartmentTest {
    @Test
    public void aTest() {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        Assert.assertTrue("ITDepartment must be not null", itDepartment != null);
    }

    @Test
    public void bTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        double balance = atm.getATMBalance();
        Assert.assertTrue("Initial balance of newly loaded ATM = 3325000", balance == 3325000.0d);
    }

    @Test
    public void cTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        itDepartment.setAlgorithm(atm, SimpleCashOutAlgorithm.getAlgorithm());
        CashOutAlgorithm cashOutAlgorithm = atm.getAlgorithm();
        Assert.assertEquals("Algorithm should be SimpleCashOutAlgorithm", cashOutAlgorithm, SimpleCashOutAlgorithm.getAlgorithm());
    }

    @Test
    public void dTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        ATM atm2 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        ATM atm3 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        ATM atm4 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        ATM atm5 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        itDepartment.register(atm1);
        itDepartment.register(atm2);
        itDepartment.register(atm3);
        itDepartment.register(atm4);
        itDepartment.register(atm5);
        Assert.assertNotNull("There are 5 ATMs generated with builder method", itDepartment.getATMList());
    }

    @Test
    public void eTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        itDepartment.register(atm1);

        itDepartment.setToServiceModeATM(atm1);
        Assert.assertTrue("ATM now in service mode", atm1.getState() == State.MAINTENANCE);

        itDepartment.setATMWork(atm1);
        Assert.assertTrue("ATM now in work mode", atm1.getState() == State.IDLE);
    }

    @Test
    public void fTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        itDepartment.register(atm1);


        itDepartment.setATMWork(atm1);
        Assert.assertTrue("ATM now in work mode", atm1.getState() == State.IDLE);
        atm1.shutDown();
        Assert.assertTrue("ATM now is shutting down", atm1.getState() == State.POWER_OFF);
        atm1.setServiceMode();
        Assert.assertTrue("ATM now is in service mode", atm1.getState() == State.MAINTENANCE);
    }

    @Test
    public void gTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        itDepartment.register(atm1);
        itDepartment.setAlgorithm(atm1, MostlyEvenlyCashOutAlgorithm.getAlgorithm());

        itDepartment.setATMWork(atm1);
        double balanceBefore = atm1.getATMBalance();
        atm1.processCash(59850);
        double balanceAfter = atm1.getATMBalance();
        double delta = balanceBefore - balanceAfter;
        Assert.assertTrue("ATM delta is RUB 59850", delta == 59850d);
    }

    @Test
    public void hTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashInATM");
        itDepartment.register(atm1);


        itDepartment.setATMWork(atm1);
        double balanceBefore = atm1.getATMBalance();
        atm1.processCash(55000, 1000);
        double balanceAfter = atm1.getATMBalance();
        double delta = balanceAfter - balanceBefore;
        Assert.assertTrue("ATM delta is RUB 55_000", delta == 55_000d);
    }

    @Test
    public void iTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashInATM");
        itDepartment.register(atm1);

        itDepartment.setToServiceModeATM(atm1);
        Assert.assertTrue("ATM status now is maintenance", atm1.getState() == State.MAINTENANCE);
        itDepartment.saveATMsStatus();
        itDepartment.setATMWork(atm1);
        Assert.assertTrue("ATM status now is IDLE", atm1.getState() == State.IDLE);
        itDepartment.restartATM(atm1);
        Assert.assertTrue("ATM status now is rebooting", atm1.getState() == State.REBOOTING);
        itDepartment.restoreATMStatus(atm1);
        Assert.assertTrue("ATM status was restored - maintenance now", atm1.getState() == State.MAINTENANCE);
    }

    @Test
    public void jTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashInATM");
        itDepartment.register(atm1);

        itDepartment.setToServiceModeATM(atm1);
        Assert.assertTrue("ATM status now is maintenance", atm1.getState() == State.MAINTENANCE);

        itDepartment.loadATM(atm1);
        Assert.assertNotNull("ATM was reloaded with cassettes, initial balance = 3325000", atm1.getATMBalance() == 3325000);
    }

    @Test
    public void kTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        ITDepartment itDepartment = ITDepartment.getITDepartment();
        ATM atm1 = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashInATM");
        itDepartment.register(atm1);

        Assert.assertEquals("ATM address is Bank office", atm1.getAddress(), "Bank office");
        itDepartment.setATMAddress(atm1, "Bank2");
        Assert.assertEquals("ATM address is Bank2", atm1.getAddress(), "Bank2");
        itDepartment.setATMAddress(atm1, "Bank3");
        Assert.assertEquals("ATM address is Bank3", atm1.getAddress(), "Bank3");

    }
}
