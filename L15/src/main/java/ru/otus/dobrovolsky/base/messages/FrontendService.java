package ru.otus.dobrovolsky.base.messages;

public interface FrontendService {
    void init();

    void handleRequest(String login);

    void addUser(long id, String name);
}

