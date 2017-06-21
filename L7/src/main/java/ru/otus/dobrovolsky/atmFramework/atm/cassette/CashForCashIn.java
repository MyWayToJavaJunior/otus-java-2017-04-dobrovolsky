package ru.otus.dobrovolsky.atmFramework.atm.cassette;

/**
 * Created by ketaetc on 31.05.17.
 */
public class CashForCashIn implements Cash {
    private int cash;
    private int count;

    public CashForCashIn(int cash, int count) {
        this.cash = cash;
        this.count = count;
    }

    @Override
    public int getCash() {
        return cash;
    }

    public int getCount() {
        return count;
    }
}
