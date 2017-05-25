package ru.otus.ketaetc.atmFramework.atm;

import ru.otus.ketaetc.atmFramework.atm.cassette.Cassette;
import ru.otus.ketaetc.atmFramework.atm.memento.Memento;
import ru.otus.ketaetc.atmFramework.itDepartment.Department;

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

    void setDepartment(Department department);

    String getATMMiniStatement();

    Memento saveToMemento();

    void getSavedState();

    void loadATM();
}
