package ru.otus.dobrovolsky.servlet.webSocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.dobrovolsky.dbService.CacheDescriptor;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdminWebSocketCreator implements WebSocketCreator {
    private final CacheDescriptor cacheDescriptor;
    private Set<AdminWebSocket> users;

    public AdminWebSocketCreator(CacheDescriptor cacheDescriptor) {
        this.cacheDescriptor = cacheDescriptor;
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<AdminWebSocket, Boolean>());
        System.out.println("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        AdminWebSocket socket = new AdminWebSocket(users, cacheDescriptor);
        System.out.println("Socket created");
        return socket;
    }
}