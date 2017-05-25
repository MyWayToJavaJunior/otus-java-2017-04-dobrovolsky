package ru.otus.ketaetc.atmFramework.atm.memento;

import ru.otus.ketaetc.atmFramework.atm.State;

public class Memento {
    private final State state;

    public Memento(State state) {
        this.state = state;
    }

    public State getSavedState() {
        return state;
    }
}
