package ru.otus.ketaetc.atmFramework.itDepartment;

import ru.otus.ketaetc.atmFramework.atm.ATM;
import ru.otus.ketaetc.atmFramework.atm.CashDispenserATM;
import ru.otus.ketaetc.atmFramework.atm.CashInATM;
import ru.otus.ketaetc.atmFramework.atm.State;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.CashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.MostlyEvenlyCashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cashOutAlgorithm.SimpleCashOutAlgorithm;
import ru.otus.ketaetc.atmFramework.atm.cassette.CashDispenserCassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.CashInCassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.Cassette;
import ru.otus.ketaetc.atmFramework.atm.cassette.Nominal;
import ru.otus.ketaetc.atmFramework.atm.command.ATMCommand;
import ru.otus.ketaetc.atmFramework.atm.command.ATMCommand2ATM;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ITDepartment implements Department {
    private static ITDepartment itDepartment = new ITDepartment();
    private final List<ATM> atmList = new ArrayList<>();
    private Queue<ATMCommand> commandQueue = new ConcurrentLinkedQueue<>();

    private ITDepartment() {
    }

    public static ITDepartment getITDepartment() {
        return itDepartment;
    }

    @Override
    public List<ATM> getATMList() {
        return atmList;
    }

    @Override
    public <T extends ATM> void setAlgorithm(T t, CashOutAlgorithm algorithm) {
        t.setCashOutAlgorithm(algorithm);
    }

    private void addCommand(ATMCommand command) {
        commandQueue.add(command);
    }

    @Override
    public void ejectCassetesFromATMS() {
        for (ATM atm : atmList) {
            atm.ejectAllCassettes();
        }
    }

    @Override
    public void reloadCassetesToATMS() {
        for (ATM atm : atmList) {
            atm.ejectAllCassettes();
        }
    }

    @Override
    public void ejectCassetesFromATM(ATM atm) {
        atm.ejectAllCassettes();
    }

    private void processCommands(State state) {
        for (ATMCommand command : commandQueue) {
            command.execute(state);
        }
    }

    @Override
    public void register(ATM atm) {
        if (!atmList.contains(atm)) {
            atmList.add(atm);
        }
    }

    @Override
    public void unregisterAllATMS() {
        for (ATM atm : atmList) {
            atmList.remove(atm);
        }
    }

    @Override
    public void unregister(ATM atm) {
        atmList.remove(atm);
    }

    @Override
    public void setATMSWork() {
        for (ATM atm : atmList) {
            addCommand(new ATMCommand2ATM("Trying to set atm working   " + atm.toString(), atm));
        }
        for (ATM atm : atmList) {
            processCommands(State.IDLE);
        }
        commandQueue.clear();
    }

    @Override
    public void setATMWork(ATM atm) {
        addCommand(new ATMCommand2ATM("Trying to set atm working   " + atm.toString(), atm));
        processCommands(State.IDLE);
        commandQueue.clear();
    }


    @Override
    public void restartAllATMS() {
        for (ATM atm : atmList) {
            addCommand(new ATMCommand2ATM("Trying to restart   " + atm.toString(), atm));
        }
        for (ATM atm : atmList) {
            processCommands(State.REBOOTING);
        }
        commandQueue.clear();
    }

    @Override
    public void restartATM(ATM atm) {
        addCommand(new ATMCommand2ATM("Trying to restart   " + atm.toString(), atm));
        processCommands(State.REBOOTING);
        commandQueue.clear();
    }

    @Override
    public void setToServiceModeAllATMS() {
        for (ATM atm : atmList) {
            addCommand(new ATMCommand2ATM("Trying to set to maintenance mode   " + atm.toString(), atm));
        }
        for (ATM atm : atmList) {
            processCommands(State.MAINTENANCE);
        }
        commandQueue.clear();
    }

    @Override
    public void setToServiceModeATM(ATM atm) {
        addCommand(new ATMCommand2ATM("Trying to set to maintenance mode   " + atm.toString(), atm));
        processCommands(State.MAINTENANCE);
        commandQueue.clear();
    }


    @Override
    public void printCommandQueue() {
        for (ATMCommand command : commandQueue) {
            System.out.println(command);
        }
    }

    @Override
    public void saveATMsStatus() {
        for (ATM atm : atmList) {
            atm.saveToMemento();
        }
    }

    @Override
    public void loadATM(ATM atm) {
        atm.loadATM();
    }

    @Override
    public void saveATMStatus(ATM atm) {
        atm.saveToMemento();
    }

    @Override
    public void restoreATMsStatus() {
        for (ATM atm : atmList) {
            atm.getSavedState();
        }
    }

    @Override
    public void restoreATMStatus(ATM atm) {
        atm.getSavedState();
    }

    @Override
    public double getTotalDepartmentBalance() {
        return atmList.stream().mapToDouble(ATM::getATMBalance).sum();
    }

    @Override
    public void setATMAddress(ATM atm, String address) {
        atm.setAddress(address);
    }

    @Override
    public ATM buildATM(String className) {
        ATM atm;
        Cassette cas1;
        Cassette cas2;
        Cassette cas3;
        Cassette cas4;
        Cassette cas5;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (className.contains("CashDispenserATM")) {
            atm = (CashDispenserATM) object;
            cas1 = new CashDispenserCassette(Nominal.FIFTY, 500);
            cas2 = new CashDispenserCassette(Nominal.ONE_HUNDRED, 500);
            cas3 = new CashDispenserCassette(Nominal.FIVE_HUNDRED, 500);
            cas4 = new CashDispenserCassette(Nominal.ONE_THOUSAND, 500);
            cas5 = new CashDispenserCassette(Nominal.FIVE_THOUSAND, 500);
        } else if (className.contains("CashInATM")) {
            atm = (CashInATM) object;
            cas1 = new CashInCassette(Nominal.FIFTY);
            cas2 = new CashInCassette(Nominal.ONE_HUNDRED);
            cas3 = new CashInCassette(Nominal.FIVE_HUNDRED);
            cas4 = new CashInCassette(Nominal.ONE_THOUSAND);
            cas5 = new CashInCassette(Nominal.FIVE_THOUSAND);
        } else {
            throw new ClassCastException();
        }
        ITDepartment ITDepartment = getITDepartment();
        ITDepartment.register(atm);

        ITDepartment.setToServiceModeATM(atm);
        atm.setCassette(cas1);
        atm.setCassette(cas2);
        atm.setCassette(cas3);
        atm.setCassette(cas4);
        atm.setCassette(cas5);

        if (className.contains("CashDispenserATM")) {
            Random r = new Random();
            CashOutAlgorithm algorithm;
            algorithm = r.nextInt() > 500 ? SimpleCashOutAlgorithm.getAlgorithm() : MostlyEvenlyCashOutAlgorithm.getAlgorithm();

            System.out.println("Setting cash out algorithm: " + algorithm.getClass().getName());
            ITDepartment.setAlgorithm(atm, algorithm);
        }

        ITDepartment.setATMWork(atm);

        return atm;
    }
}
