package ru.otus.ketaetc.atmFramework.atm.cassette;

import java.util.Iterator;

public class CashDispenserCassette implements Cassette, Comparable<CashDispenserCassette>, Iterable<CashDispenserCassette> {

    private final Nominal nominal;
    private final int MAX_CAPACITY = 1500;
    private int count;
    private CashDispenserCassette next;

    public CashDispenserCassette(Nominal nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    public CashDispenserCassette(Nominal nominal) {
        this.nominal = nominal;
        this.count = MAX_CAPACITY;
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }

    @Override
    public int compareTo(CashDispenserCassette o) {
        if (nominal.getNominalValue() > o.getNominal())
            return -1;
        if (nominal.getNominalValue() < o.getNominal())
            return 1;
        return 0;
    }

    @Override
    public Iterator<CashDispenserCassette> iterator() {
        return new Iterator<CashDispenserCassette>() {
            CashDispenserCassette current = CashDispenserCassette.this;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public CashDispenserCassette next() {
                CashDispenserCassette before = current;
                current = current.next;
                return before;
            }
        };
    }

    public void cashOut(int cash) {
        count -= cash / getNominal();
    }

    @Override
    public void eject() {
        count = 0;
    }

    @Override
    public int getNominal() {
        return nominal.getNominalValue();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getBalance() {
        return getNominal() * count;
    }

    public void setNext(CashDispenserCassette cassette) {
        next = cassette;
    }
}
