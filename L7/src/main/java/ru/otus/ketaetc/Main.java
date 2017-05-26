package ru.otus.ketaetc;

import ru.otus.ketaetc.atmFramework.atm.CashDispenserATM;
import ru.otus.ketaetc.atmFramework.atm.CashInATM;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.MostlyEvenlyCashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.SimpleCashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cassette.CashDispenserCassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.CashInCassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.Cassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.Nominal;
import ru.otus.ketaetc.atmFramework.exception.*;
import ru.otus.ketaetc.atmFramework.itDepartment.ITDepartment;

public class Main {
    public static void main(String[] args) throws NoCassettesFoundException, NotSuchNominalException, NotEnoughMoneyException, NotSuchAlgorithmException, ATMStateException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        CashDispenserATM cashDispenserATM1 = new CashDispenserATM();
        CashDispenserATM cashDispenserATM2 = new CashDispenserATM("111");

        ITDepartment itDepartment = ITDepartment.getITDepartment();
        itDepartment.register(cashDispenserATM1);
        itDepartment.register(cashDispenserATM2);

        itDepartment.setAlgorithm(cashDispenserATM1, SimpleCashOutAlgorithm.getAlgorithm());
        itDepartment.setAlgorithm(cashDispenserATM2, MostlyEvenlyCashOutAlgorithm.getAlgorithm());

        itDepartment.saveATMsStatus();
        itDepartment.restartAllATMS();
        itDepartment.register(cashDispenserATM1);
        itDepartment.register(cashDispenserATM2);
        itDepartment.setToServiceModeAllATMS();

        Cassette cas1 = new CashDispenserCassette(Nominal.ONE_HUNDRED, 750);
        Cassette cas2 = new CashDispenserCassette(Nominal.FIVE_THOUSAND, 50);
        Cassette cas3 = new CashDispenserCassette(Nominal.FIFTY, 500);
        Cassette cas4 = new CashDispenserCassette(Nominal.FIFTY, 500);
        Cassette cas5 = new CashDispenserCassette(Nominal.ONE_HUNDRED, 1000);
        cashDispenserATM1.setCassette(cas1);
        cashDispenserATM1.setCassette(cas2);
        cashDispenserATM1.setCassette(cas3);

        itDepartment.restoreATMsStatus();

        cashDispenserATM2.setCassette(cas4);
        cashDispenserATM2.setCassette(cas5);

        itDepartment.setToServiceModeAllATMS();
        cashDispenserATM2.setCassette(cas4);
        cashDispenserATM2.setCassette(cas5);

        System.out.println(cashDispenserATM1.toString() + "\nbefore cashing out: " + cashDispenserATM1.getATMBalance());
        cashDispenserATM1.cashOut(150);
        cashDispenserATM1.cashOut(450);
        cashDispenserATM1.cashOut(950);
        cashDispenserATM1.cashOut(15000);
        System.out.println("after: " + cashDispenserATM1.getATMBalance());

        System.out.println(cashDispenserATM2.toString() + "\nbefore cashing out: " + cashDispenserATM2.getATMBalance());
        cashDispenserATM2.cashOut(100);
        cashDispenserATM2.cashOut(500);
        cashDispenserATM2.cashOut(1000);
        cashDispenserATM2.cashOut(1450);
        cashDispenserATM2.cashOut(450);
        cashDispenserATM2.cashOut(200);
        cashDispenserATM2.cashOut(3000);
        System.out.println("after: " + cashDispenserATM2.getATMBalance());


        System.out.println(cashDispenserATM1.getATMMiniStatement());
        System.out.println(cashDispenserATM2.getATMMiniStatement());

        cashDispenserATM1.getCassettesLoadInfo();
        cashDispenserATM2.getCassettesLoadInfo();

        System.out.println("ITDepartment balance: " + itDepartment.getTotalDepartmentBalance() + "\n");

        System.out.println(cashDispenserATM1.toString() + "\nbefore cashin out: " + cashDispenserATM1.getATMBalance());
        System.out.println("trying to cash out: " + 1950);
        cashDispenserATM1.cashOut(1950);
        System.out.println("after: " + cashDispenserATM1.getATMBalance());
        cashDispenserATM1.getCassettesLoadInfo();

        System.out.println("Ejecting cassettes from cash dispensers:");
        System.out.println("We need to set ATMs to maintenance:");
        itDepartment.setToServiceModeAllATMS();
        itDepartment.ejectCassetesFromATMS();

        CashInATM cashInATM1 = new CashInATM();
        itDepartment.register(cashInATM1);
        itDepartment.saveATMStatus(cashInATM1);
        itDepartment.restartATM(cashInATM1);
        itDepartment.setToServiceModeATM(cashInATM1);

        Cassette casIn1 = new CashInCassette();

        cashInATM1.setCassette(casIn1);

        itDepartment.setATMSWork();
        System.out.println(cashInATM1.toString() + "\nbefore cash in: " + cashInATM1.getATMBalance());
        cashInATM1.cashIn(105, 125_000);
        cashInATM1.cashIn(105, 125_000);
        System.out.println("after: " + cashInATM1.getATMBalance() + "\n" + "count:  " + cashInATM1.getCount());
        System.out.println(cashInATM1.getATMMiniStatement());
        cashInATM1.getCassettesLoadInfo();
        itDepartment.restoreATMStatus(cashInATM1);

        System.out.println("================================================");
        System.out.println("creating ATM via builder method in ITDepartment class:");
        CashDispenserATM atmCashDispenser = (CashDispenserATM) itDepartment.buildATM("ru.otus.ketaetc.atmFramework.atm.CashDispenserATM");
        CashInATM atmCashIn = (CashInATM) itDepartment.buildATM("ru.otus.ketaetc.atmFramework.atm.CashInATM");

        atmCashDispenser.cashOut(10500);
        atmCashDispenser.cashOut(350);
        atmCashDispenser.cashOut(15550);
        atmCashDispenser.cashOut(5000);
        atmCashDispenser.cashOut(23000);
        atmCashDispenser.cashOut(50);
        System.out.println(atmCashDispenser.getATMBalance());
        System.out.println(atmCashDispenser.getATMMiniStatement());
        atmCashDispenser.getCassettesLoadInfo();

        atmCashIn.cashIn(10, 19550);
        atmCashIn.cashIn(20, 2000);
        atmCashIn.cashIn(45, 4500);
        System.out.println(atmCashIn.getATMBalance());
        System.out.println(atmCashIn.getATMMiniStatement());
        atmCashIn.getCassettesLoadInfo();
    }
}
