package ru.otus.dobrovolsky.frontend.servlet.webSocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdminWebSocketCreator implements WebSocketCreator {
    private final SocketClientChannel client;
    private Set<AdminWebSocket> users;

    public AdminWebSocketCreator(SocketClientChannel client) {
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<AdminWebSocket, Boolean>());
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<AdminWebSocket, Boolean>());
        this.client = client;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        AdminWebSocket socket = new AdminWebSocket(users, client);
        return socket;
    }
}