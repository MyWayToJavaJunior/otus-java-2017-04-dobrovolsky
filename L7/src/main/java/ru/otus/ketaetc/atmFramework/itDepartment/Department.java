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

public class Department {
    private static Department department = new Department();
    private final List<ATM> atmList = new ArrayList<>();
    private Queue<ATMCommand> commandQueue = new ConcurrentLinkedQueue<>();

    private Department() {
    }

    public static Department getDepartment() {
        return department;
    }

    public List<ATM> getATMList() {
        return atmList;
    }

    public void setAlgorithm(CashDispenserATM atm, CashOutAlgorithm algorithm) {
        atm.setAlgorithm(algorithm);
    }

    private void addCommand(ATMCommand command) {
        commandQueue.add(command);
    }

    public void ejectCassetesFromATMS() {
        for (ATM atm : atmList) {
            atm.ejectAllCassettes();
        }
    }

    public void reloadCassetesToATMS() {
        for (ATM atm : atmList) {
            atm.ejectAllCassettes();
        }
    }

    public void ejectCassetesFromATM(ATM atm) {
        atm.ejectAllCassettes();
    }

    private void processCommands(State state) {
        for (ATMCommand command : commandQueue) {
            command.execute(state);
        }
    }

    public void register(ATM atm) {
        if (!atmList.contains(atm)) {
            atmList.add(atm);
        }
    }

    public void unregisterAllATMS() {
        for (ATM atm : atmList) {
            atmList.remove(atm);
        }
    }

    public void unregister(ATM atm) {
        atmList.remove(atm);
    }

    public void setATMSWork() {
        for (ATM atm : atmList) {
            addCommand(new ATMCommand2ATM("Trying to set atm working   " + atm.toString(), atm));
        }
        for (ATM atm : atmList) {
            processCommands(State.IDLE);
        }
        commandQueue.clear();
    }

    public void setATMWork(ATM atm) {
        addCommand(new ATMCommand2ATM("Trying to set atm working   " + atm.toString(), atm));
        processCommands(State.IDLE);
        commandQueue.clear();
    }


    public void restartAllATMS() {
        for (ATM atm : atmList) {
            addCommand(new ATMCommand2ATM("Trying to restart   " + atm.toString(), atm));
        }
        for (ATM atm : atmList) {
            processCommands(State.REBOOTING);
        }
        commandQueue.clear();
    }

    public void restartATM(ATM atm) {
        addCommand(new ATMCommand2ATM("Trying to restart   " + atm.toString(), atm));
        processCommands(State.REBOOTING);
        commandQueue.clear();
    }

    public void setToServiceModeAllATMS() {
        for (ATM atm : atmList) {
            addCommand(new ATMCommand2ATM("Trying to set to maintenance mode   " + atm.toString(), atm));
        }
        for (ATM atm : atmList) {
            processCommands(State.MAINTENANCE);
        }
        commandQueue.clear();
    }

    public void setToServiceModeATM(ATM atm) {
        addCommand(new ATMCommand2ATM("Trying to set to maintenance mode   " + atm.toString(), atm));
        processCommands(State.MAINTENANCE);
        commandQueue.clear();
    }


    public void printCommandQueue() {
        for (ATMCommand command : commandQueue) {
            System.out.println(command);
        }
    }

    public void saveATMsStatus() {
        for (ATM atm : atmList) {
            atm.saveToMemento();
        }
    }

    public void loadATM(ATM atm) {
        atm.loadATM();
    }

    public void saveATMStatus(ATM atm) {
        atm.saveToMemento();
    }

    public void restoreATMsStatus() {
        for (ATM atm : atmList) {
            atm.getSavedState();
        }
    }

    public void restoreATMStatus(ATM atm) {
        atm.getSavedState();
    }

    public double getTotalDepartmentBalance() {
        return atmList.stream().mapToDouble(ATM::getATMBalance).sum();
    }

    public ATM buildATM(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ATM atm;
        Cassette cas1;
        Cassette cas2;
        Cassette cas3;
        Cassette cas4;
        Cassette cas5;
        Class<?> clazz = Class.forName(className);
        Object object = clazz.newInstance();
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
        Department department = getDepartment();
        department.register(atm);

        department.setToServiceModeATM(atm);
        atm.setCassette(cas1);
        atm.setCassette(cas2);
        atm.setCassette(cas3);
        atm.setCassette(cas4);
        atm.setCassette(cas5);

        if (className.contains("CashDispenserATM")) {
            Random r = new Random();
            CashOutAlgorithm algorithm = null;
            algorithm = r.nextInt() > 500 ? SimpleCashOutAlgorithm.getAlgorithm() : MostlyEvenlyCashOutAlgorithm.getAlgorithm();

            System.out.println("Setting cash out algorithm: " + algorithm.getClass().getName());
            department.setAlgorithm((CashDispenserATM) atm, algorithm);
        }

        department.setATMWork(atm);

        return atm;
    }
}
