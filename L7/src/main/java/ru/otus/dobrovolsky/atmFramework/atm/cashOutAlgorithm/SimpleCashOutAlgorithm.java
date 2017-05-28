package ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm;

public class SimpleCashOutAlgorithm implements CashOutAlgorithm {
    private static SimpleCashOutAlgorithm algorithm = new SimpleCashOutAlgorithm();

    private SimpleCashOutAlgorithm() {
    }

    public static SimpleCashOutAlgorithm getAlgorithm() {
        return algorithm;
    }
}
