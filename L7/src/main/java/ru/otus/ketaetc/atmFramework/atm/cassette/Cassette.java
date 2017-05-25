package ru.otus.ketaetc.atmFramework.atm.cassette;

public interface Cassette {

    void eject();

    int getNominal();

    int getCount();

    double getBalance();

    int getMaxCapacity();
}
