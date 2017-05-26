package ru.otus.ketaetc.atmFramework.atm;

import ru.otus.ketaetc.atmFramework.atm.cassette.Cassette;
import ru.otus.ketaetc.atmFramework.atm.memento.Memento;

import java.util.concurrent.atomic.AtomicInteger;

public interface ATM {
    AtomicInteger ID_GENERATOR = new AtomicInteger(0);

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
}
