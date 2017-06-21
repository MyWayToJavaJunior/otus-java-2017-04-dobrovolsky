package ru.otus.dobrovolsky.atmFramework.atm.cassette;

/**
 * Created by ketaetc on 30.05.17.
 */
public class CashFromCashDispenser implements Cash {
    private int cash;

    public CashFromCashDispenser(int cash) {
        this.cash = cash;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getCash() {
        return cash;
    }
}
