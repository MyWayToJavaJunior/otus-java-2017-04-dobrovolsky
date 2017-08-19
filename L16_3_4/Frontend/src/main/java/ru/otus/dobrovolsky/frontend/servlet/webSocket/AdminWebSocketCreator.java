package ru.otus.dobrovolsky.frontend.servlet.webSocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.dobrovolsky.frontend.server.FrontendServer;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdminWebSocketCreator implements WebSocketCreator {
    private final SocketClientChannel client;
    private Set<AdminWebSocket> users;
    private FrontendServer frontendServer;

    public AdminWebSocketCreator(SocketClientChannel client, FrontendServer frontendServer) {
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<AdminWebSocket, Boolean>());
        this.client = client;
        this.frontendServer = frontendServer;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        AdminWebSocket socket = new AdminWebSocket(users, client, frontendServer);
        return socket;
    }
}