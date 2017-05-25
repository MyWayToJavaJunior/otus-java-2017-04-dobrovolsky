package ru.otus.ketaetc.atmFramework.atm.cassette;

public enum Nominal {
    FIFTY(50),
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private int nominalValue;

    Nominal(int nominalValue) {
        this.nominalValue = nominalValue;
    }

    public int getNominalValue() {
        return nominalValue;
    }
}
