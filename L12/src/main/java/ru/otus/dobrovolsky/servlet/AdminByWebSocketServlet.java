package ru.otus.dobrovolsky.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.dobrovolsky.dbService.CacheDescriptor;
import ru.otus.dobrovolsky.servlet.webSocket.AdminWebSocketCreator;


public class AdminByWebSocketServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    private final CacheDescriptor cacheDescriptor;

    public AdminByWebSocketServlet(CacheDescriptor cacheDescriptor) {
        this.cacheDescriptor = cacheDescriptor;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new AdminWebSocketCreator(this.cacheDescriptor));
    }
}
