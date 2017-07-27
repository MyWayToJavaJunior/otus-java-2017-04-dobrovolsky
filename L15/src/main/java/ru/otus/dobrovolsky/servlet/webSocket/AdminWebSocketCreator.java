package ru.otus.dobrovolsky.servlet.webSocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.dobrovolsky.base.messages.CacheDescriptor;
import ru.otus.dobrovolsky.base.messages.FrontendService;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdminWebSocketCreator implements WebSocketCreator {
    private final CacheDescriptor cacheDescriptor;
    private Set<AdminWebSocket> users;
    private FrontendService frontendService;

    public AdminWebSocketCreator(CacheDescriptor cacheDescriptor, FrontendService frontendService) {
        this.cacheDescriptor = cacheDescriptor;
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<AdminWebSocket, Boolean>());
        this.frontendService = frontendService;
        System.out.println("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        AdminWebSocket socket = new AdminWebSocket(users/*, cacheDescriptor*/, frontendService);
        System.out.println("Socket created");
        return socket;
    }
}