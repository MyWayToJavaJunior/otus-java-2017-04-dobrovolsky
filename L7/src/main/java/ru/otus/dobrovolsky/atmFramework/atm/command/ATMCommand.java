package ru.otus.dobrovolsky.atmFramework.atm.command;

import ru.otus.dobrovolsky.atmFramework.atm.State;

@FunctionalInterface
public interface ATMCommand {
    void execute(State state);
}

