package ru.otus.ketaetc.atmFramework.atm;

import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cassette.CashInCassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.Cassette;
import ru.otus.ketaetc.atmFramework.atm.memento.Memento;
import ru.otus.ketaetc.atmFramework.atm.statement.ATMStatement;
import ru.otus.ketaetc.atmFramework.exception.ATMStateException;
import ru.otus.ketaetc.atmFramework.exception.NoCassettesFoundException;

import static ru.otus.ketaetc.atmFramework.utils.Utils.prepareStatementRow;

public class CashInATM implements ATM {
    private final String DEFAULT_ADDRESS = "Bank office";

    private final State DEFAULT_STATE = State.NOT_WORKING;
    private final ATMStatement atmStatement = ATMStatement.getAtmStatement();
    private State state;
    private long id;
    private String address;
    private CashInCassette cassette;
    private Memento memento;

    public CashInATM() {
        id = ID_GENERATOR.incrementAndGet();
        address = DEFAULT_ADDRESS;
        state = State.ERROR;
    }

    public CashInATM(String address) {
        id = ID_GENERATOR.incrementAndGet();
        this.address = address;
        state = State.NO_RESPONSE;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCount() {
        return cassette.getCount();
    }

    /**
     * cash[0] - cash
     * cash[1] - count
     */
    @Override
    public void processCash(int... cash) {
        if (cassette == null) {
            try {
                throw new NoCassettesFoundException("No cassettes were loaded into ATM.");
            } catch (NoCassettesFoundException e) {
                e.printStackTrace();
            }
        }
        checkState();
        if ((cassette.getCount() + cash[1]) <= cassette.getMaxCapacity()) {
            atmStatement.addStatement(this, prepareStatementRow(cash[0]) + "   " + "C");
            cassette.cashIn(cash[0], cash[1]);
        }
    }

    @Override
    public void ejectAllCassettes() {
        ejectCassette(cassette);
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
        return cassette.getBalance();
    }

    @Override
    public void setCassette(Cassette cassette) {
        this.cassette = (CashInCassette) cassette;
    }

    @Override
    public void ejectCassette(Cassette cassette) {
        cassette = null;
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

    public void getCassettesLoadInfo() {
        if (getATMBalance() == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(this.toString() + "\nloaded with:");

        if (cassette.getCount() > 0) {
            stringBuilder.append("\n").append("count:   ").append(cassette.getCount()).append("   ").append("RUB  ")
                    .append(cassette.getBalance()).append(" load factor:    ")
                    .append(String.format("%3.4f", (((double) cassette.getCount() / cassette.getMaxCapacity()) * 100)));
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
    public CashOutAlgorithm getAlgorithm() {
        try {
            throw new Exception("For CashDispenserATMs only");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getSavedState() {
        this.state = memento.getSavedState();
        System.out.println("State after restoring from Memento: " + state);
    }

    @Override
    public void loadATM() {
        if (state == State.MAINTENANCE) {
            cassette = new CashInCassette();
        } else {
            try {
                throw new ATMStateException();
            } catch (ATMStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Memento saveToMemento() {
        memento = new Memento(this.state);
        System.out.println("Saving " + this.state + " to Memento.");
        return memento;
    }

    public void setCashOutAlgorithm(CashOutAlgorithm algorithm) {
        try {
            throw new Exception("For CashDispenserATMs only");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
