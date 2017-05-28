package ru.otus.dobrovolsky.atmFramework.itDepartment;

import ru.otus.dobrovolsky.atmFramework.atm.ATM;
import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;

import java.util.List;

public interface Department {

    List<ATM> getATMList();

    <T extends ATM> void setAlgorithm(T t, CashOutAlgorithm algorithm);

    void ejectCassetesFromATMS();

    void reloadCassetesToATMS();

    void ejectCassetesFromATM(ATM atm);

    void register(ATM atm);

    void unregisterAllATMS();

    void unregister(ATM atm);

    void setATMSWork();

    void setATMWork(ATM atm);

    void restartAllATMS();

    void restartATM(ATM atm);

    void setToServiceModeAllATMS();

    void setToServiceModeATM(ATM atm);

    void printCommandQueue();

    void saveATMsStatus();

    void loadATM(ATM atm);

    void saveATMStatus(ATM atm);

    void restoreATMsStatus();

    void restoreATMStatus(ATM atm);

    ATM buildATM(String className);

    double getTotalDepartmentBalance();

    void setATMAddress(ATM atm, String address);
}
