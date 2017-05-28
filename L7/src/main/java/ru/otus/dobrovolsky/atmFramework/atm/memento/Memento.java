package ru.otus.dobrovolsky.atmFramework.atm.memento;

import ru.otus.dobrovolsky.atmFramework.atm.State;

public class Memento {
    private final State state;

    public Memento(State state) {
        this.state = state;
    }

    public State getSavedState() {
        return state;
    }
}
