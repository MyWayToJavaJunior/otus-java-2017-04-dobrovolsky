package ru.otus.ketaetc.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.ketaetc.atmFramework.atm.CashDispenserATM;
import ru.otus.ketaetc.atmFramework.atm.CashInATM;
import ru.otus.ketaetc.atmFramework.atm.State;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.MostlyEvenlyCashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.SimpleCashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.exception.*;
import ru.otus.ketaetc.atmFramework.itDepartment.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentTest {
    @Test
    public void aTest() {
        Department department = Department.getDepartment();
        Assert.assertTrue("Department must be not null", department != null);
    }

    @Test
    public void bTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Department department = Department.getDepartment();
        CashDispenserATM atm = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        double balance = atm.getATMBalance();
        Assert.assertTrue("Initial balance of newly loaded ATM = 3325000", balance == 3325000.0d);
    }

    @Test
    public void cTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Department department = Department.getDepartment();
        CashDispenserATM atm = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        department.setAlgorithm(atm, SimpleCashOutAlgorithm.getAlgorithm());
        CashOutAlgorithm cashOutAlgorithm = atm.getAlgorithm();
        Assert.assertEquals("Algorithm should be SimpleCashOutAlgorithm", cashOutAlgorithm, SimpleCashOutAlgorithm.getAlgorithm());
    }

    @Test
    public void dTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Department department = Department.getDepartment();
        CashDispenserATM atm1 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        CashDispenserATM atm2 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        CashDispenserATM atm3 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        CashDispenserATM atm4 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        CashDispenserATM atm5 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        department.register(atm1);
        department.register(atm2);
        department.register(atm3);
        department.register(atm4);
        department.register(atm5);
        Assert.assertNotNull("There are 5 ATMs generated with builder method", department.getATMList());
    }

    @Test
    public void eTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Department department = Department.getDepartment();
        CashDispenserATM atm1 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        department.register(atm1);

        department.setToServiceModeATM(atm1);
        Assert.assertTrue("ATM now in service mode", atm1.getState() == State.MAINTENANCE);

        department.setATMWork(atm1);
        Assert.assertTrue("ATM now in work mode", atm1.getState() == State.IDLE);
    }

    @Test
    public void fTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Department department = Department.getDepartment();
        CashDispenserATM atm1 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        department.register(atm1);


        department.setATMWork(atm1);
        Assert.assertTrue("ATM now in work mode", atm1.getState() == State.IDLE);
        atm1.shutDown();
        Assert.assertTrue("ATM now is shutting down", atm1.getState() == State.POWER_OFF);
        atm1.setServiceMode();
        Assert.assertTrue("ATM now is in service mode", atm1.getState() == State.MAINTENANCE);
    }

    @Test
    public void gTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException {
        Department department = Department.getDepartment();
        CashDispenserATM atm1 = (CashDispenserATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        department.register(atm1);
        department.setAlgorithm(atm1, MostlyEvenlyCashOutAlgorithm.getAlgorithm());


        department.setATMWork(atm1);
        double balanceBefore = atm1.getATMBalance();
        atm1.cashOut(59850);
        double balanceAfter = atm1.getATMBalance();
        double delta = balanceBefore - balanceAfter;
        Assert.assertTrue("ATM delta is RUB 59850", delta == 59850d);
    }

    @Test
    public void hTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        Department department = Department.getDepartment();
        CashInATM atm1 = (CashInATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashInATM");
        department.register(atm1);


        department.setATMWork(atm1);
        double balanceBefore = atm1.getATMBalance();
        atm1.cashIn(1000, 55000);
        double balanceAfter = atm1.getATMBalance();
        double delta = balanceAfter - balanceBefore;
        Assert.assertTrue("ATM delta is RUB 55_000", delta == 55_000d);
    }

    @Test
    public void iTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        Department department = Department.getDepartment();
        CashInATM atm1 = (CashInATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashInATM");
        department.register(atm1);

        department.setToServiceModeATM(atm1);
        Assert.assertTrue("ATM status now is maintenance", atm1.getState() == State.MAINTENANCE);
        department.saveATMsStatus();
        department.setATMWork(atm1);
        Assert.assertTrue("ATM status now is IDLE", atm1.getState() == State.IDLE);
        department.restartATM(atm1);
        Assert.assertTrue("ATM status now is rebooting", atm1.getState() == State.REBOOTING);
        department.restoreATMStatus(atm1);
        Assert.assertTrue("ATM status was restored - maintenance now", atm1.getState() == State.MAINTENANCE);
    }

    @Test
    public void jTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NotEnoughMoneyException, NotSuchNominalException, NoCassettesFoundException, NotSuchAlgorithmException, ATMStateException {
        Department department = Department.getDepartment();
        CashInATM atm1 = (CashInATM) department.buildATM("ru.otus.ketaetc.atmFramework.atm.CashInATM");
        department.register(atm1);

        department.setToServiceModeATM(atm1);
        Assert.assertTrue("ATM status now is maintenance", atm1.getState() == State.MAINTENANCE);

        department.loadATM(atm1);
        Assert.assertNotNull("ATM was reloaded with cassettes, initial balance = 3325000", atm1.getATMBalance() == 3325000);
    }
}
