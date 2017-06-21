package ru.otus.dobrovolsky;

import ru.otus.dobrovolsky.atmFramework.atm.ATM;
import ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM;
import ru.otus.dobrovolsky.atmFramework.atm.CashInATM;
import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.MostlyEvenlyCashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.SimpleCashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.atm.cassette.*;
import ru.otus.dobrovolsky.atmFramework.exception.*;
import ru.otus.dobrovolsky.atmFramework.itDepartment.ITDepartment;

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
        cashDispenserATM1.processCash(new CashFromCashDispenser(150));
        cashDispenserATM1.processCash(new CashFromCashDispenser(450));
        cashDispenserATM1.processCash(new CashFromCashDispenser(950));
        cashDispenserATM1.processCash(new CashFromCashDispenser(15000));
        System.out.println("after: " + cashDispenserATM1.getATMBalance());

        System.out.println(cashDispenserATM2.toString() + "\nbefore cashing out: " + cashDispenserATM2.getATMBalance());
        cashDispenserATM2.processCash(new CashFromCashDispenser(100));
        cashDispenserATM2.processCash(new CashFromCashDispenser(500));
        cashDispenserATM2.processCash(new CashFromCashDispenser(1000));
        cashDispenserATM2.processCash(new CashFromCashDispenser(1450));
        cashDispenserATM2.processCash(new CashFromCashDispenser(450));
        cashDispenserATM2.processCash(new CashFromCashDispenser(200));
        cashDispenserATM2.processCash(new CashFromCashDispenser(3000));
        System.out.println("after: " + cashDispenserATM2.getATMBalance());


        System.out.println(cashDispenserATM1.getATMMiniStatement());
        System.out.println(cashDispenserATM2.getATMMiniStatement());

        cashDispenserATM1.getCassettesLoadInfo();
        cashDispenserATM2.getCassettesLoadInfo();

        System.out.println("ITDepartment balance: " + itDepartment.getTotalDepartmentBalance() + "\n");

        System.out.println(cashDispenserATM1.toString() + "\nbefore cashin out: " + cashDispenserATM1.getATMBalance());
        System.out.println("trying to cash out: " + 1950);
        cashDispenserATM1.processCash(new CashFromCashDispenser(1950));
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
        cashInATM1.processCash(new CashForCashIn(125_000, 105));
        cashInATM1.processCash(new CashForCashIn(125_000, 105));
        System.out.println("after: " + cashInATM1.getATMBalance() + "\n" + "count:  " + cashInATM1.getCount());
        System.out.println(cashInATM1.getATMMiniStatement());
        cashInATM1.getCassettesLoadInfo();
        itDepartment.restoreATMStatus(cashInATM1);

        System.out.println("================================================");
        System.out.println("creating ATM via builder method in ITDepartment class:");
        ATM atmCashDispenser = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashDispenserATM");
        ATM atmCashIn = itDepartment.buildATM("ru.otus.dobrovolsky.atmFramework.atm.CashInATM");

        atmCashDispenser.processCash(new CashFromCashDispenser(10500));
        atmCashDispenser.processCash(new CashFromCashDispenser(350));
        atmCashDispenser.processCash(new CashFromCashDispenser(15550));
        atmCashDispenser.processCash(new CashFromCashDispenser(5000));
        atmCashDispenser.processCash(new CashFromCashDispenser(23000));
        atmCashDispenser.processCash(new CashFromCashDispenser(50));
        System.out.println(atmCashDispenser.getATMBalance());
        System.out.println(atmCashDispenser.getATMMiniStatement());
        atmCashDispenser.getCassettesLoadInfo();

        atmCashIn.processCash(new CashForCashIn(19550, 10));
        atmCashIn.processCash(new CashForCashIn(2000, 20));
        atmCashIn.processCash(new CashForCashIn(4500, 45));
        System.out.println(atmCashIn.getATMBalance());
        System.out.println(atmCashIn.getATMMiniStatement());
        atmCashIn.getCassettesLoadInfo();
    }
}
