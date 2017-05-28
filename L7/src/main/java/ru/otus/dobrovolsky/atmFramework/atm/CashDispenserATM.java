package ru.otus.dobrovolsky.atmFramework.atm;

import ru.otus.dobrovolsky.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;
import ru.otus.dobrovolsky.atmFramework.atm.cassette.CashDispenserCassette;
import ru.otus.dobrovolsky.atmFramework.atm.cassette.Cassette;
import ru.otus.dobrovolsky.atmFramework.atm.cassette.Nominal;
import ru.otus.dobrovolsky.atmFramework.atm.memento.Memento;
import ru.otus.dobrovolsky.atmFramework.atm.statement.ATMStatement;
import ru.otus.dobrovolsky.atmFramework.exception.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.otus.dobrovolsky.atmFramework.utils.Utils.prepareStatementRow;

public class CashDispenserATM implements ATM {
    private final String DEFAULT_ADDRESS = "Bank office";
    private final State DEFAULT_STATE = State.NOT_WORKING;
    private final ATMStatement atmStatement = ATMStatement.getAtmStatement();
    private CashOutAlgorithm algorithm;
    private State state;
    private long id;
    private String address;
    private List<CashDispenserCassette> cassettes = new ArrayList<>();
    private Memento memento;

    public CashDispenserATM() {
        id = ID_GENERATOR.incrementAndGet();
        address = DEFAULT_ADDRESS;
        state = State.SELFTESTING;
    }

    public CashDispenserATM(String address) {
        id = ID_GENERATOR.incrementAndGet();
        this.address = address;
        state = State.NETWORK_PROBLEM;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    int getLeastNominal() throws NoCassettesFoundException {
        if (cassettes.isEmpty()) {
            throw new NoCassettesFoundException("No cassettes were loaded into ATM.");
        }
        int leastNominal = Integer.MAX_VALUE;
        for (Cassette cassette : cassettes) {
            if (cassette.getCount() != 0) {
                if (leastNominal > cassette.getNominal()) {
                    leastNominal = cassette.getNominal();
                }
            }
        }
        return leastNominal;
    }

    @Override
    public void processCash(int... cash) {
        if ((algorithm == null) || (algorithm.getClass().getCanonicalName().contains("SimpleCashOutAlgorithm"))) {
            try {
                cashOutSimple(cash[0]);
            } catch (NoCassettesFoundException | NotEnoughMoneyException | NotSuchNominalException e) {
                e.printStackTrace();
            }
        } else if (algorithm.getClass().getCanonicalName().contains("MostlyEvenlyCashOutAlgorithm")) {
            try {
                cashOutMostlyEvenly(cash[0]);
            } catch (NoCassettesFoundException | NotEnoughMoneyException | NotSuchNominalException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new NotSuchAlgorithmException("No such algorithm for cashing out.");
            } catch (NotSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private void cashOutSimple(int cash) throws NoCassettesFoundException, NotEnoughMoneyException, NotSuchNominalException {
        double atmBalance = getATMBalance();
        if (atmBalance < cash) {
            throw new NotEnoughMoneyException("Not enough money: " + atmBalance + " for requested sum: " + cash);
        }
        int leastNominal = getLeastNominal();
        if ((cash % leastNominal) != 0) {
            throw new NotSuchNominalException("There is no such nominal in ATM cassettes.");
        }

        int i = 1;

        int expectedCount = Math.min(cash / cassettes.get(0).getNominal(), cassettes.get(0).getCount());
        int expectedCash = expectedCount * cassettes.get(0).getNominal();
        if (cash == expectedCash) {
            cassettes.get(0).cashOut(cash);
        } else {
            cassettes.get(0).cashOut(cash - expectedCash);
        }
        while (cash > expectedCash) {
            expectedCount = Math.min(cash / cassettes.get(i).getNominal(), cassettes.get(i).getCount());
            expectedCash = expectedCount * cassettes.get(i).getNominal();
            if (cash == expectedCash) {
                cassettes.get(i).cashOut(cash);
            } else {
                cassettes.get(i).cashOut(cash - expectedCash);
            }
            if (i == (cassettes.size() - 1)) {
                i = 0;
            } else {
                i++;
            }
        }
        if (cash == expectedCash) {
            atmStatement.addStatement(this, prepareStatementRow(cash) + "   " + "D");
        }
    }

    private boolean cashOutMostlyEvenly(int cash) throws NoCassettesFoundException, NotEnoughMoneyException, NotSuchNominalException {
        double atmBalance = getATMBalance();
        if (atmBalance < cash) {
            throw new NotEnoughMoneyException("Not enough money for requested sum.");
        }
        int leastNominal = getLeastNominal();
        if ((cash % leastNominal) != 0) {
            throw new NotSuchNominalException("There is no such nominal in ATM cassettes.");
        }

        int atmCash = 0;
        while (atmCash < cash) {
            for (CashDispenserCassette cassette : cassettes) {
                if (cassette.getNominal() <= cash) {
                    atmCash += cassette.getNominal();
                    cassette.cashOut(cassette.getNominal());
                    if (atmCash == cash) {
                        atmStatement.addStatement(this, prepareStatementRow(cash) + "   " + "D");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void loadATM() {
        if (state == State.MAINTENANCE) {
            if (cassettes.isEmpty()) {
                cassettes.add(new CashDispenserCassette(Nominal.FIVE_THOUSAND, 1000));
                cassettes.add(new CashDispenserCassette(Nominal.ONE_THOUSAND, 1000));
                cassettes.add(new CashDispenserCassette(Nominal.FIVE_THOUSAND, 1000));
                cassettes.add(new CashDispenserCassette(Nominal.ONE_THOUSAND, 1000));
                cassettes.add(new CashDispenserCassette(Nominal.FIFTY, 1000));
            }
        } else {
            try {
                throw new ATMStateException();
            } catch (ATMStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void ejectAllCassettes() {
        for (Cassette cassette : cassettes) {
            cassette.eject();
        }
    }

    @Override
    public State getState() {
        return this.state;
    }

    private void setState(State state) {
        this.state = state;
    }

    @Override
    public String getATMMiniStatement() {
        return atmStatement.getATMMiniStatement(this);
    }

    @Override
    public double getATMBalance() {
        double balance = 0;
        for (Cassette cassette : cassettes) {
            balance += cassette.getBalance();
        }
        return balance;
    }

    @Override
    public void setCassette(Cassette cassette) {
        if (state == State.MAINTENANCE) {
            cassettes.add((CashDispenserCassette) cassette);
            Collections.sort(cassettes);
        } else {
            System.out.println("ATM need to set to maintenance mode before working with cassettes.");
        }
    }

    @Override
    public void ejectCassette(Cassette cassette) {
        if (state == State.MAINTENANCE) {
            cassettes.remove(cassette);
            Collections.sort(cassettes);
        } else {
            try {
                throw new ATMStateException();
            } catch (ATMStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setServiceMode() {
        state = State.MAINTENANCE;
    }

    @Override
    public void reboot() {
        setState(State.REBOOTING);
    }

    @Override
    public void shutDown() {
        setState(State.POWER_OFF);
    }

    @Override
    public void startUp() {
        setState(State.STARTING_UP);
        System.out.println(state.getDescription());
        System.out.println("All services started well. ATM is working now.");
        setState(State.IDLE);
    }

    @Override
    public String toString() {
        return "ATM:    " + "id:    " + id + "  address: " + address + "    ATM state:  " + state;
    }

    @Override
    public CashOutAlgorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public void getCassettesLoadInfo() {
        if (getATMBalance() == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(this.toString() + "\nloaded with:");

        for (Cassette cassette : cassettes) {
            if (cassette.getCount() > 0) {
                stringBuilder.append("\n").append("nominal:    ").append(cassette.getNominal()).append("   ")
                        .append("count:   ").append(cassette.getCount()).append("   ").append("RUB  ")
                        .append(cassette.getCount() * cassette.getNominal()).append(" load factor:    ")
                        .append(String.format("%3.4f", (((double) cassette.getCount() / cassette.getMaxCapacity()) * 100)));
            }
        }
        System.out.println(stringBuilder.toString() + "\n");
    }

    @Override
    public void checkState() {
        if (state != State.IDLE) {
            try {
                throw new ATMStateException("ATM can not handle request right now.");
            } catch (ATMStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getSavedState() {
        this.state = memento.getSavedState();
        System.out.println("State after restoring from Memento: " + state);
    }

    @Override
    public Memento saveToMemento() {
        memento = new Memento(this.state);
        System.out.println("Saving " + this.state + " to Memento.");
        return memento;
    }

    @Override
    public void setCashOutAlgorithm(CashOutAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
