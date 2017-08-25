package ru.otus.dobrovolsky.message.server.channel;

import ru.otus.dobrovolsky.message.server.messages.Msg;

import java.io.IOException;

public interface MsgChannel {
    void send(Msg msg);

    Msg poll();

    Msg take() throws InterruptedException;

    void close() throws IOException;
}
