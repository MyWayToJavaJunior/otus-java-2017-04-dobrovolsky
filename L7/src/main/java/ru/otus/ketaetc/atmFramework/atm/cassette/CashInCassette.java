package ru.otus.ketaetc.atmFramework.atm.cassette;

import ru.otus.ketaetc.atmFramework.atm.statement.ATMStatement;

import java.util.Iterator;

public class CashInCassette implements Cassette, Comparable<CashInCassette>, Iterable<CashInCassette> {

    private final int MAX_CAPACITY = 10000;
    private final ATMStatement atmStatement = ATMStatement.getAtmStatement();
    private int count;
    private int sum;
    private CashInCassette next;

    public CashInCassette() {
        this.count = 0;
    }

    public CashInCassette(Nominal nominal) {
        this.count = 0;
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }

    public int compareTo(CashInCassette o) {
        return 0;
    }

    @Override
    public Iterator<CashInCassette> iterator() {
        return new Iterator<CashInCassette>() {
            CashInCassette current = CashInCassette.this;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public CashInCassette next() {
                CashInCassette before = current;
                current = current.next;
                return before;
            }
        };
    }

    public boolean cashIn(int count, int cash) {
        if ((this.count + count) <= MAX_CAPACITY) {
            this.count += count;
            this.sum += cash;
        }
        return true;
    }

    @Override
    public void eject() {
        count = 0;
    }

    @Override
    public int getNominal() {
        return 0;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getBalance() {
        return sum;
    }

    public void setNext(CashInCassette cassette) {
        next = cassette;
    }
}
