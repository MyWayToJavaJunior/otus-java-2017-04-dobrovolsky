package ru.otus.ketaetc.atmFramework.atm;

public enum State {
    NOT_WORKING("ATM does not provide customer service", 0),
    NO_RESPONSE("ATM not responding", 1),
    IDLE("ATM in idle mode", 2),
    WORKING("ATM working on request", 3),
    REBOOTING("ATM rebooting now", 4),
    POWER_OFF("ATM powered off", 5),
    STARTING_UP("ATM starting up now", 6),
    NETWORK_PROBLEM("ATM has some problems with the network, client requests may not be sent to the processing center", 7),
    MAINTENANCE("ATM in maintenance mode", 8),
    SELFTESTING("ATM in process of self testing", 9),
    ERROR("Unknown error", 99);

    private int state;
    private String description;

    State(String description, int state) {
        this.description = description;
        this.state = state;
    }

    int getState() {
        return this.state;
    }

    String getDescription() {
        return this.description;
    }
}
