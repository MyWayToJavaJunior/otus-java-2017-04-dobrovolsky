package ru.otus.dobrovolsky.atmFramework.atm;

import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.atm.cassette.Cassette;
import ru.otus.dobrovolsky.atmFramework.atm.memento.Memento;

import java.util.concurrent.atomic.AtomicInteger;

public interface ATM {
    AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    String getAddress();

    void setAddress(String address);

    double getATMBalance();

    void setCassette(Cassette cassette);

    void ejectCassette(Cassette cassette);

    void ejectAllCassettes();

    void setServiceMode();

    void reboot();

    void shutDown();

    void startUp();

    String getATMMiniStatement();

    Memento saveToMemento();

    void getSavedState();

    void loadATM();

    void setCashOutAlgorithm(CashOutAlgorithm algorithm);

    void checkState();

    CashOutAlgorithm getAlgorithm();

    State getState();

    /**
     * i[0] - cash
     * i[1] - count
     */
//    void processCash(int... i);
    <T> void processCash(T cash);

    void getCassettesLoadInfo();
}
