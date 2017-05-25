package ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm;

public class MostlyEvenlyCashOutAlgorithm implements CashOutAlgorithm {
    private static MostlyEvenlyCashOutAlgorithm algorithm = new MostlyEvenlyCashOutAlgorithm();

    private MostlyEvenlyCashOutAlgorithm() {
    }

    public static MostlyEvenlyCashOutAlgorithm getAlgorithm() {
        return algorithm;
    }
}
