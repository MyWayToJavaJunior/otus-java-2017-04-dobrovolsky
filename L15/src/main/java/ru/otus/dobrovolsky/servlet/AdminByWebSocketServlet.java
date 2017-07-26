package ru.otus.dobrovolsky.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.servlet.webSocket.AdminWebSocketCreator;


public class AdminByWebSocketServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    @Autowired
    private DBService dbService;

    public AdminByWebSocketServlet() {
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new AdminWebSocketCreator(dbService.getCacheDescriptor()));
    }
}
