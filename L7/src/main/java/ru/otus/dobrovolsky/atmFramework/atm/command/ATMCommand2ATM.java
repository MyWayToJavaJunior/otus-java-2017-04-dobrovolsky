package ru.otus.dobrovolsky.atmFramework.atm.command;

import ru.otus.dobrovolsky.atmFramework.atm.ATM;
import ru.otus.dobrovolsky.atmFramework.atm.State;

public class ATMCommand2ATM implements ATMCommand {
    private final String message;
    private final ATM atm;

    public ATMCommand2ATM(String message, ATM atm) {
        this.message = message;
        this.atm = atm;
    }

    @Override
    public void execute(State state) {
        System.out.println(message);
        switch (state) {
            case REBOOTING:
                atm.reboot();
                System.out.println("Rebooting " + atm.toString() + "    " + atm.getClass().getName());
                break;
            case POWER_OFF:
                atm.shutDown();
                System.out.println("Powering off " + atm.toString() + "    " + atm.getClass().getName());
                break;
            case STARTING_UP:
                atm.startUp();
                System.out.println("Starting up " + atm.toString() + "    " + atm.getClass().getName());
                break;
            case MAINTENANCE:
                atm.setServiceMode();
                System.out.println("Setting to maintenance mode " + atm.toString() + "    " + atm.getClass().getName());
                break;
            case IDLE:
                atm.startUp();
                System.out.println("Setting to working mode " + atm.toString() + "    " + atm.getClass().getName());
                break;
            default:
                System.out.println("Incorrect command at " + atm.toString() + "    " + atm.getClass().getName());
                break;
        }
    }
}
