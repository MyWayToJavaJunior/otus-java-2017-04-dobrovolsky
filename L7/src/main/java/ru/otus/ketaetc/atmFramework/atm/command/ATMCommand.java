package ru.otus.ketaetc.atmFramework.atm.command;

import ru.otus.ketaetc.atmFramework.atm.State;

@FunctionalInterface
public interface ATMCommand {
    void execute(State state);
}

